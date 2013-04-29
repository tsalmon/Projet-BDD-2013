import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.util.*;

public class JScrollPaneOfComponents {

  /**
	 * Default constructor for ScrollBarOfComponents.class
	 */
	public JScrollPaneOfComponents() {
		initComponents();
	}

	/**
	 * Initialize GUI and components (including ActionListeners etc)
	 */
	private void initComponents() {
		JFrame jFrame = new JFrame();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel(new GridLayout(15, 15));

		//create 225 JButtons and add them to JPanel;
		for (int i = 0; i < (15*15); i++) {
			panel.add(new JButton(String.valueOf((i + 1))) {
				//make buttons bigger for demonstartion purposes
				@Override
				public Dimension getPreferredSize() {
					return new Dimension(100, 100);
				}
			});
		}

		JScrollPane scrollpane = new JScrollPane(panel) {
			//size the JScrollPane purposelfully smaller than all components
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(300, 300);
			}
		};

		//add scrollpane to frame
		jFrame.add(scrollpane);

		//pack frame (size JFrame to match preferred sizes of added components and set visible
		jFrame.pack();
		jFrame.setVisible(true);
	}

	public static void main(String[] args) {

		/**
		 * Create GUI and components on Event-Dispatch-Thread
		 */
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					//set nimbus look and feel
					for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				//create new instance of GUI
				JScrollPaneOfComponents test = new JScrollPaneOfComponents();
			}
		});
	}
}
