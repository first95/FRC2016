package org.usfirst.frc.team95.robot.auto;

import org.usfirst.frc.team95.robot.RobotMap;

public class Coast extends Auto {
	double p, i, d;

	@Override
	public void init() {
		p = RobotMap.arm1.getP();
		i = RobotMap.arm1.getI();
		d = RobotMap.arm1.getD();
		RobotMap.arm1.setPID(0, 0, 0);
	}

	@Override
	public void update() {
		;
	}

	@Override
	public void stop() {
		RobotMap.arm1.set(RobotMap.arm1.getPosition());
		RobotMap.arm1.setPID(p, i, d);
	}

	@Override
	public boolean done() {
		return true;
	}

}
