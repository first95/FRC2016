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
			RobotMap.shoot1L.set(-1);
			RobotMap.shoot2L.set(-1);
			RobotMap.shoot2R.set(-1);
		}

	}

	@Override
	public void stop() {
		RobotMap.shoot1L.setSetpoint(0);
		RobotMap.shoot2L.setSetpoint(0);
		RobotMap.shoot2R.setSetpoint(0);
	}

	@Override
	public boolean done() {
		return done;
	}

}
