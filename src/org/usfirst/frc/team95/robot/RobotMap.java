package org.usfirst.frc.team95.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

public class RobotMap {
	static CANTalon left1, left2, left3, right1, right2, right3;
	static Joystick driveStick;
	static Drive robotDrive;
	static SyncGroup left, right;
	
	public static void init() {
		// Not actually mapped to the real locations on the robot
    	left1  = new CANTalon(4);
    	left2  = new CANTalon(3);
    	//left3  = new CANTalon(2);
    	right1 = new CANTalon(1);
    	right2 = new CANTalon(2);
    	//right3 = new CANTalon(5);
    	
    	// SyncGroups for each side.
    	CANTalon[] leftTable = {left1, left2, };//left3};
    	for (CANTalon t : leftTable) {
    		t.setPosition(0);
    		t.enableBrakeMode(Constants.brakeMode);
    		if (Constants.useVoltageRamp) {
    			t.setVoltageRampRate(Constants.voltageRampRate);
    		} else {
    			t.setVoltageRampRate(0.0);
    		}
    	}
    	
    	CANTalon[] rightTable = {right1, right2, };//right3};
    	for (CANTalon t : rightTable) {
    		t.setPosition(0);
    		t.enableBrakeMode(Constants.brakeMode);
    		if (Constants.useVoltageRamp) {
    			t.setVoltageRampRate(Constants.voltageRampRate);
    		} else {
    			t.setVoltageRampRate(0.0);
    		}
    	}
    	
    	// SyncGroups for each side
    	left = new SyncGroup(leftTable);
    	right = new SyncGroup(rightTable);
    	
    	
    	// The main drive train for the robot
    	robotDrive = new Drive(left, right);
    	
    	driveStick = new Joystick(0);
	}

}
