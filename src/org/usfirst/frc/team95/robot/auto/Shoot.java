package org.usfirst.frc.team95.robot.auto;

import org.usfirst.frc.team95.robot.Constants;
import org.usfirst.frc.team95.robot.RobotMap;
import org.usfirst.frc.team95.robot.VisionHandler;

import edu.wpi.first.wpilibj.Timer;

public class Shoot extends Auto { // Assumes Cannon is Charged
	Timer timer = new Timer();
	boolean done = false;

	@Override
	public void init() {
		timer.reset();
		timer.start();
		
		RobotMap.shootL.set(-1);
		RobotMap.shootR.set(-1);
		//
	}

	@Override
	public void update() {
		if (timer.get() >= 1) {
			done = true;
			RobotMap.shootL.setSetpoint(0);
			RobotMap.shootR.setSetpoint(0);
		} else {
			RobotMap.shootR.setSetpoint(-1);
			RobotMap.shootL.setSetpoint(-1);
		}

	}

	@Override
	public void stop() {
		RobotMap.shootL.setSetpoint(0);
		RobotMap.shootR.setSetpoint(0);
	}

	@Override
	public boolean done() {
		return done;
	}

}
