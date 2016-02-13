package org.usfirst.frc.team95.robot.auto;

import org.usfirst.frc.team95.robot.Constants;
import org.usfirst.frc.team95.robot.Drive;
import org.usfirst.frc.team95.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PreserveHeading extends HybridAutoDrive
{
	double headingToPreserve;
	Drive drive;
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
		double ySpeed = driveStick.getY();
		double yawError = headingToPreserve - RobotMap.am.getYaw();
		double zCorrection;
		
		zCorrection = yawError / Math.PI;
		
		drive = new Drive(RobotMap.left1, RobotMap.right1);
		
		SmartDashboard.putNumber("weapon throttle",(((RobotMap.weaponStick.getThrottle()*-1)+1)/2));
		drive.arcadeDrive(ySpeed*(((driveStick.getThrottle()*-1)+1)/-2), zCorrection*(((RobotMap.weaponStick.getThrottle()*-1)+1)/2));
		
	}

}
