package fr.utbm.vi51.gui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class FrameProject extends JFrame {
	
	/**
	 * Main Frame project
	 */
	private static final long serialVersionUID = -8346526694391516641L;
	private MainPanel panel = new MainPanel();
	private JScrollPane scroll = new JScrollPane(panel);
	public FrameProject() {
		// set the Frame 
		this.setTitle("Lemming Game");
		this.setLocation(10,200);
		//add scroll to avoid object unreachable in the frame
		this.getContentPane().add(scroll, BorderLayout.CENTER);
		addWindowListener(new WindowAdapter() {
	  	public void windowClosing(WindowEvent e) {
		   System.exit(0);
	  	}
	} );
	}
	


}
