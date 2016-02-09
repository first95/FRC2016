package org.usfirst.frc.team95.robot;


import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotMap {
	public static CANTalon left1, left2, right1, right2, light, arm, shoot;
	public static Joystick driveStick;
	public static Drive drive;
	public static ButtonTracker incP, decP, incI, decI, incD, decD, magInc, magDec, incF, decF;
	
	public static void init() {
		// Not actually mapped to the real locations on the robot
    	left1  = new CANTalon(4);
    	left2  = new CANTalon(3);
    	right1  = new CANTalon(2);
    	right2  = new CANTalon(1);
    	light = new CANTalon(5);
    	arm = new CANTalon(6);
    	// Shooter motor
    	shoot = new CANTalon(7);
    	
    	CANTalon[] leftTable = {left1, left2, };//left3};
    	for (CANTalon t : leftTable) {
    		t.setPosition(0);
    		t.enableBrakeMode(Constants.brakeMode);
    		if (Constants.useVoltageRamp) {
    			t.setVoltageRampRate(Constants.voltageRampRate);
    		} else {
    			t.setVoltageRampRate(0.0);
    		}
    		t.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    		t.configEncoderCodesPerRev(1024);
    		
    		t.setIZone(10);
    		
    	}
    	
    	CANTalon[] rightTable = {right1, right2, };//left3};
    	for (CANTalon t : rightTable) {
    		t.setPosition(0);
    		t.enableBrakeMode(Constants.brakeMode);
    		if (Constants.useVoltageRamp) {
    			t.setVoltageRampRate(Constants.voltageRampRate);
    		} else {
    			t.setVoltageRampRate(0.0);
    		}
    		t.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    		t.configEncoderCodesPerRev(1024);
    	}
    	
    	arm.setPosition(0);
    	arm.enableBrakeMode(Constants.brakeMode);
    	if (Constants.useVoltageRamp) {
    		arm.setVoltageRampRate(Constants.voltageRampRate);
    	} else {
    		arm.setVoltageRampRate(0);
    	}
    	arm.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    	arm.configEncoderCodesPerRev(1024);
    	
    	arm.setF(Constants.armF);
    	arm.setPID(Constants.armP, Constants.armI, Constants.armD);
    	arm.changeControlMode(CANTalon.TalonControlMode.Position);
    	arm.enableControl();
    	
    	//Do we need this? Copied the arm stuff but changed the Constants
    	shoot.setPosition(0);
    	shoot.enableBrakeMode(Constants.brakeMode);
    	if (Constants.useVoltageRamp) {
    		shoot.setVoltageRampRate(Constants.voltageRampRate);
    	} else {
    		shoot.setVoltageRampRate(0);
    	}
    	shoot.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    	shoot.configEncoderCodesPerRev(1024);
    	
    	shoot.setF(Constants.shootF);
    	shoot.setPID(Constants.shootP, Constants.shootI, Constants.shootD);
    	shoot.changeControlMode(CANTalon.TalonControlMode.Position);
    	shoot.enableControl();
    	////////////////////
    	
    	left1.setF(Constants.F);
    	left1.setPID(Constants.P, Constants.I, Constants.D);
    	right1.setPID(Constants.P, Constants.I, Constants.D);
    	left1.changeControlMode(CANTalon.TalonControlMode.PercentVbus); // Speed
    	left1.enableControl();
    	left2.changeControlMode(CANTalon.TalonControlMode.Follower);
    	left2.set(4);
    	left2.enableControl();
    	right1.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	right1.reverseSensor(true);
    	right1.enableControl();
    	right2.changeControlMode(CANTalon.TalonControlMode.Follower);
    	right2.set(2);
    	right2.enableControl();
    	
    	
    	
    	driveStick = new Joystick(0);
    	
    	incP = new ButtonTracker(driveStick, 5);
    	decP = new ButtonTracker(driveStick, 10);
    	incI = new ButtonTracker(driveStick, 6);
    	decI = new ButtonTracker(driveStick, 9);
    	incD = new ButtonTracker(driveStick, 7);
    	decD = new ButtonTracker(driveStick, 8);
    	magInc = new ButtonTracker(driveStick, 3);
    	magDec = new ButtonTracker(driveStick, 4);
    	incF = new ButtonTracker(driveStick, 13);
    	decF = new ButtonTracker(driveStick, 14);

    	drive = new Drive(left1, right1);
	}

	public static void testDrive() {
		double setpoint = ((1.0-driveStick.getThrottle()) * 1);
		SmartDashboard.putNumber("Throttle setpoint", setpoint);
		SmartDashboard.putNumber("OutputVoltage", left1.getOutputVoltage());
		
		// on/off step function
		if(driveStick.getRawButton(1)) {
			left1.set(setpoint);
		} else {
			left1.set(0);
		}
	}

}
