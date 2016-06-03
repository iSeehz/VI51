package fr.utbm.vi51.model;

import java.util.ArrayList;
import java.util.List;

import fr.utbm.vi51.parser.JSONReadAndConvertingFromLevelFile;

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
		JSONReadAndConvertingFromLevelFile js = new JSONReadAndConvertingFromLevelFile(level);
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
		List<Percept> allPerception = new ArrayList<Percept>();
		
		LemmingBody body = searchBody(id).getBody();
		int x = body.getX();
		int y = body.getY();
//		System.out.println(x + ":" + y);
//		System.out.println(grid.get(x).get(y+1).getType());
		allPerception.add(new Percept(grid.get(x).get(y)));
		
		//left
		for (int i = 1; i <= body.getFovLeft(); i++){
//			System.out.println((y - i+grid.get(x).size())%grid.get(x).size());
			allPerception.add(new Percept(grid.get(x).get((y - i+grid.get(x).size())%grid.get(x).size())));
		}
		
		//up
		for (int j = 1; j <= body.getFovUp(); j++) {
				if (x - j >= 0) {
					allPerception.add(new Percept(grid.get(x - j).get(y)));
				}else{
					
					allPerception.add(new Percept(new Cell(-1, -1, TypeObject.WALL)));
				}
		}
		
		//right
		for (int i = 1; i <= body.getFovRight(); i++){
			allPerception.add(new Percept(grid.get(x).get((y + i)%grid.get(x).size())));
		}
		
		//down
		for (int j = 1; j <= body.getFovUnder(); j++) {
			if (x + j < grid.size()) {
				allPerception.add(new Percept(grid.get(x + j).get(y)));
			}else{
				
				allPerception.add(new Percept(new Cell(-1, -1, TypeObject.EMPTY)));
			}
		}			
				
				
				
		//left up
		for (int i = 1; i <= body.getFovLeft(); i++) {
			for (int j = 1; j <= body.getFovUp(); j++) {
				if (x - j >= 0 && y - i >= 0) {
					allPerception.add(new Percept(grid.get(x - j).get(y - i)));
				}else{
					
					allPerception.add(new Percept(new Cell(-1, -1, TypeObject.WALL)));
				}
			}
		}
		
		//right up
		for (int i = 1; i <= body.getFovRight(); i++) {
			for (int j = 1; j <= body.getFovUp(); j++) {
				if (x - j >= 0 && y + i <= grid.get(x).size()) {
					allPerception.add(new Percept(grid.get(x - j).get(y + i)));
				}else{
					
					allPerception.add(new Percept(new Cell(-1, -1, TypeObject.WALL)));
				}
			}
		}

		//right down
		for (int i = 1; i <= body.getFovRight(); i++) {
			for (int j = 1; j <= body.getFovUnder()-1; j++) {
				if ( (x + j) < grid.size() ) {
					allPerception.add(new Percept(grid.get(x + j).get((y + i)%grid.get(x).size())));
				}else{
					
					allPerception.add(new Percept(new Cell(-1, -1, TypeObject.EMPTY)));
				}
			}
		}
		
		//left down
		for (int i = 1; i <= body.getFovLeft(); i++) {
			for (int j = 1; j <= body.getFovUnder()-1; j++) {
				if ( (x + j) < grid.size()) {
					allPerception.add(new Percept(grid.get(x + j).get((y - i+grid.get(x).size())%grid.get(x).size())));
				}else{
					
					allPerception.add(new Percept(new Cell(-1, -1, TypeObject.EMPTY)));
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
		if (isLand(x+1, y)){
		//all moves allowed			
			if(move == PossibleMove.MOVEBACKWARD){
				body.increaseFatigue();
				if (body.getOrientation().equals(Orientation.RIGHT)){
					return moveLeft(body, p);
				} else{
					return moveRight(body,p);
				}
			}
			else if(move == PossibleMove.MOVEFORWARD){
				if (body.getOrientation().equals(Orientation.LEFT)){
					return moveLeft(body, p);
				} else{
					return moveRight(body,p);
				}
			}
			else if(move == PossibleMove.CLIMBFORWARD){
				return climbingBody(body, p, false);
			}
			else if(move == PossibleMove.CLIMBBACKWARD){
				return climbingBody(body, p, true);			
			}
			else if(move == PossibleMove.DIG){
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
			}

		// body climbing
		
		} else if (body.isClimbing() && move.equals(PossibleMove.CLIMBFORWARD)){ 
			
			return climbingBody(body, p, false);
			
		} else if (body.isClimbing() && move.equals(PossibleMove.CLIMBBACKWARD)){
			
			body.increaseFatigue();
			return climbingBody(body, p, true);
			
		// body parachute
		} else if (move.equals(PossibleMove.PARACHUTE)){
			if (y +1 < grid.get(x).size()){
				body.activateParachute();
				fallingBody(body, p);
				statusBody(body);
				return true;
			} else{
				return false;
			}
		} else {
			if (y +1 < grid.get(x).size()){
				if (!body.statusParachute()){
					body.fall();
				}
				fallingBody(body, p);
				statusBody(body);
				return true;
			} else {
				return false;
			}
		}
		return false;
		
			
	}
	public boolean moveLeft(LemmingBody body, int p){
		int x = body.getX();
		int y = body.getY();
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
	}

	public boolean moveRight(LemmingBody body, int p){
		int x = body.getX();
		int y = body.getY();
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
		
	}

	// Action after any move
	public void statusBody(LemmingBody body) {
		
		int x = body.getX();
		int y = body.getY();
		
		// Body on the Exit
		if (isExit(x, y)){
			body.winner();
			outLemming(body);
		// if the body is on a land nothing to do except disable parachute and stop climbing
		} else if (isLand(x,y+1)){
			if (body.getOrientation().equals(Orientation.DOWN)){
				if(body.isClimbing()){
					body.increaseFatigue();
					body.getUp();
				} else {
					body.getUp();
					body.resetFatigue();
				}		
			}
		// if the body is falling
		} else if (accessibleCase(x, y+1) && !body.isClimbing() && !body.statusParachute()){
			body.fall();
			body.setOrientation(Orientation.DOWN);
			 if (body.getFall()==3){
				body.setOrientation(Orientation.DEAD);
				killLemming(body);
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
	
	public boolean climbingBody (LemmingBody body, int p, boolean type){
		// True  = Backward
		// False = Forward
		int x = body.getX();
		int y = body.getY();
		
		if (type){ //BACKWARD
			if(body.getOrientation().equals(Orientation.LEFT)){ //Try Jump Right First
				if(canJump(x,y, false)){ // Try to jump right
					grid.get(x+1).get(y-1).getListOfBodyInCell().add(body);
					grid.get(x).get(y).getListOfBodyInCell().remove(p);
					body.setX(x+1);
					body.setY(y-1);
					body.setOrientation(Orientation.RIGHT);
					statusBody(body);
					return true;
				} else if (canClimb(x,y)){ // Try to climb
					grid.get(x).get(y-1).getListOfBodyInCell().add(body);
					grid.get(x).get(y).getListOfBodyInCell().remove(p);
					body.setY(y-1);
					body.setOrientation(Orientation.RIGHT);
					body.startClimbing();
					statusBody(body);
					return true;
				} else { //Stay where you are
					body.startClimbing();
					return false;
				}
			} else {
				if(canJump(x,y, true)){ // Try to jump left
					grid.get(x-1).get(y-1).getListOfBodyInCell().add(body);
					grid.get(x).get(y).getListOfBodyInCell().remove(p);
					body.setX(x-1);
					body.setY(y-1);
					body.setOrientation(Orientation.LEFT);
					statusBody(body);
					return true;
				} else if (canClimb(x,y)){ // Try to climb
					grid.get(x).get(y-1).getListOfBodyInCell().add(body);
					grid.get(x).get(y).getListOfBodyInCell().remove(p);
					body.setY(y-1);
					body.setOrientation(Orientation.LEFT);
					body.startClimbing();
					statusBody(body);
					return true;
				} else { //Stay where you are
					body.startClimbing();
					return false;
				}
			}
		} else { //FORWARD
			if(body.getOrientation().equals(Orientation.RIGHT)){ //Try Jump Right First
				if(canJump(x,y, false)){ // Try to jump right
					grid.get(x+1).get(y-1).getListOfBodyInCell().add(body);
					grid.get(x).get(y).getListOfBodyInCell().remove(p);
					body.setX(x+1);
					body.setY(y-1);
					body.startClimbing();
					statusBody(body);
					return true;
				} else if (canClimb(x,y)){ // Try to climb
					grid.get(x).get(y-1).getListOfBodyInCell().add(body);
					grid.get(x).get(y).getListOfBodyInCell().remove(p);
					body.setY(y-1);
					body.startClimbing();
					statusBody(body);
					return true;
				} else { //Stay where you are
					body.startClimbing();
					return false;
				}
			} else {
				if(canJump(x,y, true)){ // Try to jump left
					grid.get(x-1).get(y-1).getListOfBodyInCell().add(body);
					grid.get(x).get(y).getListOfBodyInCell().remove(p);
					body.setX(x-1);
					body.setY(y-1);
					body.startClimbing();
					statusBody(body);
					return true;
				} else if (canClimb(x,y)){ // Try to climb
					grid.get(x).get(y-1).getListOfBodyInCell().add(body);
					grid.get(x).get(y).getListOfBodyInCell().remove(p);
					body.setY(y-1);
					body.startClimbing();
					statusBody(body);
					return true;
				} else { //Stay where you are
					body.startClimbing();
					return false;
				}
			}
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
			if (accessibleCase(x, y-1) && (isLand(x-1,y-1) || isLand(x+1,y-1))){
				return true;
			} else {
				return false;
			}
		}else{
			return false;
		}
	}
	
	// Jump is go on diagonal left-up or right-up
	public boolean canJump(int x, int y, boolean side){
		//TRUE  = LEFT
		//FALSE = RIGHT
		if (side){ // LEFT
			if (x > 0 && y > 0){
				if (accessibleCase(x, y-1) && isLand(x-1, y) && accessibleCase(x-1, y-1)){
					return true;
				} else {
					return false;
				}
			} else return false;
			
		} else {   // RIGHT
			if (x < grid.size() && y > 0){
				if (accessibleCase(x, y-1) && isLand(x+1, y) && accessibleCase(x-1, y+1)){
					return true;
				} else {
					return false;
				}
			} else return false;
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
