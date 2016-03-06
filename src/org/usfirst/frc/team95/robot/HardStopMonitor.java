package org.usfirst.frc.team95.robot;

import edu.wpi.first.wpilibj.Timer;

public class HardStopMonitor implements PollableSubsystem {
	Timer time = new Timer();

	@Override
	public void init() {
		time.reset();
		time.stop();
	}

	@Override
	public void update() {
		// if (RobotMap.pdb.getCurrent(15) > Constants.hardStopThreshold) {
		// time.st
		// }
	}

}
