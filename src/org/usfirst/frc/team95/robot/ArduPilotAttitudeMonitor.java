package org.usfirst.frc.team95.robot;

import java.io.DataInputStream;

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
		;
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
