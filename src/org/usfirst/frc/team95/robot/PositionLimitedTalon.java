package org.usfirst.frc.team95.robot;

import edu.wpi.first.wpilibj.CANTalon;

public class PositionLimitedTalon extends CANTalon implements PollableSubsystem {
	double forward, reverse;
	
	public void setForwardLimit(double forward) {
		this.forward = forward;
	}
	
	public void setReverseLimit(double reverse) {
		this.reverse = reverse;
	}
	
	public PositionLimitedTalon(int device) {
		super(device);
	}

	@Override
	public String getSmartDashboardType() {
		return super.getSmartDashboardType();
	}

	@Override
	public void init() {
		;
	}

	@Override
	public void update() {
		if (this.getPosition()+Constants.encoderOffset > this.forward && 
				!RobotMap.limitOveride.Pressedp()) {
			//this.set(this.getSetpoint()-0.1);
		}
		if (this.getPosition()+Constants.encoderOffset < this.reverse && 
				!RobotMap.limitOveride.Pressedp()) {
			//this.set(this.getSetpoint()+0.1);
		}
	}
	
	@Override
	public void set(double pos) {
		pos += Constants.encoderOffset;
		if (pos < this.forward && !RobotMap.limitOveride.Pressedp()) {
			super.set(this.forward);
		} else if (pos > this.reverse && !RobotMap.limitOveride.Pressedp()) {
			super.set(this.reverse);
		}
		super.set(pos);
	}

}
