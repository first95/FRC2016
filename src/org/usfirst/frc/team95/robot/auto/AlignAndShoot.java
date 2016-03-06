package org.usfirst.frc.team95.robot.auto;

public class AlignAndShoot extends Auto {

	Auto move;

	@Override
	public void init() {
		Auto[] x = { new Align(), new Shoot() };
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
	}

	@Override
	public boolean done() {
		return move.done();
	}

}
