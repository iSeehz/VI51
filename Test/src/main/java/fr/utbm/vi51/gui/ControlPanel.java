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
	private JButton stepBystepButton;
	private JButton stopButton;
	public ControlPanel() {
		
		// initialize Buttons
		playButton = new JButton("Play");
		stepBystepButton = new JButton("Coup par coup");
		stopButton = new JButton("Stop");
		//set the Panel
		this.setLayout(new FlowLayout());
		this.add(playButton);
		this.add(stepBystepButton);
		this.add(stopButton);
		
	}
	public JButton getPlayButton() {
		return playButton;
	}
	public JButton getStepBystepButton() {
		return stepBystepButton;
	}
	public JButton getStopButton() {
		return stopButton;
	}
	
	
}
