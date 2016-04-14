package org.usfirst.frc.team95.robot.auto;

import org.usfirst.frc.team95.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team95.robot.auto.DeployBallBlockerForward;

public class DeployBallBlockerBackward extends Auto
{
	boolean done = false;
	
	String ballBlockerStage = DeployBallBlockerForward.ballBlockerStage;

	@Override
	public void init()
	{
		// ** ballBlockerL is not being used **
		// RobotMap.ballBlockerL.set(-.5);
		RobotMap.ballBlockerR.set(-.5);
	}

	@Override
	public void update()
	{
		// Continuous movement when button is pressed
		if (RobotMap.weaponStick.getRawButton(15))
		{
			done = true;
			// ** ballBlockerL is not being used **
			// RobotMap.ballBlockerL.setSetpoint(-.5);
			RobotMap.ballBlockerR.setSetpoint(.5);
		}
		else
		{
			// ** ballBlockerL is not being used **
			// RobotMap.ballBlockerL.setSetpoint(0);
			RobotMap.ballBlockerR.setSetpoint(0);
		}
	}

	@Override
	public void stop()
	{
		// ** ballBlockerL is not being used **
		// RobotMap.ballBlockerL.setSetpoint(0);
		RobotMap.ballBlockerR.setSetpoint(0);
	}

	@Override
	public boolean done()
	{
		return done;
	}

}
