package org.usfirst.frc.team95.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class RobotMap {
	static  CANTalon backLeftMotor, backRightMotor, frontLeftMotor, frontRightMotor;
	static RobotDrive rearDrive, frontDrive;
	static Joystick driveStick;
	
	public static void init() {
		// Not actually mapped to the real locations on the robot
    	backLeftMotor   = new CANTalon(0);
    	backRightMotor  = new CANTalon(1);
    	frontLeftMotor  = new CANTalon(2);
    	frontRightMotor = new CANTalon(3);
    	
    	// Ideally we'd use some smarter class (likely a SyncGroup or something like that),
    	// but to make this simple I'm gonna just instantiate a drive controller for
    	// the front and for the back
    	rearDrive = new RobotDrive(backLeftMotor, backRightMotor);
    	frontDrive = new RobotDrive(backLeftMotor, backRightMotor);
    	
    	driveStick = new Joystick(0);
	}

}
