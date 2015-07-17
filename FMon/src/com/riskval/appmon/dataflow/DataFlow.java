package com.riskval.appmon.dataflow;

import java.util.ArrayList;
import java.util.List;

public class DataFlow implements SequentialFlow {
	protected List<Process> processes;
	protected int cur_pointer; 
	
	private DataFlow() {
		processes = new ArrayList<Process>();
		cur_pointer = -1;
	}
	
	public DataFlow(String[] processes) {
		this();
		for(int i=0; i<processes.length; i++) {
			this.processes.add(new Process(processes[i]));
		}
	}
	
	@Override
	public Process expected() {
		if((cur_pointer+1) < processes.size()) return processes.get(cur_pointer+1);
		return null;
	}

	@Override
	public Process previous() {
		if(cur_pointer > 0) return processes.get(cur_pointer-1);
		return null;
	}
	
	@Override
	public Process current() {
		if(cur_pointer >= 0) return processes.get(cur_pointer);
		return null;
	}
	
	@Override
	public boolean hasNext() {
		return (cur_pointer+1) < processes.size();
	}
	
	@Override
	public boolean flowIsStart() {
		return cur_pointer > -1;
	}
	
	@Override
	public boolean flowIsEnd() {
		if(cur_pointer == -1) return false;
		return !hasNext() && current().isEnd();
	}

	@Override
	public void flowMove() {
		if(!flowIsEnd()) {
			if(cur_pointer == -1) {
				cur_pointer++;
			} else {
				if(current().isEnd()) cur_pointer++;
				else current().setIsEnd(true);
			}
		}
	}

	@Override
	public void flowReset() {
		for(int i=cur_pointer; i>=0; i--)
			processes.get(i).setIsEnd(false);
		cur_pointer = -1;
	}
}
