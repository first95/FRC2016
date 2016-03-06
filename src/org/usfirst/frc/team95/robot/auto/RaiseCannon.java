package org.usfirst.frc.team95.robot.auto;

import org.usfirst.frc.team95.robot.Constants;
import org.usfirst.frc.team95.robot.RobotMap;
import org.usfirst.frc.team95.robot.VisionHandler;

public class RaiseCannon extends Auto {

	double angle;

	public RaiseCannon(double angle) {
		this.angle = angle;
	}

	@Override
	public void init() {
		RobotMap.armLock = this;
		double range = VisionHandler.getInstance().getRange();
		RobotMap.arm1.set(RobotMap.arm1.getSetpoint() + angle + (Constants.rangeAdjustment * range));

	}

	@Override
	public void update() {
		;
	}

	@Override
	public void stop() {
		RobotMap.armLock = null;
	}

	@Override
	public boolean done() {
		return RobotMap.arm1.getClosedLoopError() < 0.1;
	}

}
