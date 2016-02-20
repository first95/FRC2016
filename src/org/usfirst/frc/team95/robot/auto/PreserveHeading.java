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
	double headingToPreserve, ySpeed, yawError, zCorrection, timerSens, yawToSpeed, errorAcc;
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
		if (RobotMap.driveLock == null) {
			RobotMap.driveLock = this;
		}
		if (!manualHeading) {
			headingToPreserve = RobotMap.am.getYaw();
		}
		errorAcc = 0;
	}

	@Override
	public void update()
	{
		if ((RobotMap.driveLock == null || RobotMap.driveLock == this)) {
			RobotMap.driveLock = this;
			yawError = headingToPreserve - RobotMap.am.getYaw();
			errorAcc += yawError;
			zCorrection = yawError / Math.PI;
			double a = zCorrection*Constants.headingPreservationP;
			SmartDashboard.putNumber("P Term: ", a);
			yawToSpeed = a;
			yawToSpeed += RobotMap.am.getYawRate()*Constants.headingPreservationD;
			yawToSpeed += errorAcc*Constants.headingPreservationI;
			if (yawToSpeed > Constants.autonomousRotateSpeed) {
				yawToSpeed = Constants.autonomousRotateSpeed;
			} else if (yawToSpeed < -Constants.autonomousRotateSpeed) {
				yawToSpeed = -Constants.autonomousRotateSpeed;
			}
			RobotMap.drive.arcadeDrive(
					RobotMap.driveStick.getY()*(((RobotMap.driveStick.getThrottle()*-1)+1)/-2), 
					yawToSpeed);
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
		if (RobotMap.driveLock == this) {
			RobotMap.driveLock = null;
		}
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
		;
	}
	
	double diminish(double a, double b) {
		b = Math.abs(b);
		if (Math.abs(a) < b) {
			return 0;
		} else if (a > 0) {
			return a - b;
		} else if (a < 0) {
			return a + b;
		}
		return 0;
	}

}
