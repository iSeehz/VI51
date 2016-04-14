package fr.utbm.vi51.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class FrameProject extends JFrame {
	
	/**
	 * Main Frame project
	 */
	private static final long serialVersionUID = -8346526694391516641L;
	private JSplitPane split;
	private MainPanel mainPanel;
	private OptionPanel optionPanel;
	public FrameProject() {
		//initialize object
		mainPanel = new MainPanel();
		optionPanel = new OptionPanel();
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainPanel, optionPanel);
		
		// set the Frame 
		this.setTitle("Lemming Game");
		this.setSize(1000,800);
		this.setLocation(10,200);
		
		//set the OptionPanel
		split.setResizeWeight(1.0);
		
		//add scroll to avoid object unreachable in the frame
		this.add(split);
		addWindowListener(new WindowAdapter() {
	  	public void windowClosing(WindowEvent e) {
		   System.exit(0);
	  	}
	} );
	}
	


}
