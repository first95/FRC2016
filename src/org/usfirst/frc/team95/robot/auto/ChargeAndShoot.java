package org.usfirst.frc.team95.robot.auto;

import org.usfirst.frc.team95.robot.RobotMap;

public class ChargeAndShoot extends Auto {

	// *** No Longer In Use ***
	
	Auto move;

	@Override
	public void init() {
		Auto[] x = { new Charge(), new Shoot() };
		move = new SequentialMove(x);
		move.init();
	}

	@Override
	public void update() {
		move.update();

	}

	@Override
	public void stop() {
		move.stop();
		RobotMap.shootL.setSetpoint(0);
		RobotMap.shootR.setSetpoint(0);
	}

	@Override
	public boolean done() {
		return move.done();
	}

}
