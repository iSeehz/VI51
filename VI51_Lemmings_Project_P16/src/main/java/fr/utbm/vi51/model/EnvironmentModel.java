package fr.utbm.vi51.model;

import java.util.List;

import fr.utbm.vi51.agent.EnvironmentAgent;
import fr.utbm.vi51.parser.JSONReadAndConvertingFromFile;

public class EnvironmentModel {
	
	private List<List<Cell>> grid;
	private EnvironmentAgent env_agent;
	private int numbreOfBody;
	public EnvironmentModel() {
		
		JSONReadAndConvertingFromFile js = new JSONReadAndConvertingFromFile("lab_parachute.txt");
		this.grid = js.convertingContentForGame();
		
		
		
		
	}

	public List<List<Cell>> getGrid() {
		return grid;
	}
	
	

}
