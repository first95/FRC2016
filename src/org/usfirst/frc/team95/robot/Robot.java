
package org.usfirst.frc.team95.robot;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.mavlink.*;
import org.mavlink.messages.*;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	CameraServer cameraServer;
	ArduPilotAttitudeMonitor am = null;
	ArrayList<PollableSubsystem> updates;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	RobotMap.init();
    	VisionHandler.getInstance().init();
    	
    	//cameraServer = CameraServer.getInstance();
    	//cameraServer.startAutomaticCapture("/dev/video0");
    	am = new ArduPilotAttitudeMonitor();
    	
    	updates.add(RobotMap.incP);
    	updates.add(RobotMap.decP);
    	updates.add(RobotMap.incI);
    	updates.add(RobotMap.decI);
    	updates.add(RobotMap.incD);
    	updates.add(RobotMap.decD);
    	updates.add(RobotMap.magInc);
    	updates.add(RobotMap.magDec);
    	updates.add(RobotMap.incF);
    	updates.add(RobotMap.decF);
    	updates.add(am);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    }
   
    public void commonPeriodic(){
    	SmartDashboard.putNumber("Test", 5);
    	
    	for (PollableSubsystem p : updates) {
    		p.update();
    	}
    	
    	SmartDashboard.putNumber("Pitch", am.getPitch());
    	SmartDashboard.putNumber("Roll", am.getRoll());
    	SmartDashboard.putNumber("Yaw", am.getYaw());
    	
		SmartDashboard.putNumber("P", Constants.P);
		SmartDashboard.putNumber("10^6*I", Constants.I * (1e6));
		SmartDashboard.putNumber("D", Constants.D);
		SmartDashboard.putNumber("F", Constants.F);
		
		SmartDashboard.putNumber("encoder position", RobotMap.left1.getEncPosition());
		
		SmartDashboard.putNumber("Left Setpoint", RobotMap.left1.getSetpoint());
		SmartDashboard.putNumber("Left Speed", -1 * RobotMap.left1.getSpeed());
		
		SmartDashboard.putNumber("Target X", VisionHandler.getInstance().x);
		//System.out.println(VisionHandler.getInstance().x);
		SmartDashboard.putNumber("Target Y", VisionHandler.getInstance().y);
		//System.out.println(VisionHandler.getInstance().y);
		//System.out.println("----");
		//SmartDashboard.putNumber("Right Setpoint", RobotMap.right1.getSetpoint());
		//SmartDashboard.putNumber("Right Speed", RobotMap.right1.getSpeed());
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void disabledPeriodic(){
        commonPeriodic();
    }
    
    @Override
    public void teleopInit() {
    	RobotMap.light.set(0.5);
    }
    
    public void teleopPeriodic() {
        commonPeriodic();
//        RobotMap.drive.arcadeDrive(RobotMap.driveStick);
        RobotMap.testDrive();
        PIDTuner();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
    public void PIDTuner() {
//    	if (RobotMap.driveStick.getRawButton(3)) {
//    		Constants.magnitude = Constants.magnitude - 1;
//    	}else if (RobotMap.driveStick.getRawButton(4)) {
//    		Constants.magnitude = Constants.magnitude + 1;
//    	}
    	
    	if (RobotMap.magInc.justPressedp()) {
    		Constants.magnitude++;
    	}else if (RobotMap.magDec.justPressedp()) {
    		Constants.magnitude--;
    	}
    	double d = Math.pow(10, Constants.magnitude);
    	boolean changed = false;
    	if (RobotMap.decP.justPressedp()) {
        	Constants.P = Constants.P - (.1 * d);
        	changed = true;
        } else if (RobotMap.incP.justPressedp()) {
        	Constants.P = Constants.P + (.1 * d);
        	changed = true;
        }
        if (RobotMap.decI.justPressedp()) {
        	Constants.I = Constants.I - (.0000001 * d);
        	changed = true;
        } else if (RobotMap.incI.justPressedp()) {
        	Constants.I = Constants.I + (.0000001 * d);
        	changed = true;
        }
        if (RobotMap.decD.justPressedp()) {
        	Constants.D = Constants.D - (.1 * d);
        	changed = true;
        } else if (RobotMap.incD.justPressedp()) {
        	Constants.D = Constants.D + (.1 * d);
        	changed = true;
        }
        if (RobotMap.incF.justPressedp()) {
        	Constants.F = Constants.F + (.1 * d);
        	changed = true;
        }else if (RobotMap.decF.justPressedp()) {
        	Constants.F = Constants.F - (.1 * d);
        	changed = true;
        }
        if(changed) {
        	RobotMap.left1.setPID(Constants.P, Constants.I, Constants.D);
        	RobotMap.right1.setPID(Constants.P, Constants.I, Constants.D );        	
        }

    }
    
    public void disabledInit() {
    	
    }
    
}
