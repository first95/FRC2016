package org.usfirst.frc.team95.robot;

import java.io.DataInputStream;
import java.io.IOException;

import org.mavlink.*;
import org.mavlink.messages.*;
import org.mavlink.messages.ardupilotmega.*;

import edu.wpi.first.wpilibj.Timer;

public class ArduPilotAttitudeMonitor implements PollableSubsystem {
	ArduPilotAthenaInputStream is = null;
	MAVLinkReader rd = null;
	Timer arduTime = new Timer();
	double pitch = 0;
	double roll  = 0;
	double yaw   = 0;
	static double time;
	
	public ArduPilotAttitudeMonitor() {
		is = new ArduPilotAthenaInputStream();
		DataInputStream dis = new DataInputStream(is);
		rd = new MAVLinkReader(dis, IMAVLinkMessage.MAVPROT_PACKET_START_V10);
	}
	
	public void init() {
		/*msg_param_set msg = new msg_param_set(1,1);
		msg.param_id = new char[]{'S', 'R', '0', '_', 'E', 'X', 'T', 'R', 'A', '1', 0, 0, 0, 0, 0, 0};
		msg.param_value = 10;
		msg.param_type = 1;
		byte[] buffer;
		try {
			buffer = msg.encode();
			is.write(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	public void update() { // Check for and Process New Data
		try {
			MAVLinkMessage msg = rd.getNextMessageWithoutBlocking();
			if (msg != null) {
				if (msg instanceof msg_attitude) {
					time = arduTime.get();
					msg_attitude attitude = (msg_attitude)msg;
					pitch = attitude.pitch;
					roll  = attitude.roll;
					yaw   = attitude.yaw;
					arduTime.reset();
					arduTime.start();
				}
				System.out.println("Message ID: "+msg.messageType);
			}
		} catch (Exception e) {
			System.out.println("Got error reading Ardupilot message:");
			e.printStackTrace();
		}
	}
	
	public double getPitch() { return pitch; }
	public double getRoll()  { return roll; }
	public double getYaw()   { return yaw; }
}
