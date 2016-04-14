package org.usfirst.frc.team95.robot.auto;

import org.usfirst.frc.team95.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;

public class PickUp extends Auto {
	Timer timer = new Timer();
	boolean done = false;

	@Override
	public void init() {
		// Being used in Anteni Deployment
		//RobotMap.shoot1L.setSetpoint(.6);
		
		RobotMap.shoot2L.setSetpoint(0.6);
		RobotMap.shoot2R.setSetpoint(0.6);
		timer.reset();
		timer.start();
	}

	@Override
	public void update() {
		if (timer.get() >= 1) {
			done = true;
		}
	}

	@Override
	public void stop() {
		// Used in Anteni Deployment
		//RobotMap.shoot1L.setSetpoint(0);
		
		RobotMap.shoot2L.setSetpoint(0);
		RobotMap.shoot2R.setSetpoint(0);
		done = true;

	}

	@Override
	public boolean done() {
		// RobotMap.shoot1L.setSetpoint(0);
		// RobotMap.shoot2L.setSetpoint(0);
		return done;
	}

}
