package com.Mrbysco.TimeStages;

public class StageInfo {

	private String stage;
	private String nextStage;
	private int time;
	private String amount;
	private boolean removal;
	public int timer;
	
	public StageInfo(String stage, String nextStage, int time, String amount, boolean removal) {
		this.stage = stage;
		this.nextStage = nextStage;
		this.time = time;
		this.amount = amount;
		this.removal = removal;
		this.timer = 0;
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
}
