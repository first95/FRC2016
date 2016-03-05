package org.usfirst.frc.team95.robot.auto;

public class UnderLowBarAndShoot extends Auto {
	Auto seq;

	@Override
	public void init() {
		Auto[] moves = {new UnderLowBar(), new AlignAndShoot()};
		seq = new SequentialMove(moves);
		seq.init();
	}

	@Override
	public void update() {
		seq.update();
	}

	@Override
	public void stop() {
		seq.stop();
	}

	@Override
	public boolean done() {
		return seq.done();
	}
	

}
