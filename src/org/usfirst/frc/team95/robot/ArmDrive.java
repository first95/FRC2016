package org.usfirst.frc.team95.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public class ArmDrive {
	SpeedController arm;
	
	public ArmDrive(SpeedController arm) {
		this.arm = arm;
	}
	
	public void Move(double position) {
		arm.set(position);
	}
	
	public void ArmControll(Joystick stick) {
		double y = stick.getY()*(((stick.getThrottle()*-1)+1)/2);
		if (Math.abs(y) <= Constants.deadBand) {
			y = 0;
		}else {
			double positionAdder = (RobotMap.arm1.getPosition() + y);
			Move(positionAdder);
		}
		
	}
}
