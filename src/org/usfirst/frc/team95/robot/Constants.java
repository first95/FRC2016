package org.usfirst.frc.team95.robot;

public class Constants {
	
	static final double[] emptydoubleTable = new double[0];
	
	static final double voltageRampRate = 48.0; // in volts/second
	static final boolean useVoltageRamp = true;
	
	static final boolean brakeMode = false;
	
	//for changing -1 - 1 to RPM
	static final double timeserRPM = 4425;
	
	//deadbanding
	static final double deadBand = 0.007;
	
	// Drive PID
	static double P = 0, I = 0, D = 0, F = 0; // These aren't final because they are changed
												// during PID Tuning
	
	//PID tuning speed
	static int magnitude = 0; // See previous note
	
	// Vision Tracking Parallelitude (angles)
	static final double parallelTolerance = 10;
	
	// Vision Tracking Line Closeness (pixels)
	static final double lineDistanceTolerance=10;
}
