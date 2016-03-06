package org.usfirst.frc.team95.robot;

import java.io.IOException;
import java.io.InputStream;

import edu.wpi.first.wpilibj.SerialPort;

public class SerialPortInputStream extends InputStream {
	SerialPort sp;

	public SerialPortInputStream(SerialPort sp) {
		this.sp = sp;
	}

	@Override
	public int read() throws IOException {
		return sp.read(1)[0]; // reads 1 byte from the serial port
	}

}
