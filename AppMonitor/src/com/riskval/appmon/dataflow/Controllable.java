package com.riskval.appmon.dataflow;

public interface Controllable {
	public void next();
	public void interrupt();
	public void reset();
	public boolean isStop();
}
