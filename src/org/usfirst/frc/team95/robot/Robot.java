
package org.usfirst.frc.team95.robot;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.mavlink.MAVLinkReader;
import org.mavlink.messages.MAVLinkMessage;

import edu.wpi.first.wpilibj.IterativeRobot;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	MAVLinkReader rd;
	double P = .1;
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
    	System.out.println(P);
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void disabledPeriodic(){
        commonPeriodic();
    }
    
    
    public void teleopPeriodic() {
        commonPeriodic();
        RobotMap.left1.setSetpoint(RobotMap.driveStick.getRawAxis(1)*-10000);
        RobotMap.right1.setSetpoint(RobotMap.driveStick.getRawAxis(1)*10000);
        if (RobotMap.driveStick.getRawButton(3)) {
        	P = P - .01;
        	RobotMap.left1.setPID(P, 0, 0);
        	RobotMap.left2.setPID(P, 0, 0);
        	RobotMap.right1.setPID(P, 0, 0);
        	RobotMap.right2.setPID(P, 0, 0);
        }else if (RobotMap.driveStick.getRawButton(2)) {
        	P = P + .01;
        	RobotMap.left1.setPID(P, 0, 0);
        	RobotMap.left2.setPID(P, 0, 0);
        	RobotMap.right1.setPID(P, 0, 0);
        	RobotMap.right2.setPID(P, 0, 0);
        }
        //System.out.println(mesg.messageType);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
    public void disabledInit() {
    	
    }
    
}
