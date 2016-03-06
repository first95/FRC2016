package org.usfirst.frc.team95.robot.auto;

import org.usfirst.frc.team95.robot.RobotMap;

public class BumpSetpoint extends Auto {
	double amount;

	public BumpSetpoint(double a) {
		amount = a;
	}

	@Override
	public void init() {
		RobotMap.arm1.setSetpoint(RobotMap.arm1.getSetpoint() + amount);
	}

	@Override
	public void update() {
		;
	}

	@Override
	public void stop() {
		;
	}

	@Override
	public boolean done() {
		return RobotMap.arm1.getClosedLoopError() < 0.05;
	}

}
