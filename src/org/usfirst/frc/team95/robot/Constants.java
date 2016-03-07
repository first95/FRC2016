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

	// for changing -1 - 1 to RPM
	static final public double timeserRPM = 430.89;

	// deadbanding
	static final public double deadBand = 0.05;
	static final public double horDeadBand = 0.07;

	// Drive PID
	// static public double P = 4, I = 50 * 1e-6, D = 200, F = 1.39; //left
	// during PID Tuning
	static public double P = 6, I = 400 * 1e-6, D = 500, F = 1.69; // right
	static public double armP = 0.6, armI = (5.7 * 1e-6), armD = 0, armF = 0;

	// arm when level 0.027 .03

	// Shoot PID do we need this? Set initial PID values for the shooter motor
	static public double shootP = 0, shootI = 0, shootD = 0, shootF = 0;
	/////////////////////////////

	// PID tuning speed
	static public int magnitude = 0; // See previous note

	// Vision Tracking Parallelitude (angles)
	static final public double parallelTolerance = 10;

	// Vision Tracking Line Closeness (pixels)
	static final public double lineDistanceTolerance = 10;

	// Vision - Camera specific conversions
	static final public double horizontalWidth = 640; // (Pixels)
	static final public double verticalHeight = 480; // (Pixels)
	static final public double horizontalPixelsToDegrees = (53 / horizontalWidth) / 1.3;
	static final public double verticalPixelsToDegrees = (40 / verticalHeight) / 1.3;

	static public double headingPreservationP = 4.2;
	static public double headingPreservationI = 0.0000003;
	static public double headingPreservationD = 0.05;
	static final public double headingPreservationClosenessTolerance = 0;
	// Autonomous
	static final public double autonomousRotateSpeed = 0.30;

	static final public double armPowerOnPlace = Math.PI / 2 - 0.4537852;
	static final public double armGroundedBack = Math.PI + (6 * Math.PI / 180);
	static final public double armGroundedFront = -28.7 * Math.PI / 180;
	static public double encoderOffset = (armPowerOnPlace) + 0.413;

	// Camera offsets
	public final static double cameraHorizontalOffset = 0;
	static final public double cameraVerticalOffset = -0.2;
	static final public double rangeAdjustment = 0;

	static final public double setPointChangeLimit = 0.15;
	public static final double GRIPDeadTime = 2;
}
