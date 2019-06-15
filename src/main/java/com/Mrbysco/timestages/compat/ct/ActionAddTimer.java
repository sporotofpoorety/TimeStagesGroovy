package com.mrbysco.timestages.compat.ct;

import com.mrbysco.timestages.TimeStages;

import crafttweaker.IAction;

public class ActionAddTimer implements IAction {

	private final String uniqueID;
	private final String stage;
	private final String nextStage;
	private final int time;
	private final String amount;  
	private final boolean removal;
	private final boolean removeOld;

	public ActionAddTimer(String ID, String stage, String nextStage, int time, String amount, boolean removal, boolean removeOld) {
		this.uniqueID = ID;
		this.stage = stage;
		this.nextStage = nextStage;
		this.time = time;
		this.amount = amount;
		this.removal = removal;
		this.removeOld = removeOld;
	}
	
	@Override
	public void apply() {
		if (this.removal)
			TimeStages.addTimerInfo(uniqueID, stage, nextStage, time, amount, true, removeOld);
		else
			TimeStages.addTimerInfo(uniqueID, stage, nextStage, time, amount, false, false);
	}

	@Override
	public String describe() {
		if (this.removal)
			return String.format(this.stage + "will be locked in %d %s", this.time, this.amount);	
		else
			return String.format("%d %s has been added to unlock stage " + this.nextStage, this.time, this.amount);	
	}

}
