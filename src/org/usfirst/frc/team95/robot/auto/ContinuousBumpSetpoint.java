package org.usfirst.frc.team95.robot.auto;

import org.usfirst.frc.team95.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Timer;

public class ContinuousBumpSetpoint extends Auto {
	
	double amount;
	Timer timer = new Timer();

	public ContinuousBumpSetpoint(double a) {
		amount = a;
	}

	@Override
	public void init() {
		if (RobotMap.arm1.getControlMode() == CANTalon.TalonControlMode.Position) {
			RobotMap.arm1.setSetpoint(RobotMap.arm1.getSetpoint() + amount);
		}
		timer.reset();
		timer.start();
	}

	@Override
	public void update() {
		if (RobotMap.arm1.getControlMode() == CANTalon.TalonControlMode.Position
				&& timer.get() > 0.1) {
			RobotMap.arm1.setSetpoint(RobotMap.arm1.getSetpoint() + amount);
			timer.reset();
			timer.start();
		}
	}

	@Override
	public void stop() {
		;
	}

	@Override
	public boolean done() {
		return false;
	}

}
