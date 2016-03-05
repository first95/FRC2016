package org.usfirst.frc.team95.robot.auto;

import edu.wpi.first.wpilibj.Timer;

public class TimeOut extends Auto {
	Timer time = new Timer();
	Auto move;
	double timeLimit;
	boolean done;

	public TimeOut(double time, Auto move) {
		this.move = move;
		this.timeLimit = time;
	}

	@Override
	public void update() {
		if (time.get() > timeLimit) {
			done = true;
			stop();
		}
		if (!done) {
			move.update();
		}
	}

	@Override
	public void stop() {
		move.stop();
	}

	@Override
	public boolean done() {
		return done;
	}

	@Override
	public void init() {
		time.reset();
		time.start();
		move.init();
	}

}
