package gogog22510.appmon.dataflow;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JLabelDataFlow extends JPanel implements Controllable {
	private static final long serialVersionUID = 1L;
	
	private boolean interrupt;
	private DataFlow flow;
	private Map<String, JLabel> procMap;
	
	public JLabelDataFlow() {
		super();
		procMap = new HashMap<String, JLabel>();
		interrupt = false;
	}
	
	public JLabelDataFlow(String[] processes) {
		this();
		flow = new DataFlow(processes);
		initUI(processes);
	}
	
	private void initUI(String[] processes) {
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		add(Box.createVerticalStrut(21));
		add(Box.createHorizontalStrut(5));
		for(int i=0; i<processes.length; i++) {
			JLabel label = new JLabel("<html>"+processes[i]+"</html>");
			label.setPreferredSize(new Dimension(100, 50));
			label.setMaximumSize(new Dimension(100, 50));
			label.setMinimumSize(new Dimension(100, 50));
			label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			procMap.put(processes[i], label);
			add(label);
			add(Box.createHorizontalStrut(5));
		}
		add(Box.createVerticalStrut(21));
	}

	@Override
	public void next() {
		if(!flow.flowIsEnd()) {
			flow.flowMove();
			Process cur = flow.current();
			JLabel curlab = procMap.get(cur.name());
			if(cur.isEnd()) {
				curlab.setBorder(BorderFactory.createLineBorder(Color.RED));
				if(flow.hasNext()) {
					JLabel next = procMap.get(flow.expected().name());
					next.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
				}
			} else {
				curlab.setBorder(BorderFactory.createLineBorder(Color.GREEN));
				if(flow.previous() != null) {
					JLabel prev = procMap.get(flow.previous().name());
					prev.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				}
			}
		}
	}

	@Override
	public void interrupt() {
		interrupt = true;
	}

	@Override
	public void reset() {
		interrupt = false;
		Process cur = flow.current();
		JLabel curlab = procMap.get(cur.name());
		curlab.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		flow.flowReset();
	}

	@Override
	public boolean isStop() {
		return flow.flowIsEnd() || interrupt;
	}
}
