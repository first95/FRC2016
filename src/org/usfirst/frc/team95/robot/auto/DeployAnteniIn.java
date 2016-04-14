package org.usfirst.frc.team95.robot.auto;

import org.usfirst.frc.team95.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team95.robot.auto.DeployAnteniOut;

public class DeployAnteniIn extends Auto
{

	Timer timer = new Timer();
	boolean done = false;
	String out = DeployAnteniOut.out;
	
	@Override
	public void init()
	{
		timer.reset();
		timer.start();
		RobotMap.shoot1L.set(-1);
		RobotMap.shoot1R.set(1);
	}

	@Override
	public void update()
	{
		if (timer.get() >= 1) {
			done = true;
			RobotMap.shoot1L.setSetpoint(0);
			RobotMap.shoot1R.setSetpoint(0);
			this.out = "no";
		} else {
			RobotMap.shoot1L.setSetpoint(-1);
			RobotMap.shoot1R.setSetpoint(1);
		}
	}

	@Override
	public void stop()
	{
		this.out = "no";
		RobotMap.shoot1L.setSetpoint(0);
		RobotMap.shoot1R.setSetpoint(0);
	}

	@Override
	public boolean done()
	{
		return done;
	}

}
