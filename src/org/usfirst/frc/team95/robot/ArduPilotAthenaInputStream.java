package org.usfirst.frc.team95.robot;

import java.io.IOException;
import java.io.InputStream;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;

public class ArduPilotAthenaInputStream extends InputStream {
	SerialPort sp = null;
	public ArduPilotAthenaInputStream() {
		sp = new SerialPort(115200, Port.kUSB);		
	}

	@Override
	public int read(){
		byte[] data = sp.read(1);
		int num = data[0] & 0xff;
		return num;
	}
	
	@Override
	public int available()
	{
		return sp.getBytesReceived();
	}	
}
