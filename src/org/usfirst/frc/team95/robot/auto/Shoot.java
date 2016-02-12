package org.usfirst.frc.team95.robot.auto;

import org.usfirst.frc.team95.robot.Constants;
import org.usfirst.frc.team95.robot.RobotMap;
import org.usfirst.frc.team95.robot.VisionHandler;

import edu.wpi.first.wpilibj.Timer;

public class Shoot extends Auto{
	
	double shootTolerance = VisionHandler.getInstance().getPower() * Constants.shootPowTol;
	Timer timer = new Timer();
	boolean done = false;
	
	@Override
	public void init() {
		RobotMap.shoot2L.setSetpoint(VisionHandler.getInstance().getPower());
	}

	@Override
	public void update() {
		if(RobotMap.shoot2L.getSpeed() > VisionHandler.getInstance().getPower() - shootTolerance && 
				RobotMap.shoot2L.getSpeed() < VisionHandler.getInstance().getPower() + shootTolerance ){
			timer.reset();
			timer.start();
			RobotMap.shoot1L.setSetpoint(1);
		}
		
		if (timer.get() >= 1){
			done = true;
			RobotMap.shoot1L.setSetpoint(0);
			RobotMap.shoot2L.setSetpoint(0);
		}
		
	}

	@Override
	public void stop() {
		RobotMap.shoot1L.setSetpoint(0);
		RobotMap.shoot2L.setSetpoint(0);
		
	}

	@Override
	public boolean done() {
		return done;
	}

}
