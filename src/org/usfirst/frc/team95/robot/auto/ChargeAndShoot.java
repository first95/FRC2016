package org.usfirst.frc.team95.robot.auto;

import org.usfirst.frc.team95.robot.RobotMap;

public class ChargeAndShoot extends Auto {

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
		RobotMap.shoot1L.setSetpoint(0);
		RobotMap.shoot2L.setSetpoint(0);
		RobotMap.shoot2R.setSetpoint(0);
	}

	@Override
	public boolean done() {
		return move.done();
	}

}
