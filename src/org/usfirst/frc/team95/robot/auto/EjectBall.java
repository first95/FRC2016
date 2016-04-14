package org.usfirst.frc.team95.robot.auto;

import edu.wpi.first.wpilibj.Timer;

import org.usfirst.frc.team95.robot.RobotMap;

public class EjectBall extends Auto {

	Timer timer = new Timer();
	boolean done = false;

	@Override
	public void init() {
		timer.reset();
		timer.start();
	}

	@Override
	public void update() {
		if (timer.get() >= .75) {
			stop();
			done = true;
		} else {
			RobotMap.shootL.set(-1);
			RobotMap.shootR.set(-1);
		}

	}

	@Override
	public void stop() {
		RobotMap.shootL.setSetpoint(0);
		RobotMap.shootR.setSetpoint(0);
	}

	@Override
	public boolean done() {
		return done;
	}

}
