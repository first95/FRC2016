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
		System.out.println("Called read");
		byte[] data = sp.read(1);
		int num = data[0] & 0xff;
		System.out.println("Got " + data.length + " bytes, first byte=" + data[0]);
		return num;
	}
	
	@Override
	public int available()
	{
		System.out.println("Available");
		return sp.getBytesReceived();
	}
	
	@Override
	public int read(byte[] b)
	{
		System.out.println("Read byte");
		try
		{
			return super.read(b);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	
}
