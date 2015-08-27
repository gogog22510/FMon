package gogog22510.appmon.dataflow;

public interface SequentialFlow {
	public Process expected();
	public Process previous();
	public Process current();
	public boolean hasNext();
	public boolean flowIsStart();
	public boolean flowIsEnd();
	public void flowMove();
	public void flowReset();
}
