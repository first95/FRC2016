package org.usfirst.frc.team95.robot;

import edu.wpi.first.wpilibj.CANTalon;

public class VoltageCompensatingTalon extends CANTalon {

	public VoltageCompensatingTalon(int deviceNumber) {
		super(deviceNumber);
	}
	
	public void set(double speed) {
		super.set(speed*(10/RobotMap.pdb.getVoltage()));
	}

}
