package org.usfirst.frc.team95.robot;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MAVLinkDecoder {
	FileInputStream bytes;
	public void init(FileInputStream bytes)
	{
		this.bytes = bytes;
	}
	
	public void scanMSG()
	{
		
		byte[] byteTable = new byte[1];
		while(true)
		{
			try {
				bytes.read(byteTable);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(byteTable[0]==0xFE)
			{
				try {
					bytes.read(byteTable);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				byte[] message = new byte[byteTable[0]];
				try {
					bytes.read(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				switch (message[3])
						{
				case 0x1E: 
					parseAttitudeMessage();
						}
			}
		}
	}
	public void parseAttitudeMessage(byte[] message)
	{
		
	}
}