package fr.utbm.vi51.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MainPanel extends JPanel{
	/**
	 * Contains the game's grid and buttons to launch the simulation
	 */
	private static final long serialVersionUID = 2013026409215368873L;
	private ControlPanel controlPanel;
	private GridPanel gridPanel;
	public MainPanel() {
		// initialize content objects
		controlPanel = new ControlPanel();
		gridPanel = new GridPanel(10, 10);
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());
		
		
		this.add("North",controlPanel);
		this.add("Center",gridPanel);
		this.add("South",new JLabel("time : " + "00:05:20"));
	}
}
