package org.usfirst.frc.team95.robot.auto;

import org.usfirst.frc.team95.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;

public class SetOpenLoopMode extends Auto {

	@Override
	public void init() {
		RobotMap.arm1.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		RobotMap.arm1.enableControl();
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
