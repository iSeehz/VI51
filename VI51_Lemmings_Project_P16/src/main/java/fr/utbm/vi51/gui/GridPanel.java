package fr.utbm.vi51.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import fr.utbm.vi51.model.Cell;

public class GridPanel extends JPanel{
	/**
	 * the panel contains the game's map
	 */
	private static final long serialVersionUID = -4072497686393047571L;
	private int width;
	private int height;
	private JPanel grid [][];
	public GridPanel(List<List<Cell>> tab) {
		// Initialize the grid
		this.width = tab.size();
		this.height = tab.get(0).size();
		this.grid = new JPanel[this.height][this.width];
		
		this.setLayout(new GridLayout(width,height));
		for (int i = 0;i<this.height;i++){
			
			for (int j = 0;j<this.width;j++){
				grid[i][j] = new JPanel();
				grid[i][j].setBackground(Color.WHITE);
				grid[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				this.add(grid[i][j]);
			}
		}
	}
}
