package fr.utbm.vi51.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MainPanel extends JPanel{
	/**
	 * Contains the game's grid and buttons to launch the simulation
	 */
	private static final long serialVersionUID = 2013026409215368873L;
	private JScrollPane scroll;
	private ControlPanel control;
	public MainPanel() {
		// initialize content objects
		control = new ControlPanel();
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());
		//scroll = new JScrollPane();
		this.add("North",control);
	}
}
