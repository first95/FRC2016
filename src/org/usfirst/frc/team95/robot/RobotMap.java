package org.usfirst.frc.team95.robot;


import org.usfirst.frc.team95.robot.auto.ChargeAndShoot;
import org.usfirst.frc.team95.robot.auto.PickUp;
import org.usfirst.frc.team95.robot.auto.PreserveHeading;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotMap {
	public static CANTalon left1, left2, left3, right1, right2, right3, arm1, arm2, shoot1L, shoot1R, shoot2L, shoot2R, light;
	public static Joystick driveStick, weaponStick;
	public static Drive drive;
	public static ArmDrive armDrive;
	public static ButtonTracker incP, decP, incI, decI, incD, decD, magInc, magDec, incF, decF, preserveHeadingButtonTracker, fire, pickUp;
	public static PreserveHeading preserveHeadingAutoMove;
	public static ArduPilotAttitudeMonitor am = null;
	
	public static void init() {
		// drive motors
    	left1 = new CANTalon(1);
    	left2 = new CANTalon(2);
    	left3 = new CANTalon(3);
    	right1 = new CANTalon(4);
    	right2 = new CANTalon(5);
    	right3 = new CANTalon(6);
    	
    	// arm shoulder motors
    	arm1 = new CANTalon(7);
    	arm2 = new CANTalon(8);
    	// Shooter motors, shoot 1 is stage 1 and shoot 2 is for stage 2
    	shoot1L = new CANTalon(9);
    	shoot1R = new CANTalon(10);
    	shoot2L = new CANTalon(11);
    	shoot2R = new CANTalon(12);
    	
    	// ring light for vision
    	light = new CANTalon(13);
    	
    	am = new ArduPilotAttitudeMonitor();
    	
    	CANTalon[] leftTable = {left1, left2, left3};
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
    	
    	CANTalon[] rightTable = {right1, right2, right3};
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
    	
    	arm1.setPosition(0);
    	arm1.enableBrakeMode(Constants.brakeMode);
    	if (Constants.useVoltageRamp) {
    		arm1.setVoltageRampRate(Constants.voltageRampRate);
    	} else {
    		arm1.setVoltageRampRate(0);
    	}
    	arm1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    	arm1.configEncoderCodesPerRev(1024);
    	
    	arm1.setF(Constants.armF);
    	arm1.setPID(Constants.armP, Constants.armI, Constants.armD);
    	//arm1.changeControlMode(CANTalon.TalonControlMode.Position);
    	arm1.enableControl();
    	
    	//arm2.changeControlMode(CANTalon.TalonControlMode.Follower);
    	arm2.set(0);
    	arm2.enableControl();
    	arm2.setInverted(true);
    	//can't invert a follower
    	arm2.set(arm1.get());
    	//Do we need this? Copied the arm stuff but changed the Constants
    	shoot1L.setPosition(0);
    	shoot1L.enableBrakeMode(Constants.brakeMode);
    	if (Constants.useVoltageRamp) {
    		shoot1L.setVoltageRampRate(Constants.voltageRampRate);
    	} else {
    		shoot1L.setVoltageRampRate(0);
    	}
    	shoot1L.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    	shoot1L.configEncoderCodesPerRev(1024);
    	
    	shoot1L.setF(Constants.shootF);
    	shoot1L.setPID(Constants.shootP, Constants.shootI, Constants.shootD);
    	//shoot1L.changeControlMode(CANTalon.TalonControlMode.Position);
    	shoot1L.enableControl();
    	
    	//shoot1R.changeControlMode(CANTalon.TalonControlMode.Follower);
    	shoot1R.setInverted(true);
    	shoot1R.set(0);
    	shoot1R.enableControl();
    	
    	shoot2L.setPosition(0);
    	shoot2L.enableBrakeMode(Constants.brakeMode);
    	if (Constants.useVoltageRamp) {
    		shoot2L.setVoltageRampRate(Constants.voltageRampRate);
    	} else {
    		shoot2L.setVoltageRampRate(0);
    	}
    	shoot2L.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    	shoot2L.configEncoderCodesPerRev(1024);
    	
    	shoot2L.setF(Constants.shootF);
    	shoot2L.setPID(Constants.shootP, Constants.shootI, Constants.shootD);
    	//shoot2L.changeControlMode(CANTalon.TalonControlMode.Position);
    	shoot2L.enableControl();
    	
    	shoot2R.changeControlMode(CANTalon.TalonControlMode.Follower);
    	shoot2R.set(11);
    	//shoot2R.setInverted(true);
    	shoot2R.enableControl();
    	
    	
    	left1.setF(Constants.F);
    	left1.setPID(Constants.P, Constants.I, Constants.D);
    	right1.setPID(Constants.P, Constants.I, Constants.D);
    	left1.changeControlMode(CANTalon.TalonControlMode.PercentVbus); // Speed
    	left1.enableControl();
    	left2.changeControlMode(CANTalon.TalonControlMode.Follower);
    	left2.set(1);
    	left2.enableControl();
    	left3.changeControlMode(CANTalon.TalonControlMode.Follower);
    	left3.set(1);
    	left3.enableControl();
    	right1.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	right1.reverseSensor(true);
    	right1.enableControl();
    	right2.changeControlMode(CANTalon.TalonControlMode.Follower);
    	right2.set(4);
    	right2.enableControl();
    	right3.changeControlMode(CANTalon.TalonControlMode.Follower);
    	right3.set(4);
    	right3.enableControl();
    	
    	driveStick = new Joystick(0);
    	weaponStick = new Joystick(1);
    	
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
    	preserveHeadingAutoMove = new PreserveHeading();
    	preserveHeadingButtonTracker = new ButtonTracker(driveStick, 2, preserveHeadingAutoMove);
    	//fire = new ButtonTracker(weaponStick, 1, new ChargeAndShoot());
    	pickUp = new ButtonTracker(weaponStick, 2, new PickUp());
    	
    	armDrive = new ArmDrive(arm1);
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
