
package org.usfirst.frc.team95.robot;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.mavlink.MAVLinkReader;
import org.mavlink.messages.MAVLinkMessage;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	MAVLinkReader rd;
	boolean arduConnect = false;//checks to see if the ardupilot is connected
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	RobotMap.init();
    	VisionHandler.getInstance().init();
    	//SerialPort sp = new SerialPort(115200, edu.wpi.first.wpilibj.SerialPort.Port.kUSB);
    	DataInputStream dis = null;
		try {
			dis = new DataInputStream(new FileInputStream("/dev/ttyACM0"));
			try {
				System.out.println(dis.read());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			arduConnect = true;
			new Thread(//made MAVLink stuff a thread to avoid slow downs
				new Runnable() {
					public void run() {
						MAVLinkMessage mesg = null;
						try {
							System.out.println("Bad CRC count: " + rd.getBadCRC());
							System.out.println("Bad seq count: " + rd.getBadSequence());
							mesg = rd.getNextMessage();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (mesg == null) {
							System.out.println("Null");
						} else {
							System.out.println(mesg.messageType);
						}
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						run();
					}
				}
			).start();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		 rd = new MAVLinkReader(dis);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    }
   
    public void commonPeriodic(){
    	System.out.println(Constants.I);
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void disabledPeriodic(){
        commonPeriodic();
    }
    
    
    public void teleopPeriodic() {
        commonPeriodic();
        RobotMap.drive.arcadeDrive(RobotMap.driveStick);
        PIDTuner();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
    public void PIDTuner() {
    	if (RobotMap.driveStick.getRawButton(3)) {
    		Constants.magnitude = Constants.magnitude - 1;
    	}else if (RobotMap.driveStick.getRawButton(4)) {
    		Constants.magnitude = Constants.magnitude + 1;
    	}
    	if (RobotMap.driveStick.getRawButton(5)) {
        	Constants.P = Constants.P - (.1 * Math.pow(10, Constants.magnitude));
        	RobotMap.left1.setPID(Constants.P, Constants.I, Constants.D);
        	RobotMap.right1.setPID(Constants.P, Constants.I, Constants.D);
        } else if (RobotMap.driveStick.getRawButton(10)) {
        	Constants.P = Constants.P + (.1 * Math.pow(10, Constants.magnitude));
        	RobotMap.left1.setPID(Constants.P, Constants.I, Constants.D);
        	RobotMap.right1.setPID(Constants.P, Constants.I, Constants.D );
        }
        if (RobotMap.driveStick.getRawButton(6)) {
        	Constants.I = Constants.I - (.00001 * Math.pow(10, Constants.magnitude));
        	RobotMap.left1.setPID(Constants.P, Constants.I, Constants.D);
        	RobotMap.right1.setPID(Constants.P, Constants.I, Constants.D);
        } else if (RobotMap.driveStick.getRawButton(9)) {
        	Constants.I = Constants.I + (.00001 * Math.pow(10, Constants.magnitude));
        	RobotMap.left1.setPID(Constants.P, Constants.I, Constants.D);
        	RobotMap.right1.setPID(Constants.P, Constants.I, Constants.D );
        }
        RobotMap.drive.arcadeDrive(RobotMap.driveStick);
        if (RobotMap.driveStick.getRawButton(7)) {
        	Constants.D = Constants.D - (.1 * Math.pow(10, Constants.magnitude));
        	RobotMap.left1.setPID(Constants.P, Constants.I, Constants.D);
        	RobotMap.right1.setPID(Constants.P, Constants.I, Constants.D);
        } else if (RobotMap.driveStick.getRawButton(8)) {
        	Constants.I = Constants.I + (.1 * Math.pow(10, Constants.magnitude));
        	RobotMap.left1.setPID(Constants.P, Constants.I, Constants.D);
        	RobotMap.right1.setPID(Constants.P, Constants.I, Constants.D );
        }
        
        SmartDashboard.putNumber("P", Constants.P);
        SmartDashboard.putNumber("I", Constants.I);
        SmartDashboard.putNumber("D", Constants.D);
        
        SmartDashboard.putNumber("Left Setpoint", RobotMap.left1.getSetpoint());
        SmartDashboard.putNumber("Left Speed", RobotMap.left1.getSpeed());
        SmartDashboard.putNumber("Right Setpoint", RobotMap.right1.getSetpoint());
        SmartDashboard.putNumber("Right Speed", RobotMap.right1.getSpeed());
    }
    
    public void disabledInit() {
    	
    }
    
}
