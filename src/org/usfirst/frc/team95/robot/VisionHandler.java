package org.usfirst.frc.team95.robot;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionHandler {
	double x, y; // These represent the x and y, in pixels, of the
		// center of the goal.
	
	public double getX() { // Getters!
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	static VisionHandler instance; // This is the only instance of VisionHandler that should be used
	
	Comparator<Line> sort = new Comparator<Line>() { // This sorts Line objects based on length
		public int compare(Line l1, Line l2) { // Hooray for anonymous classes.
			if (l1.length == l2.length)
				return 0;
			return l1.length > l2.length ? 1 : -1;
		}
	};
	
	Runnable visionPoller = new Runnable() {
		@Override
		public void run() {
			while (true) {
				VisionHandler.getInstance().update();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};
	
	VideoCapture cap;
	Mat edges;
	
	public void init() { // This sets everything up to listen!
		System.setProperty("java.library.path", System.getProperty("java.library.path")+
				":/usr/local/lib/lib_OpenCV/");
		
		System.out.println(System.getProperty("java.library.path"));
		
		cap = new VideoCapture(0);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		edges = new Mat();
		if (!cap.isOpened()) {
			System.err.println("Problems opening camera.");
		}
		new Thread(visionPoller).start();
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
	
	public void update() { // This does the workhorsing of the updates
		ArrayList<Line> lines; // This would be a Set, if I could get sets working.
		ArrayList<Line> horizontal = new ArrayList<Line>(); // Again, sets.
		ArrayList<Line> vertical = new ArrayList<Line>();
		
		Mat frame = new Mat();
		cap.grab();
		cap.retrieve(frame);
		
		Imgproc.cvtColor(frame, edges, Imgproc.COLOR_BGR2GRAY);
		Imgproc.GaussianBlur(edges, edges, new Size(7,7), 1.5, 1.5);
		Imgproc.Canny(edges, edges, 0, 30);
		Mat lineMat = new Mat();
		Imgproc.HoughLinesP(edges, lineMat, 1, Math.PI/180, 100);
		
		lines = getLines(lineMat);
		
		for (Line line : lines) { // This splits them up.
			if (horizontalP(line.angle)) {
				horizontal.add(line);
			} else {
				vertical.add(line);
			}
		}
		
		// This section finds the average of the endpoints of the likely goal lines.
		vertical.sort(sort);
		horizontal.sort(sort);
		try {
			double n = average(vertical.subList(0, 4), Line.class.getField("x1"));
			this.x = n + average(vertical.subList(0,  4), Line.class.getField("x2"));
			this.x /= 2;
			this.y = n; // Note: If this mis-aims vertically, this is the culpret.
						// It assumes that x1 should be the endpoint closer to the top-left corner.
						// If that's wrong, then this should be changed.
		} catch (NoSuchFieldException | SecurityException e) {
			System.out.println("Tell Daroc that the VisionHandler is misbehaving. (C)");
		}
		
		
		
	}
	
	public ArrayList<Line> getLines(Mat lineMat) { // This marshals stuff from the Mat to
												// a bunch of line objects.
		ArrayList<Line> lines = new ArrayList<Line>();
		
		for (int i = 0; i < lineMat.size().height; i++) {
			Line l = new Line();
			l.x1 = lineMat.get(i, 0)[0];
			l.y1 = lineMat.get(i, 1)[0];
			l.x2 = lineMat.get(i, 2)[0];
			l.y2 = lineMat.get(i, 3)[0];
			l.length = Math.sqrt(Math.pow(l.x2-l.x1, 2) + Math.pow(l.y2-l.y1, 2));
			l.angle = Math.atan((l.y2-l.y1)/(l.x2-l.x1));
			lines.add(l);
		}
		
		return lines;
	}
	
	private class Line { // This would be a struct, if java weren't stupid.
		public double x1, x2, y1, y2, length, angle;
	}

}
