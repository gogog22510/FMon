package com.riskval.appmon.dataflow;

public interface Monitorable {
	public void fireStateChanged(String newState);
}
