package fr.utbm.vi51.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;

import fr.utbm.vi51.model.Cell;
import fr.utbm.vi51.time.Clock;

public class MainPanel extends JPanel{
	/**
	 * Contains the game's grid and buttons to launch the simulation
	 */
	private static final long serialVersionUID = 2013026409215368873L;
	private ControlPanel controlPanel;
	private GridPanel gridPanel;
	private Clock time;
	public MainPanel(List<List<Cell>> tab) {
		
		// initialize content objects
		this.time = new Clock();
		controlPanel = new ControlPanel();
		gridPanel = new GridPanel(tab);
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());
		
		
		this.add("North",controlPanel);
		this.add("Center",gridPanel);
		
		this.add("South",this.time);
	}
	public ControlPanel getControlPanel() {
		return controlPanel;
	}
	public GridPanel getGridPanel() {
		return gridPanel;
	}
	public Clock getTime() {
		return time;
	}
	
	
}
