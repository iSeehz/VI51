package fr.utbm.vi51.gui;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlPanel extends JPanel{

	/**
	 * Panel which contains the different buttons's control 
	 */
	private static final long serialVersionUID = 1728868702650993414L;
	private JButton playButton;
	private JButton pauseButton;
	private JButton stepBystepButton;
	public ControlPanel() {
		// initialize Buttons
		playButton = new JButton("Play");
		pauseButton = new JButton("Pause");
		stepBystepButton = new JButton("Coup par coup");
		
		//set the Panel
		this.setLayout(new FlowLayout());
		this.add(playButton);
		this.add(pauseButton);
		this.add(stepBystepButton);
		
	}
	
}
