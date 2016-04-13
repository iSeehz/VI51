package fr.utbm.vi51.gui;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

public class OptionPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5787082889364243791L;
	private JButton halfTurnAllButton;
	private JButton digAllButton;
	private JButton spawnAnotherLemmingButton;
	Object rowData[][] = { { "Lemmings lachés", "", ""},
            { "Lemmings en vie", "", ""},
            { "Lemmings arrivés", "", ""},
            { "Lemmings morts", "", ""}};
	Object columnNames[] = { "Status", "Nombre", "Pourcentage"};
	
	public OptionPanel() {
		// TODO Auto-generated constructor stub
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//set by default the table which displays the Lemming's current state
		JTable lemmingDatas = new JTable(rowData, columnNames);
		lemmingDatas.setEnabled(false);
		
		
		//set Action Button
		halfTurnAllButton = new JButton("Tout le monde, demi tour! ");
		digAllButton = new JButton("Tout le monde, creusez !");
		spawnAnotherLemmingButton = new JButton("Ajouter un Lemming ?");
		
		this.add(lemmingDatas.getTableHeader());
		this.add(lemmingDatas);
		this.add(Box.createVerticalStrut(50));
		this.add(halfTurnAllButton);
		this.add(Box.createVerticalStrut(50));
		this.add(digAllButton);
		this.add(Box.createVerticalStrut(50));
		this.add(spawnAnotherLemmingButton);
		
	}
	public Object[][] getRowData() {
		return rowData;
	}
	public void setRowData(Object[][] rowData) {
		this.rowData = rowData;
	}

	

}
