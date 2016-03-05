package org.usfirst.frc.team95.robot.auto;

public class ConfigMove extends Auto{
	Auto seq;
	
	public ConfigMove(Auto[] moves) {
		seq = new SequentialMove(moves);
	}

	@Override
	public void init() {
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
