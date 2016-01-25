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
	public void arcadeDrive(double y, double x) {
		tankDrive(x+y, x-y);
	}
	
	//gets the joystick values for stuff
	public void arcadeDrive(Joystick stick) {
		arcadeDrive(stick.getY()*-1,stick.getX());
	}
}
