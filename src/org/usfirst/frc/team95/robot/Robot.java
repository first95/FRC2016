
package org.usfirst.frc.team95.robot;

import java.util.ArrayList;

import org.usfirst.frc.team95.robot.auto.AlignAndShoot;
import org.usfirst.frc.team95.robot.auto.Auto;
import org.usfirst.frc.team95.robot.auto.ConfigMove;
import org.usfirst.frc.team95.robot.auto.Nothing;
import org.usfirst.frc.team95.robot.auto.OverRoughTerrain;
import org.usfirst.frc.team95.robot.auto.RotateBy;
import org.usfirst.frc.team95.robot.auto.TimedMove;
import org.usfirst.frc.team95.robot.auto.TimedStraightMove;
import org.usfirst.frc.team95.robot.auto.DropArm;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	double headingToPreserve;
	CameraServer cameraServer;
	ArrayList<PollableSubsystem> updates = new ArrayList<PollableSubsystem>();
	ArrayList<Auto> runningAutonomousMoves = new ArrayList<Auto>();

	SendableChooser a, b, c;

	Auto move;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		RobotMap.init();
		VisionHandler.getInstance().init();

		// cameraServer = CameraServer.getInstance();
		// cameraServer.startAutomaticCapture("/dev/video0");

		for (ButtonTracker b : ButtonTracker.usedNumbers.get(RobotMap.driveStick)) {
			if (b != null) {
				updates.add(b);
			}
		}

		for (ButtonTracker b : ButtonTracker.usedNumbers.get(RobotMap.weaponStick)) {
			if (b != null) {
				updates.add(b);
			}
		}

		updates.add(RobotMap.am);
		// updates.add(RobotMap.arm1);

		for (PollableSubsystem p : updates) {
			p.init();
		}

		a = new SendableChooser();
		b = new SendableChooser();
		c = new SendableChooser();
		a.addDefault("None", new Nothing());
		//a.addObject("Go Under Low Bar", new UnderLowBar());
		a.addObject("Cross other Obstacle", new OverRoughTerrain());
		a.addObject("Go Forward", new TimedMove(0.3, 0.3, 5));
		a.addObject("Turn 45 Right", new RotateBy(Math.PI / 4));
		a.addObject("Turn 45 Left", new RotateBy(-Math.PI / 4));
		a.addObject("Autoaim & Shoot", new AlignAndShoot());
		a.addObject("Drop Arm", new DropArm());

		b.addDefault("None", new Nothing());
		//b.addObject("Go Under Low Bar", new UnderLowBar());
		b.addObject("Cross other Obstacle", new OverRoughTerrain());
		b.addObject("Go Forward", new TimedMove(0.5, 0.5, 5));
		b.addObject("Turn 45 Right", new RotateBy(Math.PI / 4));
		b.addObject("Turn 45 Left", new RotateBy(-Math.PI / 4));
		b.addObject("Autoaim & Shoot", new AlignAndShoot());
		b.addObject("Drop Arm", new DropArm());

		c.addDefault("None", new Nothing());
		//c.addObject("Go Under Low Bar", new UnderLowBar());
		c.addObject("Cross other Obstacle", new OverRoughTerrain());
		c.addObject("Go Forward", new TimedMove(0.5, 0.5, 5));
		c.addObject("Turn 45 Right", new RotateBy(Math.PI / 4));
		c.addObject("Turn 45 Left", new RotateBy(-Math.PI / 4));
		c.addObject("Autoaim & Shoot", new AlignAndShoot());
		c.addObject("Drop Arm", new DropArm());

		SmartDashboard.putData("1st", a);
		SmartDashboard.putData("2nd", b);
		SmartDashboard.putData("3rd", c);

	}

	@Override
	public void autonomousInit() {

		System.out.println("Auto INIT");

		Auto am = (Auto) a.getSelected();
		Auto bm = (Auto) b.getSelected();
		Auto cm = (Auto) c.getSelected();
		String picked = "We picked: ";
		picked += am.getClass().getName() + ", ";
		picked += bm.getClass().getName() + ", ";
		picked += cm.getClass().getName();
		DriverStation.reportError(picked, false);
		Auto[] m = { am, bm, cm };

		RobotMap.arm1.setSetpoint(RobotMap.arm1.getPosition());

		//move = new ConfigMove(m);
		move = new TimedStraightMove(0.3, 10);
		move.init();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		for (Auto x : runningAutonomousMoves) {
			// System.out.println("Running " + x.getClass().getName());
			x.update();
			if (x.done()) {
				x.stop();
				runningAutonomousMoves.remove(x);
			}
		}

		System.out.println("Auto Periodic");
		move.update();
	}

	public void commonPeriodic() {
		// SmartDashboard.putNumber("Arm Position:",
		// RobotMap.arm1.getEncPosition());

		for (PollableSubsystem p : updates) {
			p.update();
		}

		if (RobotMap.driveLock != null) {
			SmartDashboard.putString("Drive Lock:", RobotMap.driveLock.getClass().getName());
		} else {
			SmartDashboard.putString("Drive Lock:", "Nothing");
		}

		// SmartDashboard.putNumber("Pitch", RobotMap.am.getPitch());
		// SmartDashboard.putNumber("Roll", RobotMap.am.getRoll());
		// SmartDashboard.putNumber("Yaw", RobotMap.am.getYaw());

		// SmartDashboard.putNumber("ardutime", ArduPilotAttitudeMonitor.time);

		SmartDashboard.putNumber("armSetpoint", RobotMap.arm1.getSetpoint());
		SmartDashboard.putNumber("P", Constants.P);
		SmartDashboard.putNumber("10^6*I", Constants.I * (1e6));
		SmartDashboard.putNumber("D", Constants.D);
		SmartDashboard.putNumber("F", Constants.F);

		SmartDashboard.putNumber("Arm P", RobotMap.arm1.getP());
		SmartDashboard.putNumber("Arm I", RobotMap.arm1.getI());
		SmartDashboard.putNumber("Arm D", RobotMap.arm1.getD());
		SmartDashboard.putNumber("armEncoder", RobotMap.arm1.getPosition());
		SmartDashboard.putNumber("arm Position with Offset", RobotMap.arm1.getPosition() + Constants.encoderOffset);
		// SmartDashboard.putNumber("encoder position",
		// RobotMap.left1.getEncPosition());

		// SmartDashboard.putNumber("Left Setpoint",
		// RobotMap.left1.getSetpoint());
		// SmartDashboard.putNumber("Left Speed", -1 *
		// RobotMap.left1.getSpeed());
		// SmartDashboard.putNumber("right encoder",(RobotMap.right1.getSpeed()
		// * (60/25.6)));
		SmartDashboard.putNumber("Target X", VisionHandler.getInstance().getAimX());
		// System.out.println(VisionHandler.getInstance().x);
		SmartDashboard.putNumber("Target Y", VisionHandler.getInstance().getAimY());
		SmartDashboard.putNumber("Current Heading", RobotMap.am.getYaw());
		SmartDashboard.putNumber("Heading To Preserve", headingToPreserve);
		// SmartDashboard.putNumber("headingToPreserve Yaw = ",
		// headingToPreserve);
		SmartDashboard.putNumber("weapon throttle", ((RobotMap.weaponStick.getThrottle() * -1) + 1));
		SmartDashboard.putNumber("driver throttle", ((RobotMap.driveStick.getThrottle() * -1) + 1));
		// System.out.println(VisionHandler.getInstance().y);
		// System.out.println("----");
		// SmartDashboard.putNumber("Right Setpoint",
		// RobotMap.right1.getSetpoint());
		// SmartDashboard.putNumber("Right Speed", RobotMap.right1.getSpeed());

	}

	/**
	 * This function is called periodically during operator control
	 */
	public void disabledPeriodic() {
		commonPeriodic();
	}

	@Override
	public void teleopInit() {

		// RobotMap.light.set(1);
		if (move != null) {
			move.stop();
		}

		RobotMap.armLock = null;
		
		RobotMap.driveLock = null;

		RobotMap.arm1.setSetpoint(RobotMap.arm1.getPosition());
	}

	public void teleopPeriodic() {
		commonPeriodic();
		// RobotMap.drive.arcadeDrive(RobotMap.driveStick);
		// Run all automoves
		for (Auto x : runningAutonomousMoves) {
			x.update();
			if (x.done()) {
				x.stop();
				runningAutonomousMoves.remove(x);
			}
		}

		// RobotMap.testDrive();
		// PIDTuner();
		if (RobotMap.driveLock == null) {
			RobotMap.drive.arcadeDrive(RobotMap.driveStick);
		}
		
		//RobotMap.shoot2L.set(RobotMap.weaponStick.getThrottle());
		//RobotMap.shoot2R.set(RobotMap.weaponStick.getThrottle());

		/*
		 * if (RobotMap.armGroundedBack.justPressedp()) {
		 * Constants.encoderOffset =
		 * Constants.armGroundedBack-RobotMap.arm1.getPosition();
		 * RobotMap.armDrive.Move(RobotMap.arm1.getPosition()-Constants.
		 * encoderOffset); } else if (RobotMap.armGroundedFront.justPressedp())
		 * { Constants.encoderOffset =
		 * Constants.armGroundedFront-RobotMap.arm1.getPosition();
		 * RobotMap.armDrive.Move(RobotMap.arm1.getPosition()-Constants.
		 * encoderOffset); }
		 */

		/*
		 * if (RobotMap.up.justPressedp()) {
		 * RobotMap.arm1.set(RobotMap.arm1.getSetpoint()+0.01); } if
		 * (RobotMap.down.justPressedp()) {
		 * RobotMap.arm1.set(RobotMap.arm1.getSetpoint()-0.01); }
		 */
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}

	public void PIDTuner() {
		// if (RobotMap.driveStick.getRawButton(3)) {
		// Constants.magnitude = Constants.magnitude - 1;
		// }else if (RobotMap.driveStick.getRawButton(4)) {
		// Constants.magnitude = Constants.magnitude + 1;
		// }

		if (RobotMap.magInc.justPressedp()) {
			Constants.magnitude++;
		} else if (RobotMap.magDec.justPressedp()) {
			Constants.magnitude--;
		}
		double d = Math.pow(10, Constants.magnitude);
		boolean changed = false;
		if (RobotMap.decP.justPressedp()) {
			Constants.headingPreservationP = Constants.headingPreservationP - (.1 * d);
			changed = true;
		} else if (RobotMap.incP.justPressedp()) {
			Constants.headingPreservationP = Constants.headingPreservationP + (.1 * d);
			changed = true;
		}
		if (RobotMap.decI.justPressedp()) {
			Constants.headingPreservationI = Constants.headingPreservationI - (.0000001 * d);
			changed = true;
		} else if (RobotMap.incI.justPressedp()) {
			Constants.headingPreservationI = Constants.headingPreservationI + (.0000001 * d);
			changed = true;
		}
		if (RobotMap.decD.justPressedp()) {
			Constants.headingPreservationD = Constants.headingPreservationD - (.1 * d);
			changed = true;
		} else if (RobotMap.incD.justPressedp()) {
			Constants.headingPreservationD = Constants.headingPreservationD + (.1 * d);
			changed = true;
		}
		if (RobotMap.incF.justPressedp()) {
			Constants.F = Constants.F + (.1 * d);
			changed = true;
		} else if (RobotMap.decF.justPressedp()) {
			Constants.F = Constants.F - (.1 * d);
			changed = true;
		}
		if (changed) {
			RobotMap.left1.setPID(Constants.P, Constants.I, Constants.D);
			RobotMap.right1.setPID(Constants.P, Constants.I, Constants.D);
			RobotMap.left1.setF(Constants.F);
			RobotMap.right1.setF(Constants.F);
		}

	}

	public void disabledInit() {
		if (move != null) {
			move.stop();
		}
	}

}
