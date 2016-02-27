package org.usfirst.frc.team95.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public class ArmDrive {
	SpeedController arm;
	
	public ArmDrive(SpeedController arm) {
		this.arm = arm;
	}
	
	public void Move(double position) {
		if (RobotMap.armLock == null) {
			arm.set(position);
		}
	}
	
	public void ArmControll(Joystick stick) {
		double y = stick.getY();
		if (Math.abs(y) <= Constants.deadBand || 
				Math.abs(RobotMap.arm1.getPosition()-RobotMap.arm1.getSetpoint()) 
				> Constants.setPointChangeLimit) {
			y = 0;
		} else {
			y *= (((stick.getThrottle()*-1)+1)/2)*0.7+0.1;
			double positionAdder = (RobotMap.arm1.getPosition() + (y * Math.abs(y)));
			Move(positionAdder);
		}
		
	}
}
