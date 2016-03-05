package org.usfirst.frc.team95.robot.auto;

public class BringArmDown extends Auto {
	Auto move;

	@Override
	public void init() {
		Auto[] bringArmDown = {new RaiseCannon(-Math.PI/4), new TimeOut(5, new Coast())};
		move = new SequentialMove(bringArmDown);
		move.init();
	}

	@Override
	public void update() {
		move.update();
	}

	@Override
	public void stop() {
		move.stop();
	}

	@Override
	public boolean done() {
		return move.done();
	}
	

}
