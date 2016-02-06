package org.usfirst.frc.team95.robot;

import java.io.DataInputStream;

import org.mavlink.*;
import org.mavlink.messages.*;
import org.mavlink.messages.ardupilotmega.*;

public class ArduPilotAttitudeMonitor {
	ArduPilotAthenaInputStream is = null;
	MAVLinkReader rd = null;
	
	double pitch = 0;
	double roll  = 0;
	double yaw   = 0;
	
	
	public ArduPilotAttitudeMonitor() {
		is = new ArduPilotAthenaInputStream();
		DataInputStream dis = new DataInputStream(is);
		rd = new MAVLinkReader(dis, IMAVLinkMessage.MAVPROT_PACKET_START_V10);
	}

	public void CheckForAndProcessNewData(){
		try {
			MAVLinkMessage msg = rd.getNextMessageWithoutBlocking();
			if (msg != null) {
				if (msg instanceof msg_attitude ) {
					msg_attitude attitude = (msg_attitude)msg;
					pitch = attitude.pitch;
					roll  = attitude.roll;
					yaw   = attitude.yaw;
				}
			}
		} catch (Exception e) {
			System.out.println("Got error reading message:");
			e.printStackTrace();
		}
	}
	
	public double getPitch() { return pitch; }
	public double getRoll()  { return roll; }
	public double getYaw()   { return yaw; }
}
