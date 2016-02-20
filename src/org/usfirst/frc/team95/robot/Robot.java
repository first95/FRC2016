
package org.usfirst.frc.team95.robot;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.mavlink.*;
import org.mavlink.messages.*;
import org.usfirst.frc.team95.robot.auto.Auto;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot
{
	double headingToPreserve;
	CameraServer cameraServer;
	ArrayList<PollableSubsystem> updates = new ArrayList<PollableSubsystem>();
	ArrayList<Auto> runningAutonomousMoves = new ArrayList<Auto>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit()
	{
		RobotMap.init();
		VisionHandler.getInstance().init();

		// cameraServer = CameraServer.getInstance();
		// cameraServer.startAutomaticCapture("/dev/video0");

		for (ButtonTracker b : ButtonTracker.usedNumbers.get(RobotMap.driveStick))
		{
			if (b != null)
			{
				updates.add(b);
			}
		}
		
		for (ButtonTracker b : ButtonTracker.usedNumbers.get(RobotMap.weaponStick)) {
			if (b != null) {
				updates.add(b);
			}
		}

		updates.add(RobotMap.am);
		
		for (PollableSubsystem p: updates) {
			p.init();
		}

	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic()
	{
		for (Auto x : runningAutonomousMoves)
		{
			x.update();
			if (x.done())
			{
				x.stop();
				runningAutonomousMoves.remove(x);
			}
		}
	}

	public void commonPeriodic()
	{
		SmartDashboard.putNumber("Test", 5);

		for (PollableSubsystem p : updates)
		{
			p.update();
		}
		
		if (RobotMap.driveLock != null) {
			SmartDashboard.putString("Drive Lock:", RobotMap.driveLock.getClass().getName());
		} else {
			SmartDashboard.putString("Drive Lock:", "Nothing");
		}
		
		SmartDashboard.putNumber("Pitch", RobotMap.am.getPitch());
		SmartDashboard.putNumber("Roll", RobotMap.am.getRoll());
		SmartDashboard.putNumber("Yaw", RobotMap.am.getYaw());
		
		SmartDashboard.putNumber("ardutime", ArduPilotAttitudeMonitor.time);
		
		SmartDashboard.putNumber("P", Constants.headingPreservationP);
		SmartDashboard.putNumber("10^6*I", Constants.headingPreservationI * (1e6));
		SmartDashboard.putNumber("D", Constants.headingPreservationD);
		SmartDashboard.putNumber("F", Constants.F);

		SmartDashboard.putNumber("encoder position", RobotMap.left1.getEncPosition());

		SmartDashboard.putNumber("Left Setpoint", RobotMap.left1.getSetpoint());
		SmartDashboard.putNumber("Left Speed", -1 * RobotMap.left1.getSpeed());

		SmartDashboard.putNumber("Target X", VisionHandler.getInstance().x);
		// System.out.println(VisionHandler.getInstance().x);
		SmartDashboard.putNumber("Target Y", VisionHandler.getInstance().y);
		SmartDashboard.putNumber("Current Heading", RobotMap.am.getYaw());
		SmartDashboard.putNumber("Heading To Preserve", headingToPreserve);
		SmartDashboard.putNumber("headingToPreserve Yaw = ", headingToPreserve);
		SmartDashboard.putNumber("weapon throttle",((RobotMap.weaponStick.getThrottle()*-1)+1));
		SmartDashboard.putNumber("driver throttle",((RobotMap.driveStick.getThrottle()*-1)+1));
		// System.out.println(VisionHandler.getInstance().y);
		// System.out.println("----");
		// SmartDashboard.putNumber("Right Setpoint",
		// RobotMap.right1.getSetpoint());
		// SmartDashboard.putNumber("Right Speed", RobotMap.right1.getSpeed());

	}

	/**
	 * This function is called periodically during operator control
	 */
	public void disabledPeriodic()
	{
		commonPeriodic();
	}

	@Override
	public void teleopInit()
	{
		RobotMap.light.set(0.5);

	}

	public void teleopPeriodic()
	{
		commonPeriodic();
		// RobotMap.drive.arcadeDrive(RobotMap.driveStick);
		RobotMap.armDrive.ArmControll(RobotMap.weaponStick);
		// Run all automoves
		for (Auto x : runningAutonomousMoves)
		{
			x.update();
			if (x.done())
			{
				x.stop();
				runningAutonomousMoves.remove(x);
			}
		}

		// RobotMap.testDrive();
		PIDTuner();
		if (RobotMap.driveLock == null)
		{
			RobotMap.drive.arcadeDrive(RobotMap.driveStick);
		}
		
		RobotMap.shoot1R.setSetpoint(RobotMap.shoot2R.get());
		RobotMap.arm2.set(RobotMap.arm1.get());
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic()
	{

	}

	public void PIDTuner()
	{
		// if (RobotMap.driveStick.getRawButton(3)) {
		// Constants.magnitude = Constants.magnitude - 1;
		// }else if (RobotMap.driveStick.getRawButton(4)) {
		// Constants.magnitude = Constants.magnitude + 1;
		// }

		if (RobotMap.magInc.justPressedp())
		{
			Constants.magnitude++;
		}
		else if (RobotMap.magDec.justPressedp())
		{
			Constants.magnitude--;
		}
		double d = Math.pow(10, Constants.magnitude);
		boolean changed = false;
		if (RobotMap.decP.justPressedp())
		{
			Constants.headingPreservationP = Constants.headingPreservationP - (.1 * d);
			changed = true;
		}
		else if (RobotMap.incP.justPressedp())
		{
			Constants.headingPreservationP = Constants.headingPreservationP + (.1 * d);
			changed = true;
		}
		if (RobotMap.decI.justPressedp())
		{
			Constants.headingPreservationI = Constants.headingPreservationI - (.0000001 * d);
			changed = true;
		}
		else if (RobotMap.incI.justPressedp())
		{
			Constants.headingPreservationI = Constants.headingPreservationI + (.0000001 * d);
			changed = true;
		}
		if (RobotMap.decD.justPressedp())
		{
			Constants.headingPreservationD = Constants.headingPreservationD - (.1 * d);
			changed = true;
		}
		else if (RobotMap.incD.justPressedp())
		{
			Constants.headingPreservationD = Constants.headingPreservationD + (.1 * d);
			changed = true;
		}
		if (RobotMap.incF.justPressedp())
		{
			Constants.F = Constants.F + (.1 * d);
			changed = true;
		}
		else if (RobotMap.decF.justPressedp())
		{
			Constants.F = Constants.F - (.1 * d);
			changed = true;
		}
		if (changed)
		{
			RobotMap.left1.setPID(Constants.P, Constants.I, Constants.D);
			RobotMap.right1.setPID(Constants.P, Constants.I, Constants.D);
		}

	}

	public void disabledInit()
	{

	}

}
