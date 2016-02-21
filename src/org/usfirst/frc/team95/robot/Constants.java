package org.usfirst.frc.team95.robot;

public class Constants {
	
	// The distance from one wheel to the other.
	static final public double robotWidth = 25;
	static final public double wheelDiameter = 6;
	static final public double cameraDistanceToPivot = 36; // inches
	static final public double shooterLength = 40;
	static final public double goalHeight = 96;
	static final public double gravity = 386.1; // Inches per s^2
	static final public double shootVelocityToRPM = 1;
	
	static final public double[] emptydoubleTable = new double[0];
	
	static final public double voltageRampRate = 24.0; // in volts/second
	static final public boolean useVoltageRamp = true;
	
	static final public boolean brakeMode = true;
	
	//for changing -1 - 1 to RPM
	static final public double timeserRPM = 430.89;
	
	//deadbanding
	static final public double deadBand = 0.007;
	static final public double horDeadBand = 0.01;
	
	//tolerance for shooter speed
	static final public double shootPowTol = .1;
	
	// Drive PID
	static public double P = 0, I = 0, D = 0, F = 1.39; // These aren't final because they are changed
												// during PID Tuning
	static public double armP = 0.6, armI = (5.7 * 1e-6), armD = 0, armF = 0;
	
	//arm when level 0.027       .03
	
	// Shoot PID do we need this? Set initial PID values for the shooter motor
	static public double shootP = 0, shootI = 0, shootD = 0, shootF = 0;
	/////////////////////////////
	
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
	
	static final public double headingPreservationUnAgressiveness = 7/8;
	static final public double headingPreservationD = 0.5;
	static final public double headingPreservationClosenessTolerance = 0;
	// Autonomous
	static final public double autonomousRotateSpeed=0.15;
}
