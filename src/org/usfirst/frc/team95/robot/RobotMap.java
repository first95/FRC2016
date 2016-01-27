package org.usfirst.frc.team95.robot;


import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;

public class RobotMap {
	static CANTalon left1, left2;
	static Joystick driveStick;
	
	public static void init() {
		// Not actually mapped to the real locations on the robot
    	left1  = new CANTalon(4);
    	left2  = new CANTalon(3);
    	
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
    	
    	//left1.changeControlMode(CANTalon.TalonControlMode.Speed);
    	left1.enableControl();
    	left2.changeControlMode(CANTalon.TalonControlMode.Follower);
    	left2.set(4);
    	left2.enableControl();
    	driveStick = new Joystick(0);
	}

}
