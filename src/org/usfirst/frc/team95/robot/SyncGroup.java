/*
 * This File is released under the LGPL.
 * You may modify this software, use it in a project, and so on,
 * as long as this header remains intact.
 */

package org.usfirst.frc.team95.robot;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.PIDOutput;

/**
 * 
 * @author daroc
 */
public class SyncGroup implements SpeedController, PIDOutput {
	SpeedController[] mSpeedControllers;
	boolean[] mReversed;
	double maxSpeed = Math.PI / 4;
	double minSpeed = -Math.PI / 4;
	public boolean manual = false;

	public SyncGroup(SpeedController[] SpeedControllers) {
		mSpeedControllers = SpeedControllers;
		mReversed = new boolean[mSpeedControllers.length]; // Initializes to
															// false
		System.out.println("I'm initializing.");
	}

	public SyncGroup(SpeedController[] SpeedControllers, boolean[] Reversed) {
		mSpeedControllers = SpeedControllers;
		mReversed = Reversed;
	}

	public void pidWrite(double speed) {
		// System.out.println("Get the scoundrel!");
		set(speed);
	}

	public void set(double speed) {
		if (speed > maxSpeed) {
			speed = maxSpeed;
		} else if (speed < minSpeed) {
			speed = minSpeed;
		}

		for (int i = 0; i < mSpeedControllers.length; i++) {
			if (mReversed[i]) {
				mSpeedControllers[i].set(-speed);
			} else {
				mSpeedControllers[i].set(speed);
			}
		}
	}

	public void setIrrespective(double speed) {

		for (int i = 0; i < mSpeedControllers.length; i++) {
			if (mReversed[i]) {
				mSpeedControllers[i].set(-speed);
			} else {
				mSpeedControllers[i].set(speed);
			}
		}
	}

	public double getMotor() {
		return mSpeedControllers[0].get();
	}

	public void setMaxSpeed(double speed) {
		maxSpeed = speed;
	}

	public void setMinSpeed(double speed) {
		minSpeed = speed;
	}

	@Override
	public double get() {
		// Since all of the controllers are synchronized, 
		// this should work
		return mSpeedControllers[0].get();
	}

	@Override
	public void set(double speed, byte syncGroup) {
		set(speed);

	}

	@Override
	public void disable() {
		set(0);

	}

	@Override
	public void setInverted(boolean isInverted) {
		// This is not meaningful to call for a SyncGroup.
		
	}

	@Override
	public boolean getInverted() {
		// This is also not meaningful.
		return false;
	}

}
