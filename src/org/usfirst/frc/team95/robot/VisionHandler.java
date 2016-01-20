package org.usfirst.frc.team95.robot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class VisionHandler {
	double x, y;
	
	static VisionHandler instance;
	
	NetworkTable GRIPTable;
	Comparator<Line> sort = new Comparator<Line>() {
		public int compare(Line l1, Line l2) {
			if (l1.length == l2.length)
				return 0;
			return l1.length > l2.length ? 1 : -1;
		}
	};
	
	ITableListener updater = new ITableListener() {
		@Override
		public void valueChanged(ITable table, String key, Object value, boolean newP) {
			VisionHandler.getInstance().update(table);
		}
	};
	
	public void init() {
		GRIPTable = NetworkTable.getTable("GRIP/myLinesReport");
		GRIPTable.addTableListener(updater);
	}
	
	public static VisionHandler getInstance() {
		if (instance != null)
			return instance;
		instance = new VisionHandler();
		return getInstance();
	}

	public boolean horizontalP(double degrees) {
		degrees = Math.abs((degrees - 45) % 180);
		return degrees > 90;
	}
	
	public void update(ITable table) {
		Line[] lineTable = getLines(table);
		
		ArrayList<Line> lines = new ArrayList<Line>();
		lines.addAll(Arrays.asList(lineTable));
		lines.sort(sort);
		ArrayList<Line> horizontal = new ArrayList<Line>();
		ArrayList<Line> vertical = new ArrayList<Line>();
		
		for (Line line : lines) {
			if (horizontalP(line.angle)) {
				horizontal.add(line);
			} else {
				vertical.add(line);
			}
		}
		
		vertical.sort(sort);
		double x = 0;
		for (Line l : vertical.subList(0, 4)) {
			x += l.x1;
			x += l.x2;
		}
		x /= 8; this.x = x;
		
		horizontal.sort(sort);
		double y = 0;
		for (Line l : horizontal.subList(0, 2)) {
			y += l.y1;
			y += l.y2;
		}
		y /= 8; this.y = y;
		
	}
	
	public Line[] getLines(ITable lineReport) {
		double[] x1s = lineReport.getNumberArray("x1", Constants.emptydoubleTable);
		double[] x2s = lineReport.getNumberArray("x2", Constants.emptydoubleTable);
		double[] y1s = lineReport.getNumberArray("y1", Constants.emptydoubleTable);
		double[] y2s = lineReport.getNumberArray("y2", Constants.emptydoubleTable);
		double[] lengths = lineReport.getNumberArray("length", Constants.emptydoubleTable);
		double[] areas = lineReport.getNumberArray("area", Constants.emptydoubleTable);
		Line[] lineTable = new Line[x1s.length];
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
			lineTable[i].angle = areas[i];
		}
		return lineTable;
	}
	
	private class Line {
		public double x1, x2, y1, y2, length, angle;
	}

}
