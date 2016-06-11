package fr.utbm.vi51.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.utbm.vi51.model.Cell;
import fr.utbm.vi51.model.TypeObject;

public class GridPanel extends JPanel{
	/**
	 * the panel contains the game's map
	 */
	private static final long serialVersionUID = -4072497686393047571L;
	private int width;
	private int height;
	private JPanel grid [][];
	private BufferedImage  lemmingLogo;
	public GridPanel(List<List<Cell>> tab) {
		
		//initialize the picture
		try {
		    String path = System.getProperty("user.dir") + "/src/main/resources/fr/utbm/vi51/img/lemming.png";
		    this.lemmingLogo = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
		
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
		this.removeAll();
		
		for (int i = 0;i<this.height;i++){
			
			for (int j = 0;j<this.width;j++){
				this.grid[i][j] = createPanelByType(tab.get(i).get(j).getType());

				if(tab.get(i).get(j).getListOfBodyInCell().size() != 0){
					this.grid[i][j].add(drawLemming(tab.get(i).get(j)));
				}
				this.add(this.grid[i][j]);
				
			}
		}
		this.updateUI();
	}
	
	public JPanel drawLemming(Cell c){
		
		JLabel picLabel = new JLabel(new ImageIcon(this.lemmingLogo.getScaledInstance(20, 20, 20)));
		JPanel panel = new JPanel();
		panel.add(picLabel);
		panel.add(new JLabel(""+c.getListOfBodyInCell().size()));
		return panel;
	}
	
	
	
	public JPanel createPanelByType(TypeObject to){
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		switch(to){
		
		case EMPTY: panel.setBackground(Color.WHITE);
			break;
		case LAND: panel.setBackground(Color.DARK_GRAY);	
			break;
		case ENTRY: panel.setBackground(Color.MAGENTA);
			break;
		case EXIT: panel.setBackground(Color.GREEN);
			break;
		case HALF: panel.setBackground(Color.GRAY);
			break;
		default:
		}
		return panel;
	}
	
	
	public synchronized  void smartPaint(List<Point> listOfChange,List<List<Cell>> tab){
		//converting Point to index for the GridLayout
		int index;
		for(Point p : listOfChange){
			if (p.x == 0){
				index = p.x + p.y;
			}else{
				index = p.x * tab.get(0).size() + p.y;
			}
			this.grid[p.x][p.y] = createPanelByType(tab.get(p.x).get(p.y).getType());
			if(tab.get(p.x).get(p.y).getListOfBodyInCell().size() != 0){
				this.grid[p.x][p.y].add(drawLemming(tab.get(p.x).get(p.y)));
			}
			this.remove(index);
			this.add(this.grid[p.x][p.y],index);
//			System.out.println(p);
		}
		this.updateUI();
		listOfChange.clear();
	}
}
