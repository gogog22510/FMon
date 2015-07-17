package com.riskval.appmon.dataflow;

public class Process {
	private String name;
	private boolean isEnd;
	
	public Process(String name) {
		this.name = name;
		this.isEnd = false;
	}
	
	public String name() {
		return name;
	}
	
	public boolean isEnd() {
		return isEnd;
	}
	
	public void setIsEnd(boolean end) {
		this.isEnd = end;
	}
}
