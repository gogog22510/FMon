package com.riskval.appmon.dataflow;

public interface ControllableFlowListener {
	public void fireFlowInterrupted(SequentialFlow flow);
}
