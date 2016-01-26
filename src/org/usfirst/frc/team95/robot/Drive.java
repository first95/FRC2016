package org.usfirst.frc.team95.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public class Drive {
	SpeedController left, right;
	
	//drives stuff
	public Drive(SpeedController left, SpeedController right) {
		this.left = left;
		this.right = right;
	}
	
	//pure tank controlls
	public void tankDrive(double leftsp, double rightsp) {
		left.set(leftsp * Math.abs(leftsp));
		right.set(rightsp * Math.abs(rightsp));
	}
	
	//squared arcade style drive
	public void arcadeDrive(double y, double z) {
		tankDrive(z+y, z-y);
	}
	
	//gets the joystick values for stuff and adds throttle sensitivity (redundant negatives on y are nessacary for some reason
	public void arcadeDrive(Joystick stick) {
		arcadeDrive(stick.getY()*(((stick.getThrottle()*-1)+1)/-2),stick.getZ()*(((stick.getThrottle()*-1)+1)/2));
	}
}
