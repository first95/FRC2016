package org.usfirst.frc.team95.robot;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class VisionHandler {
	double x, y; // These represent the x and y, in pixels, of the
		// center of the goal.
	
	public double getX() { // Getters!
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	private final static String[] CLEAR_TMP_CMD =
		{"/bin/rm", "-rf", "/tmp/*"};
	
	private final static String[] GRIP_CMD =
		{"/usr/local/frc/JRE/bin/java", "-jar", "/home/lvuser/grip.jar", 
				"/home/lvuser/project.grip"};
	
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
		@Override
		public void valueChanged(ITable table, String key, Object value, boolean newP) {
			if (key.equals("x")) // So that we don't get spammed by updates.
				VisionHandler.getInstance().update(table);
		}
	};
	
	Runnable poller = new Runnable() {
		@Override
		public void run() {
			while (true) {
				VisionHandler.getInstance().update(NetworkTable.getTable("GRIP/myLinesReport"));
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
			new ProcessBuilder(GRIP_CMD).inheritIO().start();
			//new ProcessBuilder("echo").inheritIO().start();
		} catch (IOException e) {
			System.out.println(e);
		}
		GRIPTable = NetworkTable.getTable("GRIP/myLinesReport");
		//GRIPTable.addTableListener(updater);
		
		new Thread(poller).start();
	}
	
	public static VisionHandler getInstance() { // Yay. Boilerplate.
		if (instance != null)
			return instance;
		instance = new VisionHandler();
		return getInstance();
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
	
	public void update(ITable table) { // This does the workhorsing of the updates
		//System.out.println(table.getKeys());
		Line[] lineTable = getLines(table);
		System.out.println(lineTable.length);
		
		ArrayList<Line> lines = new ArrayList<Line>(); // This would be a Set, if I could get sets working.
		lines.addAll(Arrays.asList(lineTable));
		lines.sort(sort);
		
		ArrayList<Pair<Line>> pairs = new ArrayList<Pair<Line>>();
		while (lines.size() > 0) {
			Line x = lines.remove(0); // Performance
			Pair<Line> p = new Pair<Line>();
			p.a = x;
			for (Line l: lines) {
				if (Math.abs(x.angle - l.angle) < Constants.parallelTolerance) {
					lines.remove(l);
					p.b = l;
					break;
				}
			}
			pairs.add(p);
		}
		
		ArrayList<TargetCandidate> targets = new ArrayList<TargetCandidate>();
		for (Pair<Line> p : pairs) {
			for (Pair<Line> q : pairs) {
				for (Pair<Line> r : pairs) {
					if (distance(p, q) < Constants.lineDistanceTolerance &&
							distance(q,r) < Constants.lineDistanceTolerance) {
						TargetCandidate t = new TargetCandidate();
						targets.add(t);
					}
				}
			}
		}
		
		System.out.println("Found this many vision targets: ");
		System.out.println(targets.size());
		
		TargetCandidate target = targets.get(0);
		this.x = (target.bottom.a.x1+target.bottom.a.x2+target.bottom.b.x1+target.bottom.b.x2)/4;
		// Note: When the bot aims at the bottom of the goal, this is the culprit:
		this.y = (target.left.a.y1+target.left.b.y1+target.right.a.y1+target.right.b.y1)/4;
		
		
	}
	
	double distance(Pair<Line> a, Pair<Line> b) {
		// As an aside: Doing all this in Haskell would be something like this:
		// let euclid = sqrt $ (x1-x2 ** 2)+(y1-y2 ** 2),
		//	   xs = [f a | f <- [(.x1), (.y1)], a <- [a.a, a.b, b.a, b.b] in
		//		fold min MAX_INT $ [euclid x1 x2 y1 y2 | x1 <- xs, x2 <- xs, y1 <- ys, y2 <- ys]
		// Which is not only more readable, but much shorter, and more efficient.
		Pair<Double> aEndpoint1 = new Pair<Double>(new Double((a.a.x1+a.b.x1)/2),
													new Double((a.a.y1+a.b.y1)/2));
		Pair<Double> aEndpoint2 = new Pair<Double>(new Double((a.a.x2+a.b.x2)/2),
				new Double((a.a.y2+a.b.y2)/2));
		Pair<Double> bEndpoint1 = new Pair<Double>(new Double((b.a.x1+b.b.x1)/2),
				new Double((b.a.y1+b.b.y1)/2));
		Pair<Double> bEndpoint2 = new Pair<Double>(new Double((b.a.x2+b.b.x2)/2),
				new Double((b.a.y2+b.b.y2)/2));
		double[] close = new double[4];
		close[0] = Math.sqrt(Math.pow(aEndpoint1.a + bEndpoint1.a, 2) 
							+ Math.pow(aEndpoint1.b + bEndpoint1.b, 2));
		close[1] = Math.sqrt(Math.pow(aEndpoint1.a + bEndpoint2.a, 2) 
				+ Math.pow(aEndpoint1.b + bEndpoint2.b, 2));
		close[2] = Math.sqrt(Math.pow(aEndpoint2.a + bEndpoint1.a, 2) 
				+ Math.pow(aEndpoint2.b + bEndpoint1.b, 2));
		close[3] = Math.sqrt(Math.pow(aEndpoint2.a + bEndpoint2.a, 2) 
				+ Math.pow(aEndpoint2.b + bEndpoint2.b, 2));
		return findMin(Double.MAX_VALUE, close);
	}
	
	double findMin(double init, double[] list) {
		double acc = init;
		for (int i=0; i<list.length; i++) {
			acc = Math.min(acc,list[i]);
		}
		return acc;
	}
	
	public Line[] getLines(ITable lineReport) { // This marshals stuff from the NetworkTable to
												// a bunch of line objects.
		double[] x1s = lineReport.getNumberArray("x1", Constants.emptydoubleTable);
		double[] x2s = lineReport.getNumberArray("x2", Constants.emptydoubleTable);
		double[] y1s = lineReport.getNumberArray("y1", Constants.emptydoubleTable);
		double[] y2s = lineReport.getNumberArray("y2", Constants.emptydoubleTable);
		double[] lengths = lineReport.getNumberArray("length", Constants.emptydoubleTable);
		double[] angles = lineReport.getNumberArray("angle", Constants.emptydoubleTable);
		Line[] lineTable = new Line[x1s.length];
		for (int i=0; i<x1s.length; i++) {
			lineTable[i] = new Line();
		}
		for (int i=0; i<x1s.length; i++) {
			lineTable[i].x1 = x1s[i];
		}
		for (int i=0; i<x1s.length; i++) {
			lineTable[i].x2 = x2s[i];
		}
		for (int i=0; i<x1s.length; i++) {
			lineTable[i].y1 = y1s[i];
		}
		for (int i=0; i<x1s.length; i++) {
			lineTable[i].y2 = y2s[i];
		}
		for (int i=0; i<x1s.length; i++) {
			lineTable[i].length = lengths[i];
		}
		for (int i=0; i<x1s.length; i++) {
			lineTable[i].angle = angles[i];
		}
		return lineTable;
	}
	
	private class TargetCandidate {
		public Pair<Line> left, bottom, right;
	}
	
	private class Pair<T> {
		public Pair(){
			;
		}
		public Pair(T arg1, T arg2) {
			a = arg1; b = arg2;
		}

		public T a, b;
	}
	
	private class Line { // This would be a struct, if java weren't stupid.
		@SuppressWarnings("unused")
		public double x1, x2, y1, y2, length, angle;
	}

}
