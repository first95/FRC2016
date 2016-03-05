package org.usfirst.frc.team95.robot.auto;

import org.usfirst.frc.team95.robot.Constants;
import org.usfirst.frc.team95.robot.Drive;
import org.usfirst.frc.team95.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TimedStraightMove extends Auto {
	Timer timer = new Timer();
	double forward, time;
	boolean done = false;
	
	boolean manualHeading = false;
	double headingToPreserve, ySpeed, yawError, zCorrection, timerSens, yawToSpeed, errorAcc;
	//Timer timer = new Timer();
	Drive drive;
	
	public TimedStraightMove(double forward, double time) {
		this.forward = forward; this.time = time;
	}

	@Override
	public void init() {
		timer.reset();
		timer.start();
		if (RobotMap.driveLock == this || RobotMap.driveLock == null) {
			RobotMap.driveLock = this;
			RobotMap.drive.arcadeDrive(forward, 0);
		}
		
		if (!manualHeading) {
			headingToPreserve = RobotMap.am.getYaw();
		}
		errorAcc = 0;
	}

	@Override
	public void update() {
		System.out.println("Update!");
		if (timer.get() > time) {
			done = true;
			stop();
		}
		if ((RobotMap.driveLock == null || RobotMap.driveLock == this)) {
			RobotMap.driveLock = this;
			yawError = headingToPreserve - RobotMap.am.getYaw();
			if (yawError > Math.PI) {
				yawError -= 2*Math.PI;
			} else if (yawError < -Math.PI) {
				yawError += 2*Math.PI;
			}
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
					forward, 
					yawToSpeed);
		}
	}

	@Override
	public void stop() {
		if (RobotMap.driveLock == null || RobotMap.driveLock == this) {
			RobotMap.drive.tankDrive(0, 0);
			RobotMap.driveLock = null;
		}
	}

	@Override
	public boolean done() {
		return done;
	}

}
