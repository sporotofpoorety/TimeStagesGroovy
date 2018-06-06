package com.Mrbysco.TimeStages.util;

public class CurrentMilliTime {
	private long milliTime;

	public CurrentMilliTime() {
		this.milliTime = System.currentTimeMillis();
	}
	
	public void setMilliTimeToCurrent() {
		this.milliTime = System.currentTimeMillis();;
	}

	public long getMilliTime() {
		return this.milliTime;
	}
}
