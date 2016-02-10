package org.usfirst.frc.team95.robot.auto;

import org.usfirst.frc.team95.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;

public class TimedMove extends Auto {
	Timer timer = new Timer();
	double left, right, time;
	boolean done = false;
	
	public TimedMove(double left, double right, double time) {
		this.left = left; this.right = right; this.time = time;
	}

	@Override
	public void init() {
		timer.reset();
		timer.start();
		RobotMap.drive.tankDrive(left, right);
	}

	@Override
	public void update() {
		if (timer.get() > time) {
			done = true;
			stop();
		}
	}

	@Override
	public void stop() {
		RobotMap.drive.tankDrive(0, 0);
	}

	@Override
	public boolean done() {
		return done;
	}

}
