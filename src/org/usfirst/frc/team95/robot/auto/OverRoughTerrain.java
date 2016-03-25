package org.usfirst.frc.team95.robot.auto;

public class OverRoughTerrain extends Auto {
	Auto simul;

	@Override
	public void init() {
		Auto[] moves = { new TimedStraightMove(0.6, 3.5) };
		simul = new SimultaneousMove(moves);
		simul.init();
	}

	@Override
	public void update() {
		simul.update();
	}

	@Override
	public void stop() {
		simul.stop();
	}

	@Override
	public boolean done() {
		return simul.done();
	}

}
