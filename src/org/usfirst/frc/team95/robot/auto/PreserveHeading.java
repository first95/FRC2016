package org.usfirst.frc.team95.robot.auto;

import org.usfirst.frc.team95.robot.Constants;
import org.usfirst.frc.team95.robot.Drive;
import org.usfirst.frc.team95.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PreserveHeading extends HybridAutoDrive
{
	double headingToPreserve, ySpeed, yawError, zCorrection, timerSens, yawToSpeed;
	Timer timer = new Timer();
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
		SmartDashboard.putNumber("weapon throttle",((RobotMap.weaponStick.getThrottle()*-1)+1));
		SmartDashboard.putNumber("yaw error", yawError);
		SmartDashboard.putNumber("yaw to speed", yawToSpeed);
		SmartDashboard.putNumber("z Correction", zCorrection);
		SmartDashboard.putNumber("driver throttle",(((RobotMap.driveStick.getThrottle()*-1)+1))/2);
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
		yawError = headingToPreserve - RobotMap.am.getYaw();
		zCorrection = yawError / Math.PI;
		yawToSpeed = (zCorrection*((RobotMap.weaponStick.getThrottle()*-1)+1));
		timerSens = yawToSpeed;
		//ySpeed = driveStick.getY();
		drive = new Drive(RobotMap.left1, RobotMap.right1);
		//timer.reset();
		//timer.start();
		drive.arcadeDrive(driveStick.getY()*-.05, yawToSpeed);		
		
	}

}
