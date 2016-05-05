package fr.utbm.vi51.model;
import java.util.List;

import fr.utbm.vi51.gui.FrameProject;
import fr.utbm.vi51.parser.JSONReadAndConvertingFromFile;

public class EnvironmentModel {
	
	private List<List<Cell>> grid;
	private int numbreOfBody;
	

	public EnvironmentModel() {
		
		JSONReadAndConvertingFromFile js = new JSONReadAndConvertingFromFile("lab_parachute.txt");
		this.grid = js.convertingContentForGame();
		


		
	}

	public List<List<Cell>> getGrid() {
		return grid;
	}

}
