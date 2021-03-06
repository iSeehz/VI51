package fr.utbm.vi51.gui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class OptionPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5787082889364243791L;
	private JButton halfTurnAllButton;
	private JButton digAllButton;
	private JButton killRandomLemming;

	private JComboBox<String> changeLevel;
	private int lemmingFree;
	
	private String print;
	private JLabel data;
	
	public OptionPanel(int numberOfLemmings) {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.lemmingFree = numberOfLemmings;
		//set the Table
		this.print =  "<html>Lemmings lachés: " + lemmingFree + " Total : 100%" + "<br>"
	             + "Lemmings en vie :" + "0"+ "<br> Total : 0%" +"<br>"
	             + "Lemmings arrivés :" + "0"+ "<br> Total : 0%" +"<br>"
	             + "Lemmings morts :" + "0"+ "<br> Total : 0%</html>";
		
		this.data = new JLabel(this.print);
			
		//set Action Button
		halfTurnAllButton = new JButton("Tout le monde, demi tour! ");
		digAllButton = new JButton("Tout le monde, creusez !");
		killRandomLemming = new JButton("Tuez un Lemming ?");
		
		//set the level
		String level[] = {"Etages.txt","Colonnes.txt","Escalier.txt","GrandSaut.txt","GrandSaut2.txt","Enfermement.txt","Enfermement2.txt", "Climber.txt","Lvl1.txt","Lvl2.txt"};
		changeLevel = new JComboBox<String>(level);
		
		this.add(data);
		this.add(Box.createVerticalStrut(50));
		this.add(halfTurnAllButton);
		this.add(Box.createVerticalStrut(50));
		this.add(digAllButton);
		this.add(Box.createVerticalStrut(50));
		this.add(killRandomLemming);
		this.add(Box.createVerticalStrut(50));
		this.add(changeLevel);
		this.add(Box.createVerticalStrut(500));
		
	}
	
	
	
	
	public void updateTab(int lemmingAlive,int lemmingSave,int lemmingDead){
            this.print =  "<html>Lemmings lachés: " + this.lemmingFree + " Total : 100%" + "<br>"
   	             + "Lemmings en vie :" + lemmingAlive+ "<br> Total : " + (lemmingAlive/this.lemmingFree)*100 +"%<br>"
   	             + "Lemmings arrivés :" + lemmingSave + "<br> Total : " + (lemmingSave/this.lemmingFree)*100 +"%<br>"
   	             + "Lemmings morts :" + lemmingDead + "<br> Total : " + (lemmingDead/this.lemmingFree)*100 + "%</html>";
        this.data.setText(this.print);
		
	}
	public JComboBox<String> getChangeLevel() {
		return changeLevel;
	}




	public JButton getHalfTurnAllButton() {
		return halfTurnAllButton;
	}




	public JButton getDigAllButton() {
		return digAllButton;
	}




	public JButton getKillRandomLemming() {
		return killRandomLemming;
	}
	
	


}
