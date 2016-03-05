package org.usfirst.frc.team95.robot;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class VisionHandler {
	double x, y, aimX, aimY, distance, power = 1; // These represent the x and y, in pixels, of the
		// center of the goal.
	
	boolean aimStalled;
	Timer time = new Timer();
	
	double timeLastSeen;
	
	public double getX() { // Getters!
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getAimX() {
		return aimX;
	}
	
	public double getAimY() {
		return aimY;
	}
	
	public double getPower() {
		return power;
	}
	
	public double getRange() {
		return distance;
	}
	
	private final static String[] CLEAR_TMP_CMD =
		{"/bin/rm", "-rf", "/tmp/*"};
	
	private final static String[] GRIP_CMD =
		{"/usr/local/frc/JRE/bin/java", "-jar", "/home/lvuser/grip.jar", 
				"/home/lvuser/project.grip"};
	
	private final static String[] KILL_REMOTE_GRIP =
		{"ssh", "pi@raspberrypi.local", "sudo", "killall", "java"};
	
	static VisionHandler instance; // This is the only instance of VisionHandler that should be used
	
	NetworkTable GRIPTable; // This represents GRIPs published reports
	Comparator<Line> sort = new Comparator<Line>() { // This sorts Line objects based on length
		public int compare(Line l1, Line l2) { // Hooray for anonymous classes.
			if (l1.length == l2.length)
				return 0;
			return l1.length > l2.length ? 1 : -1;
		}
	};
	
	ITableListener updater = new ITableListener() { // This updates the x and y whenever the table updates.
		public void valueChanged(ITable table, String x, Object y, boolean n) {
			;
		}
		
		@Override
		public void valueChangedEx(ITable table, String key, Object value, int newP) {
			timeLastSeen = time.get();
			boolean newKey = (newP & 0x04) != 0;
			boolean updated = (newP & 0x10) != 0;
			if (updated) {
				VisionHandler.getInstance().update(table);
			}
		}
	};
	
	Runnable crashCheck = new Runnable() {
		@Override
		public void run() {
			while (true) {
				System.out.println("Time "+(time.get() - timeLastSeen));
				if (time.get() - timeLastSeen > Constants.GRIPDeadTime) {
					try {
						new ProcessBuilder(KILL_REMOTE_GRIP).inheritIO().start();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};
	
	Runnable poller = new Runnable() {
		@Override
		public void run() {
			while (true) {
				ITable grip = NetworkTable.getTable("GRIP/myLinesReport");
				;
				VisionHandler.getInstance().update(grip);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};
	
	public void init() { // This sets everything up to listen!
		try {
			new ProcessBuilder(CLEAR_TMP_CMD).inheritIO().start();
			//new ProcessBuilder(GRIP_CMD).inheritIO().start();
		} catch (IOException e) {
			System.out.println(e);
		}
		
		time.reset();
		time.start();
		
		GRIPTable = NetworkTable.getTable("GRIP/myLinesReport");
		GRIPTable.addTableListener(updater);
		
		new Thread(crashCheck).start();
	}
	
	public static VisionHandler getInstance() { // Yay. Boilerplate.
		if (instance != null)
			return instance;
		instance = new VisionHandler();
		return getInstance();
	}
	
	public boolean horizontalP(Line line) {
		return horizontalP(line.angle);
	}

	public boolean horizontalP(double degrees) { // This determines if an angle is vertical or horizontal
		degrees = Math.abs((degrees - 45) % 180);
		return degrees > 90;
	}
	
	public double average(List<Line> list, Field field) {
		double x = 0;
		for (Line l : list) {
			try {
				x += field.getDouble(l);
			} catch (IllegalArgumentException e) {
				System.out.println("Tell Daroc that the VisionHandler is misbehaving. (A)");
			} catch (IllegalAccessException e) {
				System.out.println("Tell Daroc that the VisionHandler is misbehaving. (B)");
			}
		}
		x /= list.size();
		return x;
	}
	
	public double lineDistance(Line a, Line b) {
		double w = Math.sqrt(Math.pow(a.x1-b.x1, 2) + Math.pow(a.y1-b.y1, 2));
		double x = Math.sqrt(Math.pow(a.x1-b.x2, 2) + Math.pow(a.y1-b.y2, 2));
		double y = Math.sqrt(Math.pow(a.x2-b.x1, 2) + Math.pow(a.y2-b.y1, 2));
		double z = Math.sqrt(Math.pow(a.x2-b.x2, 2) + Math.pow(a.y2-b.y2, 2));
		return Math.min(Math.min(w, x), Math.min(y, z));
	}
	
	public void update(ITable table) { // This does the workhorsing of the updates
		//System.out.println(table.getKeys());
		Line[] lineTable = getLines(table);
		//System.out.println(lineTable.length);
		
		ArrayList<Line> lines = new ArrayList<Line>(); // This would be a Set, if I could get sets working.
		lines.addAll(Arrays.asList(lineTable));
		lines.removeIf(new Predicate<Line>() {
			@Override
			public boolean test(Line t) {
				return t == null;
			}
		});
		lines.sort(sort);
		
		ArrayList<Triple<Line>> targets = new ArrayList<Triple<Line>>();
		
		for (int a = 0; a < lines.size(); a++) {
			Line p = lines.get(a);
		    for (int b = a + 1; b < lines.size(); b++) {
		    	Line q = lines.get(b);
		        for (int c = b + 1; c < lines.size(); c++) {
		        	Line r = lines.get(c);
		        	if (lineDistance(p, q) 
		        			< p.length/5 &&
		        			lineDistance(q, r)
		        			< q.length/5 &&
		        			!horizontalP(p) && horizontalP(q) && !horizontalP(r)) {
		        		targets.add(new Triple<Line>(lines.get(a), lines.get(b), lines.get(c)));
		        	}
		        }
		    }
		}
		
		//System.out.println("Targets found: " + targets.size());
		if (targets.size() > 0) {
			Triple<Line> target = targets.get(0);
			this.x = (target.a.x1+target.a.x2+target.c.x1+target.c.x2)/4;
			// Note: When the bot aims at the bottom of the goal, this is the culprit:
			this.y = (target.a.x1+target.b.x1)/2;
			System.out.println(this.x + " - " + this.y);
			SmartDashboard.putBoolean("Ready to Autoaim", true);
		} else {
			SmartDashboard.putBoolean("Ready to Autoaim", false);
		}
		
		
		double tX = Constants.horizontalWidth/2 - x;
		tX *= -Constants.horizontalPixelsToDegrees / 180 * Math.PI;
		tX += Constants.cameraHorizontalOffset;
		
		double tY = Constants.verticalHeight/2 - y;
		tY *= -Constants.verticalPixelsToDegrees / 180 * Math.PI;
		tY += Constants.cameraVerticalOffset;
		
		distance = 1/Math.tan(tY)*(Constants.goalHeight-Math.sin(RobotMap.arm1.getPosition())*
				Constants.cameraDistanceToPivot);
		
		//double V0y = Math.sqrt(2*Constants.gravity*Math.sin(RobotMap.arm1.getPosition())*
		//		Constants.shooterLength);
		//double t = V0y / Constants.gravity;
		//double V0x = distance / t;
		//double theta = Math.atan(V0y/V0x);
		//double m = Math.sqrt(Math.pow(V0y, 2) + Math.pow(V0x, 2));
		
		SmartDashboard.putNumber("Autoaim Proposed Turn", tX);
		SmartDashboard.putNumber("Autoaim Proposed Arm Offset", tY);
		
		aimX = -tX;
		aimY = tY;
		//power = m * Constants.shootVelocityToRPM;
		
		
	}
	
	public Line[] getLines(ITable lineReport) { // This marshals stuff from the NetworkTable to
												// a bunch of line objects.
		double[] x1s = lineReport.getNumberArray("x1", Constants.emptydoubleTable);
		double[] x2s = lineReport.getNumberArray("x2", Constants.emptydoubleTable);
		double[] y1s = lineReport.getNumberArray("y1", Constants.emptydoubleTable);
		double[] y2s = lineReport.getNumberArray("y2", Constants.emptydoubleTable);
		double[] lengths = lineReport.getNumberArray("length", Constants.emptydoubleTable);
		double[] angles = lineReport.getNumberArray("angle", Constants.emptydoubleTable);
		Line[] lineTable = new Line[x1s.length+10];
		for (int i=0; i<x1s.length; i++) {
			lineTable[i] = new Line();
		}
		for (int i=0; i<x1s.length; i++) {
			lineTable[i].x1 = x1s[i];
		}
		for (int i=0; i<x2s.length; i++) {
			lineTable[i].x2 = x2s[i];
		}
		for (int i=0; i<y1s.length; i++) {
			lineTable[i].y1 = y1s[i];
		}
		for (int i=0; i<y2s.length; i++) {
			lineTable[i].y2 = y2s[i];
		}
		for (int i=0; i<lengths.length; i++) {
			lineTable[i].length = lengths[i];
		}
		for (int i=0; i<angles.length; i++) {
			lineTable[i].angle = angles[i];
		}
		return lineTable;
	}
	
	private class TargetCandidate {
		public Triple<Line> t;
	}
	
	private class Triple<T> {
		public Triple(){
			;
		}
		public Triple(T arg1, T arg2, T arg3) {
			a = arg1; b = arg2; c = arg3;
		}

		public T a, b, c;
	}
	
	private class Line { // This would be a struct, if java weren't stupid.
		@SuppressWarnings("unused")
		public double x1, x2, y1, y2, length, angle;
	}

}
