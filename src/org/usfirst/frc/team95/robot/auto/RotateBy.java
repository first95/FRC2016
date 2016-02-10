package org.usfirst.frc.team95.robot.auto;

import org.usfirst.frc.team95.robot.Constants;
import org.usfirst.frc.team95.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;

public class RotateBy extends Auto {
	double angle, distance, time;
	Timer timer = new Timer();
	boolean done = false;
	
	
	public RotateBy(double angle) {
		this.angle = angle;
		distance = angle / 180 * Math.PI * Constants.robotWidth;
		time = distance / Constants.autonomousRotateSpeed;
	}

	@Override
	public void init() {
		timer.reset();
		timer.start();
		RobotMap.drive.tankDrive(Constants.autonomousRotateSpeed/Constants.timeserRPM, 0);
	}

	@Override
	public void update() {
		if (timer.get() > time) {
			done = true;
			RobotMap.drive.tankDrive(0, 0);
		}
		
	}

	@Override
	public void stop() {
		RobotMap.drive.tankDrive(0,0);
		
	}

	@Override
	public boolean done() {
		return done;
	}
	

}
