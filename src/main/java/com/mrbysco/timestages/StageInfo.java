package com.mrbysco.timestages;

public class StageInfo {

	private final String uniqueID;
	private final String stage;
	private final String nextStage;
	private final int time;
	private final String amount;
	private final boolean removal;
	public int timer;
	private final boolean removeOld;
	private final String timerAddMessage;
	private final String timerRemovalMessage;

	public StageInfo(String ID, String stage, String nextStage, int time, String amount, boolean removal, boolean removeOld, String timerAddMessage, String timerRemovalMessage) {
		this.uniqueID = ID;
		this.stage = stage;
		this.nextStage = nextStage;
		this.time = time;
		this.amount = amount;
		this.removal = removal;
		this.timer = 0;
		this.removeOld = removeOld;
		this.timerAddMessage = timerAddMessage;
		this.timerRemovalMessage = timerRemovalMessage;
	}

	public String getStage() {
		return this.stage;
	}

	public String getNextStage() {
		return nextStage;
	}

	public int getTime() {
		return this.time;
	}

	public String getAmount() {
		return this.amount;
	}

	public boolean isRemoval() {
		return this.removal;
	}

	public boolean isRemoveOld() {
		return removeOld;
	}

	public String getUniqueID() {
		return uniqueID;
	}

    public String getTimerAddMessage() {
        return timerAddMessage;
    }

    public String getTimerRemovalMessage() {
        return timerRemovalMessage;
    }
}
