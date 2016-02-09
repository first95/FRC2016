package org.usfirst.frc.team95.robot;

public class Constants {
	
	// The distance from one wheel to the other.
	static final public double robotWidth = 25;
	static final public double wheelDiameter = 6;
	
	static final public double[] emptydoubleTable = new double[0];
	
	static final public double voltageRampRate = 48.0; // in volts/second
	static final public boolean useVoltageRamp = true;
	
	static final public boolean brakeMode = false;
	
	//for changing -1 - 1 to RPM
	static final public double timeserRPM = 4425;
	
	//deadbanding
	static final public double deadBand = 0.007;
	
	// Drive PID
	static public double P = 0, I = 0, D = 0, F = 0; // These aren't final because they are changed
												// during PID Tuning
	
	//PID tuning speed
	static public int magnitude = 0; // See previous note
	
	// Vision Tracking Parallelitude (angles)
	static final public double parallelTolerance = 10;
	
	// Vision Tracking Line Closeness (pixels)
	static final public double lineDistanceTolerance = 50;
	
	// Vision - Camera specific conversions
	static final public double horizontalPixelsToDegrees = 0.1046875;
	static final public double verticalPixelsToDegrees = 0.10625;
	static final public double horizontalWidth = 640; // (Pixels)
	static final public double verticalHeight = 480; // (Pixels)
	
	// Autonomous
	static final public double autonomousRotateSpeed=0.15;
}
