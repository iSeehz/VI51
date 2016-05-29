package fr.utbm.vi51.model;

import java.util.ArrayList;
import java.util.List;

import fr.utbm.vi51.parser.JSONReadAndConvertingFromFile;

public class EnvironmentModel {

	private List<List<Cell>> grid;
	private int numberOfBody;
	private Cell entry;
	private Cell exit;
	private List<LemmingBody> listOfBody;
	private int deads = 0;
	private int out = 0;

	public EnvironmentModel(String level, int numberOfBody) {

		this.setGrid(level, numberOfBody);

	}
	
	public void addDead(){
		this.deads ++;
	}
	
	
	public void addOut(){
		this.out ++;
	}
	
	public int getDead(){
		return this.deads;
	}
	
	public int getOut(){
		return this.out;
	}
	

	public int getNumberOfBody() {
		return numberOfBody;
	}



	public List<List<Cell>> getGrid() {
		return grid;
	}

	public void setGrid(String level, int numberOfBody) {
		this.listOfBody = new ArrayList<LemmingBody>();
		this.numberOfBody = numberOfBody;
		JSONReadAndConvertingFromFile js = new JSONReadAndConvertingFromFile(level);
		this.grid = js.convertingContentForGame();
		if (js.getEntryPosition() != null)
			this.entry = this.grid.get((int) js.getEntryPosition().getX()).get((int) js.getEntryPosition().getY());
		if (js.getExitPosition() != null)
			this.exit = this.grid.get((int) js.getExitPosition().getX()).get((int) js.getExitPosition().getY());

		for (int i = 0; i < this.numberOfBody; i++) {
			this.listOfBody.add(new LemmingBody(i, this.entry.getX(), this.entry.getY()));
			this.entry.getListOfBodyInCell().add(listOfBody.get(i));
		}
	}

	public Cell getEntry() {
		return entry;
	}

	public Cell getExit() {
		return exit;
	}

	// SearchedBody return the position of the body in the list of a Cell
	public SearchedBody searchBody(int id) {
		int p = 0;
		int k = 0;
		while (listOfBody.get(p).getId() != id) {
			p++;
		}
		k = searchBodyInCell(listOfBody.get(p));
		return new SearchedBody(listOfBody.get(p), k);

	}
	
	public int searchBodyInCell (LemmingBody body){
		int k = 0;
		while (grid.get(body.getX()).get(body.getY()).getListOfBodyInCell().get(k).getId() != body.getId()) {
			k++;
		}
		return k;
	}
	
	public List<Percept> getPerception(int id) {
		/*
		 * Disposition of the perception --> 
		 * 08 02 09
		 * 01 00 03
		 * 13 04 10
		 * 14 05 12
		 * 15 06 12
		 *    07
		 */
		List<Percept> allPerception = null;
		
		LemmingBody body = searchBody(id).getBody();
		int x = body.getX();
		int y = body.getY();
		
		allPerception.add(new Percept(grid.get(x).get(y)));
		
		//left
		for (int i = 1; i == body.getFovLeft(); i++){
			if(x - i >= 0) {
				allPerception.add(new Percept(grid.get(x - i).get(y)));
			}
		}
		
		//up
		for (int j = 1; j == body.getFovUp(); j++) {
				if (y - j >= 0) {
					allPerception.add(new Percept(grid.get(x).get(y - j)));
				}
		}
		
		//right
		for (int i = 1; i == body.getFovRight(); i++){
			if(x - i <= grid.size()) {
				allPerception.add(new Percept(grid.get(x + i).get(y)));
			}
		}
		
		//down
		for (int j = 1; j == body.getFovUnder(); j++) {
			if (y + j <= grid.get(x).size()) {
				allPerception.add(new Percept(grid.get(x).get(y + j)));
			}
		}			
				
				
				
		//left up
		for (int i = 1; i == body.getFovLeft(); i++) {
			for (int j = 1; j == body.getFovUp(); j++) {
				if (x - i >= 0 && y - i >= 0) {
					allPerception.add(new Percept(grid.get(x - i).get(y - j)));
				}
			}
		}
		
		//right up
		for (int i = 1; i == body.getFovRight(); i++) {
			for (int j = 1; j == body.getFovUp(); j++) {
				if (x + i < grid.size() && y - i >= 0) {
					allPerception.add(new Percept(grid.get(x + i).get(y - j)));
				}
			}
		}

		//right down
		for (int i = 1; i == body.getFovRight(); i++) {
			for (int j = 0; j == body.getFovUnder(); j++) {
				if (i + j <= body.getFovUnder() && x + i > grid.size() && y - i < grid.get(x).size()) {
					allPerception.add(new Percept(grid.get(x + i).get(x + j)));
				}
			}
		}
		
		//left down
		for (int i = 1; i == body.getFovLeft(); i++) {
			for (int j = 1; j == body.getFovUnder(); j++) {
				if (i + j <= body.getFovUnder() && x - i >= 0 && y - i < grid.get(x).size()) {
					allPerception.add(new Percept(grid.get(x - i).get(x + j)));
				}
			}
		}

		return allPerception;
	}

	// with an id and a move, the body is moved
	public boolean moveBody(int id, PossibleMove move) {

		LemmingBody body = searchBody(id).getBody();
		int p = searchBody(id).getPosition();

		int x = body.getX();
		int y = body.getY();
		
		// check if the body is on a land
		if (isLand(x, y+1)){
		//all moves allowed			
			switch (move.toString()) {

			case "LEFT":

				if ((x > 0) && accessibleCase(x-1, y)) {
					grid.get(x - 1).get(y).getListOfBodyInCell().add(body);
					grid.get(x).get(y).getListOfBodyInCell().remove(p);
					body.setX(x - 1);
					body.setOrientation(Orientation.LEFT);
					statusBody(body);
					return true;
				} else {
					return false;
				}
			case "RIGHT":

				if ((x < grid.size()) && accessibleCase(x+1, y)) {
					grid.get(x + 1).get(y).getListOfBodyInCell().add(body);
					grid.get(x).get(y).getListOfBodyInCell().remove(p);
					body.setX(x + 1);
					body.setOrientation(Orientation.RIGHT);
					statusBody(body);
					return true;
				} else {
					return false;
				}

			case "CLIMB":
				
				return climbingBody(body, p);
			/*  useless here
			 * case "PARACHUTE":
			 * 
				if ((y < grid.get(x).size()-1) && accessibleCase(x, y+1)) {
					grid.get(x).get(y + 1).getListOfBodyInCell().add(body);
					grid.get(x).get(y).getListOfBodyInCell().remove(p);
					body.setOrientation(Orientation.DOWN);
					body.activateParachute();
					return true;
				}
			 */
			

			case "DIG":
				//the case is full Land
				if ((y < grid.get(x).size()-1) && (grid.get(x).get(y + 1).getType().equals(TypeObject.LAND))) {
					grid.get(x).get(y + 1).setType(TypeObject.HALF);
					body.setOrientation(Orientation.DOWN);
				//the case if half land
				} else if (grid.get(x).get(y + 1).getType().equals(TypeObject.HALF)) {
					grid.get(x).get(y + 1).setType(TypeObject.EMPTY);
					// every body on this case fall
					for (int i = 0; i < grid.get(x).get(y).getListOfBodyInCell().size(); i++) {
						grid.get(x).get(y).getListOfBodyInCell().get(p).fall();
						fallingBody(body, p);
					}
					return true;
				} else {
					return false;
				}

			default:

				return false;
			}
		// body climbing
		
		} else if (body.getOrientation().equals(Orientation.UP) && move.equals(PossibleMove.CLIMB)){ 
			
			return climbingBody(body, p);
			
		// body parachute
		} else if (move.equals(PossibleMove.PARACHUTE)){
			if (y +1 < grid.get(x).size()){
				fallingBody(body, p);
				statusBody(body);
				return true;
			} else{
				return false;
			}			
		} else {
			if (y +1 < grid.get(x).size()){
				body.fall();
				fallingBody(body, p);
				statusBody(body);
				return true;
			} else {
				return false;
			}
		}
		
			
	}
	// Action after any move
	public void statusBody(LemmingBody body) {
		
		int x = body.getX();
		int y = body.getY();
		int p = searchBodyInCell(body);
		
		// Body on the exit = body oriented "win" (not deleted yet, do we delete it directly or on next turn ?)	
		if (isExit(x, y)){
			body.setOrientation(Orientation.WIN);
			outLemming(body);
		} else if (body.getFall()==3){
			body.setOrientation(Orientation.DEAD);
			killLemming(body);
		// if the body is on a land nothing to do except disable parachute
		} else if (isLand(x,y+1)){
			body.disactivateParachute();
			
		// if the body is falling
		} else if (accessibleCase(x, y+1) && !body.getOrientation().equals(Orientation.UP) && !body.statusParachute()){
			body.fall();
			
		// body climbing, if the cases up are free, get to the right one, or the left one if right one is not accessible
		} else if (accessibleCase(x, y-1) && body.getOrientation().equals(Orientation.UP)){
			
			if (x-1 >= 0 && x+1<= grid.get(x).size() && y-1 >= 0){
				
				if(accessibleCase(x, y-1) && accessibleCase(x+1, y-1) && isLand(x+1,y)){
					grid.get(x + 1).get(y - 1).getListOfBodyInCell().add(body);
					grid.get(x).get(y).getListOfBodyInCell().remove(p);
					body.setX(x+1);
					body.setY(y-1);
					body.setOrientation(Orientation.RIGHT);
					
				} else if (accessibleCase(x, y-1) && accessibleCase(x-1, y-1) && isLand(x-1,y)){
					grid.get(x - 1).get(y - 1).getListOfBodyInCell().add(body);
					grid.get(x).get(y).getListOfBodyInCell().remove(p);
					body.setX(x-1);
					body.setY(y-1);
					body.setOrientation(Orientation.LEFT);
				}
			}
		}
	}
	public void fallingBody (LemmingBody body, int p){
		
		int x = body.getX();
		int y = body.getY();
		
		grid.get(x).get(y).getListOfBodyInCell().get(p).setOrientation(Orientation.DOWN);
		grid.get(x).get(y + 1).getListOfBodyInCell().add(grid.get(x).get(y).getListOfBodyInCell().get(p));
		grid.get(x).get(y).getListOfBodyInCell().remove(p);
	}
	
	public boolean climbingBody (LemmingBody body, int p){
		
		int x = body.getX();
		int y = body.getY();
		
		if (canClimb(x, y)) {
			grid.get(x).get(y - 1).getListOfBodyInCell().add(body);
			grid.get(x).get(y).getListOfBodyInCell().remove(p);
			body.setY(y - 1);
			body.setOrientation(Orientation.UP);
			statusBody(body);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean accessibleCase (int x, int y){
		if (grid.get(x).get(y).getType().equals(TypeObject.EMPTY) || grid.get(x).get(y).getType().equals(TypeObject.ENTRY) || grid.get(x).get(y).getType().equals(TypeObject.EXIT)){
			return true;
		} else {
			return false;
		}
	}
	public boolean canClimb (int x, int y){
		if (x > 0 && y > 0 && x< grid.size()){
			if (grid.get(x-1).get(y-1).getType().equals(TypeObject.EMPTY) && (isLand(x-1,y-1) || isLand(x+1,y-1))){
				return true;
			} else {
				return false;
			}
		}else{
			return false;
		}
	}
	
	public boolean isLand (int x, int y){
		if (grid.get(x).get(y).getType().equals(TypeObject.HALF) || grid.get(x).get(y).getType().equals(TypeObject.LAND)){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isExit (int x, int y){
		if (grid.get(x).get(y).getType().equals(TypeObject.EXIT)){
			return true;
		} else {
			return false;
		}
	}
	public void killLemming(LemmingBody body){
		addDead();
	}
	
	public void outLemming(LemmingBody body){
		addOut();
	}
}
