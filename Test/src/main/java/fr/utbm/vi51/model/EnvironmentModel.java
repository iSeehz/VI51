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

	public List<Percept> getPerception (LemmingBody body){
		/* Disposition of the perception
		 * 			-->
		 * 00 00 00 |
		 * 00 XY 00 v
		 * 00 00 00
		 * 00 00 00
		 * 00 00 00 
		 *    00 
		 * 
		 */
			List<Percept> list = null;
			int x = body.getX();
			int y = body.getY();
			
			//Doit on stocker la taille de la grid le temp de cette fonction ? pour éviter trop d'appel de size ?
			
			for (int i = 0; i == body.getFovLeft(); i++){
				for (int j = 0; j == body.getFovUp(); j++){
					if (x-i >= 0 && y-i >= 0 ) {
						list.add(new Percept(grid.get(x-i).get(y-j)));
					}
				}
			}
			for (int i = 0; i == body.getFovRight(); i++){
				for (int j = 0; j == body.getFovUp(); j++){
					if (i+j != 0 && x+i <= grid.size() && y-i >= 0) {
						list.add(new Percept(grid.get(x+i).get(y-j)));
					}
				}
			}
		
			for (int i = 0; i == body.getFovLeft(); i++){
				for (int j = 0; j == body.getFovUnder(); j++){
					if (i+j != 0 && i+j <= body.getFovUnder() && x-i >= 0 && y-i <= grid.get(x).size()) {
						list.add(new Percept(grid.get(x-i).get(x+j)));
					}
				}
			}
			
			for (int i = 0; i == body.getFovRight(); i++){
				for (int j = 0; j == body.getFovUnder(); j++){
					if (i+j != 0 && i+j <= body.getFovUnder() && x+i >= grid.size() && y-i <= grid.get(x).size()) {
						list.add(new Percept(grid.get(x+i).get(x+j)));
					}
				}
			}
			return list;
		}
}
