package fr.utbm.vi51.gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GridPanel extends JPanel{
	/**
	 * the panel contains the game's map
	 */
	private static final long serialVersionUID = -4072497686393047571L;
	private int width;
	private int height;
	private Object grid [][];
	public GridPanel(int w, int h) {
		// Initialize the grid
		this.width = w;
		this.height = h;
		this.grid = new Object[h][w];
		
		this.setLayout(new GridLayout(width,height));
		for (int i = 0;i<this.height;i++){
			
			for (int j = 0;j<this.width;j++){
				//grid[i][j]
				this.add(new JButton());
			}
		}
	}
}