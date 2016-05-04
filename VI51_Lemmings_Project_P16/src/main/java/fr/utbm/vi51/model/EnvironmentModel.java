package fr.utbm.vi51.model;
import java.util.List;
import java.util.logging.Level;

import fr.utbm.vi51.agent.EnvironmentAgent;
import fr.utbm.vi51.parser.JSONReadAndConvertingFromFile;
import io.janusproject.Boot;
import io.janusproject.util.LoggerCreator;

public class EnvironmentModel {
	
	private List<List<Cell>> grid;
	private EnvironmentAgent env_agent;
	private int numbreOfBody;
	public EnvironmentModel() {
		
		JSONReadAndConvertingFromFile js = new JSONReadAndConvertingFromFile("lab_parachute.txt");
		this.grid = js.convertingContentForGame();
		
		
		Boot.setOffline(true);
		Boot.setVerboseLevel(LoggerCreator.toInt(Level.INFO));
		Boot.showJanusLogo();
		
	}

	public List<List<Cell>> getGrid() {
		return grid;
	}

}
