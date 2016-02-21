package org.usfirst.frc.team95.robot.auto;

import org.usfirst.frc.team95.robot.Constants;
import org.usfirst.frc.team95.robot.RobotMap;
import org.usfirst.frc.team95.robot.VisionHandler;

import edu.wpi.first.wpilibj.Timer;

public class Charge extends Auto{
	
	//double shootTolerance = VisionHandler.getInstance().getPower() * Constants.shootPowTol;
	boolean done = false;
	Timer timer = new Timer();
	
	@Override
	public void init() {
		RobotMap.shoot2L.setSetpoint(-1);//VisionHandler.getInstance().getPower());
		timer.reset();
		timer.start();
	}

	@Override
	public void update() {
		//if(RobotMap.shoot2L.getSpeed() > VisionHandler.getInstance().getPower() - shootTolerance && 
			//	RobotMap.shoot2L.getSpeed() < VisionHandler.getInstance().getPower() + shootTolerance ){
		if (timer.get() >= 2.0) {
			done = true;
		}
		
		//}
		
	}

	@Override
	public void stop() {
		RobotMap.shoot2L.setSetpoint(0);
	}

	@Override
	public boolean done() {
		return done;
	}

}
