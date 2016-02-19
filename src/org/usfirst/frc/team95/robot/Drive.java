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
	
	//pure tank controlls and switch to RPM
	public void tankDrive(double leftsp, double rightsp) {
		left.set(leftsp * Math.abs(leftsp) * Constants.timeserRPM);
		right.set(rightsp * Math.abs(rightsp) * Constants.timeserRPM);
	}
	
	//squared arcade style drive
	public void arcadeDrive(double y, double z) {
		if (Math.abs(y) <= Constants.deadBand) {
			y = 0;
		}
		if (Math.abs(z) <= Constants.deadBand) {
			z = 0;
		}
		tankDrive(z+y, z-y);
	}
	
	//gets the joystick values for stuff and adds throttle sensitivity (redundant negatives on y are nessacary for some reason
	public void arcadeDrive(Joystick stick) {
		double y = stick.getY()*-.05;//(((stick.getThrottle()*-1)+1)/-2); for testing, wanted throttle and lower sensitivity
		double z = stick.getZ()*.05;//(((stick.getThrottle()*-1)+1)/2);
		arcadeDrive(y, z);
	}
}
