package org.usfirst.frc.team95.robot;

public class Constants {
	
	static double[] emptydoubleTable = new double[0];
	
	static double voltageRampRate = 48.0; // in volts/second
	static boolean useVoltageRamp = true;
	
	static boolean brakeMode = false;
	
	//for changing -1 - 1 to RPM
	static double timeserRPM = 4425;
	
	//deadbanding
	static double deadBand = 0.007;
	
	// Drive PID
	static double P = 0, I = 0, D = 0, F = 0;
	
	//PID tuning speed
	static int magnitude = 0;
}
