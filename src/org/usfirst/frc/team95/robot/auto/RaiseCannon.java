package org.usfirst.frc.team95.robot.auto;

import org.usfirst.frc.team95.robot.RobotMap;

public class RaiseCannon extends Auto {
	
	double angle;
	
	public RaiseCannon(double angle) {
		this.angle = angle;
	}

	@Override
	public void init() {
		RobotMap.arm1.set(RobotMap.arm1.get() + angle);
		
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
		return true;
	}
	

}
