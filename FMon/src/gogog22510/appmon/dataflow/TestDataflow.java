package gogog22510.appmon.dataflow;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestDataflow {
	public static void main(String[] args) {
		final JLabelDataFlow pane = new JLabelDataFlow(new String[]{
				"Proc 1",
				"Proc 2",
				"Proc 3",
				"Proc 4",
				"Proc 5"
		});
//		JLabelDataFlow pane2 = new JLabelDataFlow(new String[]{
//				"Proc 1",
//				"Proc 3",
//				"Proc 5"
//		});
		JPanel content = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(0, 0, 5, 0);
		content.add(pane, gbc);
//		gbc.gridy++;
//		content.add(pane2, gbc);
		JFrame frame = new JFrame("Dataflow");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(content);
		frame.pack();
		frame.setVisible(true);
		
		Thread t = new Thread(new Runnable() {			
			@Override
			public void run() {
				while(!pane.isStop()) {
					try {
						pane.next();
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				pane.reset();
				System.out.println("controll flow is stop:"+pane.isStop());
				while(!pane.isStop()) {
					try {
						pane.next();
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
	}
}
