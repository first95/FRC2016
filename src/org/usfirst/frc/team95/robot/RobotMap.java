package org.usfirst.frc.team95.robot;

import org.usfirst.frc.team95.robot.auto.Align;
import org.usfirst.frc.team95.robot.auto.BumpSetpoint;
import org.usfirst.frc.team95.robot.auto.ChargeAndShoot;
import org.usfirst.frc.team95.robot.auto.Coast;
import org.usfirst.frc.team95.robot.auto.EjectBall;
import org.usfirst.frc.team95.robot.auto.OpenLoopArm;
import org.usfirst.frc.team95.robot.auto.PickUp;
import org.usfirst.frc.team95.robot.auto.PreserveHeading;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotMap {
	public static CANTalon left1, left2, left3, right1, right2, right3, arm2, shoot1L, shoot1R, shoot2L, shoot2R, light;
	public static CANTalon arm1;
	public static Joystick driveStick, weaponStick;
	public static Drive drive;
	public static ButtonTracker incP, decP, incI, decI, incD, decD, magInc, magDec, incF, decF,
			preserveHeadingButtonTracker, fireL, fireR, pickUp, align, relign, armGroundedFront, armGroundedBack,
			limitOveride, eject, zero, coast, up, down, upSmall, downSmall, upBig, downBig;
	public static PreserveHeading preserveHeadingAutoMove;
	public static ArduPilotAttitudeMonitor am = null;
	public static Object driveLock = null;
	public static Object armLock = null;
	public static PowerDistributionPanel pdb = new PowerDistributionPanel();

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
		// arm1.setForwardLimit(Constants.armGroundedFront);
		// arm1.setReverseLimit(Constants.armGroundedBack);
		arm2 = new CANTalon(8);
		// Shooter motors, shoot 1 is stage 1 and shoot 2 is for stage 2
		shoot1L = new CANTalon(9);
		shoot1R = new CANTalon(10);
		shoot2L = new CANTalon(11);
		shoot2R = new CANTalon(12);

		// ring light for vision
		// light = new CANTalon(5);

		am = new ArduPilotAttitudeMonitor();

		// talon setup
		CANTalon[] talonTable = { left1, left2, left3, right1, right2, right3 };
		for (CANTalon t : talonTable) {
			brakeAndVoltage(t);
			t.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
			t.configEncoderCodesPerRev(256);

			t.setIZone(10);

		}

		brakeAndVoltage(arm1);
		// arm1.setVoltageRampRate(rampRate);
		arm1.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Absolute);
		arm1.reverseSensor(true);
		arm1.setPosition(Constants.armPowerOnPlace);
		arm1.set(arm1.getPosition());
		arm1.setF(Constants.armF);
		arm1.setPID(Constants.armP, Constants.armI, Constants.armD);
		arm1.changeControlMode(CANTalon.TalonControlMode.Position);
		// arm1.setForwardSoftLimit(Constants.armGroundedBack);
		arm1.enableForwardSoftLimit(false);
		arm2.enableForwardSoftLimit(false);
		// arm1.setReverseSoftLimit(Constants.armGroundedFront);
		arm1.enableReverseSoftLimit(false);
		arm2.enableReverseSoftLimit(false);

		arm1.enableControl();
		// arm1.setAllowableClosedLoopErr(0.005);
		brakeAndVoltage(arm2);
		arm2.changeControlMode(CANTalon.TalonControlMode.Follower);
		arm2.set(7);
		arm2.enableControl();
		arm2.reverseOutput(true);
		// can't invert a follower

		// Do we need this? Copied the arm stuff but changed the Constants
		brakeAndVoltage(shoot1L);
		shoot1L.changeControlMode(TalonControlMode.PercentVbus);
		shoot1L.enableControl();
		shoot1L.set(0);

		brakeAndVoltage(shoot1R);
		shoot1R.changeControlMode(CANTalon.TalonControlMode.Follower);
		shoot1R.set(9);
		shoot1R.reverseOutput(true);
		shoot1R.enableControl();

		brakeAndVoltage(shoot2L);
		shoot2L.enableControl();

		brakeAndVoltage(shoot2R);
		shoot2R.enableControl();

		left1.setF(Constants.F);
		left1.setPID(Constants.P, Constants.I, Constants.D);
		right1.setF(Constants.F);
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
		// right1.reverseSensor(true); DONT DO THIS
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
		magInc = new ButtonTracker(driveStick, 4);
		magDec = new ButtonTracker(driveStick, 3);
		incF = new ButtonTracker(driveStick, 13);
		decF = new ButtonTracker(driveStick, 14);
		preserveHeadingAutoMove = new PreserveHeading();
		preserveHeadingButtonTracker = new ButtonTracker(driveStick, 2, preserveHeadingAutoMove);
		fireL = new ButtonTracker(weaponStick, 11, new ChargeAndShoot());
		// fireR = new ButtonTracker(weaponStick, 5, new ChargeAndShoot());
		pickUp = new ButtonTracker(weaponStick, 12, new PickUp());
		// eject = new ButtonTracker(weaponStick, 5, new EjectBall());

		align = new ButtonTracker(weaponStick, 4, new Align());

		relign = new ButtonTracker(weaponStick, 3, new PreserveHeading(0));

		armGroundedBack = new ButtonTracker(weaponStick, 16);
		armGroundedFront = new ButtonTracker(weaponStick, 15);
		zero = new ButtonTracker(weaponStick, 14);

		limitOveride = new ButtonTracker(weaponStick, 8);

		coast = new ButtonTracker(weaponStick, 1, new Coast());
		upSmall = new ButtonTracker(weaponStick, 5, new BumpSetpoint(-0.0075));
		downSmall = new ButtonTracker(weaponStick, 10, new BumpSetpoint(0.0075));
		up = new ButtonTracker(weaponStick, 6, new BumpSetpoint(-0.015));
		down = new ButtonTracker(weaponStick, 9, new BumpSetpoint(0.015));
		upBig = new ButtonTracker(weaponStick, 7, new BumpSetpoint(-0.15));
		downBig = new ButtonTracker(weaponStick, 8, new BumpSetpoint(0.15));

		drive = new Drive(left1, right1);
	}

	public static void testDrive() {
		double setpoint = ((((driveStick.getThrottle() * -1) + 1) / 2) * Constants.timeserRPM);
		SmartDashboard.putNumber("Throttle setpoint", setpoint);
		SmartDashboard.putNumber("spood", (left1.get()));
		// SmartDashboard.putNumber("spood", (right1.get()));

		// on/off step function
		if (driveStick.getRawButton(1)) {
			left1.set(setpoint);
			// right1.set(setpoint);
		} else if (driveStick.getRawButton(11)) {
			left1.set(-setpoint);
			// right1.set(-setpoint);
		} else {
			left1.set(0);
			// right1.set(0);
		}
	}

	static public void brakeAndVoltage(CANTalon t) {
		t.setPosition(0);
		t.enableBrakeMode(Constants.brakeMode);
		if (Constants.useVoltageRamp) {
			t.setVoltageRampRate(Constants.voltageRampRate);
		} else {
			t.setVoltageRampRate(0);
		}
	}

}
