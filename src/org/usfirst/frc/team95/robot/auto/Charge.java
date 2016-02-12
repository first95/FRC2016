package org.usfirst.frc.team95.robot.auto;

import org.usfirst.frc.team95.robot.Constants;
import org.usfirst.frc.team95.robot.RobotMap;
import org.usfirst.frc.team95.robot.VisionHandler;

import edu.wpi.first.wpilibj.Timer;

public class Charge extends Auto{
	
	double shootTolerance = VisionHandler.getInstance().getPower() * Constants.shootPowTol;
	boolean done = false;
	
	@Override
	public void init() {
		RobotMap.shoot2L.setSetpoint(VisionHandler.getInstance().getPower());
	}

	@Override
	public void update() {
		if(RobotMap.shoot2L.getSpeed() > VisionHandler.getInstance().getPower() - shootTolerance && 
				RobotMap.shoot2L.getSpeed() < VisionHandler.getInstance().getPower() + shootTolerance ){
			done = true;
		}
		
	}

	@Override
	public void stop() {
		;
	}

	@Override
	public boolean done() {
		return done;
	}

}
