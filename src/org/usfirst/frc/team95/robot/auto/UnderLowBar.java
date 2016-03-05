package org.usfirst.frc.team95.robot.auto;

public class UnderLowBar extends Auto {
	Auto move;

	@Override
	public void init() {
		Auto[] bringArmDown = {new RaiseCannon(-Math.PI/4), new TimeOut(5, new Coast())};
		Auto[] all = {new SimultaneousMove(bringArmDown), new TimedMove(0.5, 0.5, 10)};
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
