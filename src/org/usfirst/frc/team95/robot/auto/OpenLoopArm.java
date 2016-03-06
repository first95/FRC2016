package org.usfirst.frc.team95.robot.auto;

import org.usfirst.frc.team95.robot.Constants;
import org.usfirst.frc.team95.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DriverStation;

public class OpenLoopArm extends Auto {

	@Override
	public void init() {
		RobotMap.arm1.disableControl();
		RobotMap.arm1.changeControlMode(TalonControlMode.PercentVbus);
		RobotMap.arm1.enableControl();
	}

	@Override
	public void update() {
		double y = RobotMap.weaponStick.getY();
		if (Math.abs(y) < Constants.deadBand) {
			y = 0;
		}
		y *= ((-RobotMap.weaponStick.getThrottle()+1)*0.5)+0.05;
		RobotMap.arm1.set(y);
	}

	@Override
	public void stop() {
		
		RobotMap.arm1.disableControl();
		RobotMap.brakeAndVoltage(RobotMap.arm1);
    	RobotMap.arm1.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Absolute);
    	RobotMap.arm1.reverseSensor(true);
    	RobotMap.arm1.setF(Constants.armF);
    	RobotMap.arm1.setPID(Constants.armP, Constants.armI, Constants.armD);
    	RobotMap.arm1.changeControlMode(CANTalon.TalonControlMode.Position);
    	RobotMap.arm1.set(RobotMap.arm1.getPosition());
		RobotMap.arm1.enableControl();
	}

	@Override
	public boolean done() {
		return false;
	}

}
