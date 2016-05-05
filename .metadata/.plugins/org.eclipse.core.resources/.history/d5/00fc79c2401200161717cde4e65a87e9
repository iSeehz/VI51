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
		generate(tab);
	}
	
	public void generate(List<List<Cell>> tab){
		
		this.width = tab.get(0).size();
		this.height = tab.size();
		this.grid = new JPanel[this.height][this.width];
		
		this.setLayout(new GridLayout(this.height,this.width));
		
		paint(tab);
	}
	
	//put some colors depending the type of the cells
	public void paint(List<List<Cell>> tab){
		
		for (int i = 0;i<this.height;i++){
			
			for (int j = 0;j<this.width;j++){
				Color c = Color.WHITE;
				
				grid[i][j] = new JPanel();
				grid[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				
				switch(tab.get(i).get(j).getType()){
				
				case EMPTY: grid[i][j].setBackground(Color.WHITE);
					break;
				case LAND: grid[i][j].setBackground(Color.DARK_GRAY);	
					break;
				case ENTRY: grid[i][j].setBackground(Color.MAGENTA);
					break;
				case EXIT: grid[i][j].setBackground(Color.GREEN);
					break;
				case HALF: grid[i][j].setBackground(Color.GRAY);
					break;
				default:
				}
				this.add(grid[i][j]);
				
			}
		}
	}
}
