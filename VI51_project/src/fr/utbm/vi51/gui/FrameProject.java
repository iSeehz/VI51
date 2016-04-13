package fr.utbm.vi51.gui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class FrameProject extends JFrame {
	
	/**
	 * Main Frame project
	 */
	private static final long serialVersionUID = -8346526694391516641L;
	private JScrollPane scroll;
	private JSplitPane split;
	private MainPanel mainPanel;
	private OptionPanel optionPanel;
	public FrameProject() {
		//initialize object
		mainPanel = new MainPanel();
		optionPanel = new OptionPanel();
		scroll = new JScrollPane(mainPanel);
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainPanel, optionPanel);
		
		// set the Frame 
		this.setTitle("Lemming Game");
		this.setSize(500,400);
		this.setLocation(10,200);
		
		//add scroll to avoid object unreachable in the frame
		this.getContentPane().add(scroll, BorderLayout.CENTER);
		this.add(split);
		addWindowListener(new WindowAdapter() {
	  	public void windowClosing(WindowEvent e) {
		   System.exit(0);
	  	}
	} );
	}
	


}
