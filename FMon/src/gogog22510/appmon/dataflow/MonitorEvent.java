package gogog22510.appmon.dataflow;

public class MonitorEvent {
	public static final int FLOW_INTERRUPT = -1;
	public static final int FLOW_START = 0;
	public static final int FLOW_MOVE = 1;
	public static final int FLOW_END = 2;
	
	protected Object state;
	protected int type;
	
	public MonitorEvent(Object state, int type) {
		this.state = state;
		this.type = type;
	}
}
