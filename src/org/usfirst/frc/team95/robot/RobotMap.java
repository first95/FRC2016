package org.usfirst.frc.team95.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

public class RobotMap {
	static CANTalon left1, left2, left3, right1, right2, right3;
	static Joystick driveStick;
	static RobotDrive robotDrive;
	static SyncGroup left, right;
	
	public static void init() {
		// Not actually mapped to the real locations on the robot
    	left1  = new CANTalon(0);
    	left2  = new CANTalon(1);
    	left3  = new CANTalon(2);
    	right1 = new CANTalon(3);
    	right2 = new CANTalon(4);
    	right3 = new CANTalon(5);
    	
    	// SyncGroups for each side.
    	SpeedController[] leftTable = {left1, left2, left3};
    	SpeedController[] rightTable = {right1, right2, right3};
    	left = new SyncGroup(leftTable);
    	left = new SyncGroup(rightTable);
    	
    	
    	// The main drive train for the robot
    	robotDrive = new RobotDrive(left, right);
    	
    	driveStick = new Joystick(0);
	}

}
