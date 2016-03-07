package org.usfirst.frc.team95.robot.auto;

public class DropArm extends Auto {
	Auto move;

	@Override
	public void init() {
		Auto[] all = { new BumpSetpoint(-0.25), new TimeOut(5, new Coast())};
		move = new SequentialMove(all);
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
