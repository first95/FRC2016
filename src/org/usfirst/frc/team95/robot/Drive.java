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
	
	//pure tank controlls (not) and switch to RPM
	public void tankDrive(double leftsp, double rightsp) {
		left.set(leftsp);// * Constants.timeserRPM);
		right.set(rightsp);// * Constants.timeserRPM);
	}
	
	//arcade style drive
	public void arcadeDrive(double y, double x) {
		tankDrive(x+y, x-y);
	}
	
	//gets the joystick values for stuff and adds throttle sensitivity (redundant negatives on y are nessacary for some reason
	public void arcadeDrive(Joystick stick) {
		double y = stick.getY()*(((stick.getThrottle()*-1)+1)/-2);
		double x = stick.getX()*(((stick.getThrottle()*-1)+1)/2);
		if (Math.abs(y) <= Constants.deadBand) {
			y = 0;
		}
		if (Math.abs(x) <= Constants.horDeadBand) {
			x = 0;
		}
		arcadeDrive(y,x);
	}
}
