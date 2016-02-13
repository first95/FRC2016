package org.usfirst.frc.team95.robot.auto;

import org.usfirst.frc.team95.robot.Constants;
import org.usfirst.frc.team95.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PreserveHeading extends HybridAutoDrive
{
	double headingToPreserve;

	public PreserveHeading()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init()
	{
		headingToPreserve = RobotMap.am.getYaw();
	}

	@Override
	public void update()
	{
		SmartDashboard.putNumber("headingToPreserve Yaw = ", headingToPreserve);

	}

	@Override
	public void stop()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean done()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void drive(Joystick driveStick)
	{
		double leftSpeed = driveStick.getY();
		double rightSpeed = -driveStick.getY();
	
		if (Math.abs(leftSpeed) <= Constants.deadBand) {
			leftSpeed = 0;
		}
		
		if (Math.abs(rightSpeed) <= Constants.deadBand) {
			rightSpeed = 0;
		}
		
		RobotMap.left1.setSetpoint(Constants.timeserRPM * leftSpeed * 0.001);
		RobotMap.right1.setSetpoint(Constants.timeserRPM * rightSpeed * 0.001);

	}

}
