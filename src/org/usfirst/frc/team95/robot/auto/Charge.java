package org.usfirst.frc.team95.robot.auto;

import org.usfirst.frc.team95.robot.Constants;
import org.usfirst.frc.team95.robot.RobotMap;
import org.usfirst.frc.team95.robot.VisionHandler;

import edu.wpi.first.wpilibj.Timer;

public class Charge extends Auto{
	
	boolean done = false;
	Timer timer = new Timer();
	
	@Override
	public void init() {
		// The trinary operator is for backspin.
		RobotMap.shoot2L.setSetpoint((RobotMap.arm1.getPosition() < Math.PI/2) ? -0.8 : -1);
		RobotMap.shoot2R.setSetpoint((RobotMap.arm1.getPosition() >= Math.PI/2) ? -0.8 : -1);
		timer.reset();
		timer.start();
	}

	@Override
	public void update() {
		
		if (timer.get() >= .75) {
			done = true;
		}
		
	}

	@Override
	public void stop() {
		//RobotMap.shoot2L.setSetpoint(0);
		//RobotMap.shoot2R.setSetpoint(0);
	}

	@Override
	public boolean done() {
		return done;
	}

}
