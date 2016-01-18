
package org.usfirst.frc.team95.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Joystick.AxisType;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	CANTalon backLeftMotor, backRightMotor, frontLeftMotor, frontRightMotor;
	RobotDrive rearDrive, frontDrive;
	Joystick driveStick;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
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

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	// This WPIlib method uses the following simple drive equations:
    	// 		left wheel speed  = joystick Y - joystick X 
    	// 		right wheel speed = joystick Y + joystick X 
        frontDrive.arcadeDrive(driveStick);
        rearDrive.arcadeDrive(driveStick);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
