package com.mrbysco.timestages;

public class StageInfo {

	private String uniqueID;
	private String stage;
	private String nextStage;
	private int time;
	private String amount;
	private boolean removal;
	public int timer;
	private boolean removeOld;
	
	public StageInfo(String ID, String stage, String nextStage, int time, String amount, boolean removal, boolean removeOld) {
		this.uniqueID = ID;
		this.stage = stage;
		this.nextStage = nextStage;
		this.time = time;
		this.amount = amount;
		this.removal = removal;
		this.timer = 0;
		this.removeOld = removeOld;
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
}
