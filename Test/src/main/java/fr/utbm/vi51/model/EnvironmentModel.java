package fr.utbm.vi51.model;
import java.util.List;

import fr.utbm.vi51.gui.FrameProject;
import fr.utbm.vi51.parser.JSONReadAndConvertingFromFile;

public class EnvironmentModel {
	
	private List<List<Cell>> grid;
	private int numberOfBody;
	private Cell entry;
	private Cell exit;

	public EnvironmentModel(String level, int numberOfBody) {
		
		this.setGrid(level,numberOfBody);
		
	}

	public List<List<Cell>> getGrid() {
		return grid;
	}
	
	public void setGrid(String level, int numberOfBody){
		
		this.numberOfBody = numberOfBody;
		JSONReadAndConvertingFromFile js = new JSONReadAndConvertingFromFile(level);
		this.grid = js.convertingContentForGame();
		if(js.getEntryPosition() != null) this.entry = this.grid.get((int) js.getEntryPosition().getX()).get((int) js.getEntryPosition().getY());
		if(js.getExitPosition() != null) this.exit = this.grid.get((int) js.getExitPosition().getX()).get((int) js.getExitPosition().getY());
		
		for (int i=0;i<this.numberOfBody;i++){
			
			this.entry.getListOfBodyInCell().add(new LemmingBody(i+1,this.entry.getX() , this.entry.getY()));
		}
	}

	public Cell getEntry() {
		return entry;
	}

	public Cell getExit() {
		return exit;
	}

	
}
