/*
 * This File is released under the LGPL.
 * You may modify this software, use it in a project, and so on,
 * as long as this header remains intact.
 */

package org.usfirst.frc.team95.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * 
 * @author daroc
 */
public class ButtonTracker {
	int mChannel;
	Joystick mJoystick;
	boolean mNow, mLast;

	public ButtonTracker(Joystick Joystick, int Channel) {
		mChannel = Channel;
		mJoystick = Joystick;
	}

	public boolean Pressedp() {
		return mNow;
	}

	public boolean justPressedp() {
		return (mNow && (!mLast));
	}

	public void update() {
		mLast = mNow;
		mNow = mJoystick.getRawButton(mChannel);
	}
}
