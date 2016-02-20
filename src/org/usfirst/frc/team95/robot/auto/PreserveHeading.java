package org.usfirst.frc.team95.robot.auto;

import org.usfirst.frc.team95.robot.Constants;
import org.usfirst.frc.team95.robot.Drive;
import org.usfirst.frc.team95.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PreserveHeading extends HybridAutoDrive
{
	boolean manualHeading = false;
	double headingToPreserve, ySpeed, yawError, zCorrection, timerSens, yawToSpeed;
	Timer timer = new Timer();
	Drive drive;
	
	public PreserveHeading()
	{
		;
	}
	
	public PreserveHeading(double heading) {
		headingToPreserve = heading;
		manualHeading = true;
	}

	@Override
	public void init()
	{
		if (!manualHeading) {
			headingToPreserve = RobotMap.am.getYaw();
		}
	}

	@Override
	public void update()
	{
		if ((RobotMap.driveLock == null || RobotMap.driveLock == this) && !done()) {
			RobotMap.driveLock = this;
			yawError = headingToPreserve - RobotMap.am.getYaw();
			zCorrection = yawError / Math.PI;
			yawToSpeed = (zCorrection*((Constants.headingPreservationUnAgressiveness*-1)+1));
			RobotMap.drive.arcadeDrive(RobotMap.driveStick.getY()*-.05, yawToSpeed);
		} else if (RobotMap.driveLock == this && done()) {
			RobotMap.driveLock = null;
		}
		
		SmartDashboard.putNumber("headingToPreserve Yaw = ", headingToPreserve);
		SmartDashboard.putNumber("weapon throttle",((RobotMap.weaponStick.getThrottle()*-1)+1));
		SmartDashboard.putNumber("yaw error", yawError);
		SmartDashboard.putNumber("yaw to speed", yawToSpeed);
		SmartDashboard.putNumber("z Correction", zCorrection);
		SmartDashboard.putNumber("driver throttle",(((RobotMap.driveStick.getThrottle()*-1)+1))/2);
	}

	@Override
	public void stop()
	{
		RobotMap.drive.arcadeDrive(0, 0);
	}

	@Override
	public boolean done()
	{
		// TODO Auto-generated method stub
		return Math.abs(yawError) < Constants.headingPreservationClosenessTolerance;
	}

	@Override
	public void drive(Joystick driveStick)
	{
		yawError = headingToPreserve - RobotMap.am.getYaw();
		zCorrection = yawError / Math.PI;
		yawToSpeed = (zCorrection*((Constants.headingPreservationUnAgressiveness*-1)+1));
		RobotMap.drive.arcadeDrive(driveStick.getY()*-.05, yawToSpeed);		
		
	}

}
