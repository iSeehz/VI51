package fr.utbm.vi51.gui;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTable;

public class OptionPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5787082889364243791L;
	Object rowData[][] = { { "Lemmings lachés", "", ""},
            { "Lemmings en vie", "", ""},
            { "Lemmings arrivés", "", ""},
            { "Lemmings morts", "", ""}};
	Object columnNames[] = { "Status", "Nombre", "Pourcentage"};
	
	public OptionPanel() {
		// TODO Auto-generated constructor stub
		super(new GridLayout(4, 1));
		JTable infoLemming = new JTable(rowData, columnNames);
		this.add(infoLemming);
		this.setSize(1000, 500);
	}
	public Object[][] getRowData() {
		return rowData;
	}
	public void setRowData(Object[][] rowData) {
		this.rowData = rowData;
	}

	

}
