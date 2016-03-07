package org.usfirst.frc.team95.robot.auto;

import org.usfirst.frc.team95.robot.Constants;
import org.usfirst.frc.team95.robot.RobotMap;
import org.usfirst.frc.team95.robot.VisionHandler;

public class Align extends Auto {

	double horizontalRotation, verticalRotation;
	Auto combined;

	// Please, for the love of god, don't make this static.
	// If you think you need to, you are doing it completely wrong.
	// You should create a new SequentialMove containing a new Align instead.
	boolean done = false;

	@Override
	public void init() {
		horizontalRotation = -VisionHandler.getInstance().getAimX();

		verticalRotation = -VisionHandler.getInstance().getAimY();

		double heading = RobotMap.am.getYaw() + horizontalRotation;
		if (heading > Math.PI) {
			heading -= Math.PI * 2;
		} else if (heading < -Math.PI) {
			heading += Math.PI * 2;
		}

		Auto[] autoMoves = { new PreserveHeading(heading), // };
				new RaiseCannon(verticalRotation), };
		// new Charge()};

		combined = new SimultaneousMove(autoMoves);
		combined.init();
	}

	@Override
	public void update() {
		combined.update();

	}

	@Override
	public void stop() {
		combined.stop();

	}

	@Override
	public boolean done() {
		return combined.done() || done;
	}

}
