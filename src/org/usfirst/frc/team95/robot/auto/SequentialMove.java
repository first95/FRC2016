package org.usfirst.frc.team95.robot.auto;

public class SequentialMove extends Auto {
	int index = 0;
	Auto[] table;
	
	public SequentialMove(Auto[] moves) {
		table = moves;
	}

	@Override
	public void init() {
		table[index].init();
	}

	@Override
	public void update() {
		if (table[index].done()) {
			table[index].stop();
			index += 1;
			table[index].init();
		}
		table[index].update();
	}

	@Override
	public void stop() {
		table[index].stop();
		
	}
	
	@Override
	public boolean done() {
		return table[table.length - 1].done();
	}

}
