package fr.utbm.vi51.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fr.utbm.vi51.event.GarbageAgent;
import fr.utbm.vi51.parser.JSONReadAndConvertingFromLevelFile;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.EventSpace;
import io.sarl.util.Scopes;

public class EnvironmentModel {

	private List<List<Cell>> grid;
	private int numberOfBody;
	private Cell entry;
	private Cell exit;
	private List<LemmingBody> listOfBody;
	private int deads = 0;
	private int out = 0;

	private final EventSpace space;
	private final UUID uuid;
	private final Address address;
	private final Address addressReceiver;

	private List<Point> ListOfChanges;
	private int bodiesModified;
	
	public EnvironmentModel(String level, int numberOfBody, EventSpace space, Address adr) {

		this.space = space;
		this.uuid = UUID.randomUUID();
		this.address = new Address(space.getID(), this.uuid);
		this.addressReceiver = adr;

		this.setGrid(level, numberOfBody);

	}

	private void emitEvent(Event event) {
		event.setSource(this.address);
		this.space.emit(event, Scopes.addresses(this.addressReceiver));
	}

	public void addDead() {
		this.deads++;
	}

	public void addOut() {
		this.out++;
	}

	public int getDead() {
		return this.deads;
	}

	public int getOut() {
		return this.out;
	}

	public int getNumberOfBody() {
		return numberOfBody;
	}

	public List<List<Cell>> getGrid() {
		return grid;
	}

	public void setGrid(String level, int numberOfBody) {
		this.bodiesModified = 0;
		this.ListOfChanges = new ArrayList<Point>();
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
		// for(int i=0;i<this.grid.size();i++){
		// for(int j=0;j<this.grid.get(0).size();j++){
		// System.out.print(this.grid.get(i).get(j).getType() + " ");
		//
		// }
		// System.out.println();
		// }
	}

	public Cell getEntry() {
		return entry;
	}

	public Cell getExit() {
		return exit;
	}

	// SearchedBody return the position of the body in the list of a Cell
	public synchronized SearchedBody searchBody(int id) {
		int p = 0;
		int k = 0;
		while (listOfBody.get(p).getId() != id) {
			p++;
		}
		k = searchBodyInCell(listOfBody.get(p));
		return new SearchedBody(listOfBody.get(p), k);

	}

	public synchronized int searchBodyInCell(LemmingBody body) {
		int k = 0;
		while (grid.get(body.getX()).get(body.getY()).getListOfBodyInCell().get(k).getId() != body.getId()) {
			k++;
		}
		return k;
	}

	public synchronized List<Percept> getPerception(int id) {
		/*
		 * Disposition of the perception --> 08 02 09 01 00 03 13 04 10 14 05 12
		 * 15 06 12 07
		 */
		List<Percept> allPerception = new ArrayList<Percept>();

		LemmingBody body = searchBody(id).getBody();
		int x = body.getX();
		int y = body.getY();
		// System.out.println(x + ":" + y);
		// System.out.println(grid.get(x).get(y+1).getType());
		allPerception.add(new Percept(grid.get(x).get(y)));

		// left
		for (int i = 1; i <= body.getFovLeft(); i++) {
			// System.out.println((y -
			// i+grid.get(x).size())%grid.get(x).size());
			allPerception.add(new Percept(grid.get(x).get((y - i + grid.get(x).size()) % grid.get(x).size())));
		}

		// up
		for (int j = 1; j <= body.getFovUp(); j++) {
			if (x - j >= 0) {
				allPerception.add(new Percept(grid.get(x - j).get(y)));
			} else {

				allPerception.add(new Percept(new Cell(-1, -1, TypeObject.WALL)));
			}
		}

		// right
		for (int i = 1; i <= body.getFovRight(); i++) {
			allPerception.add(new Percept(grid.get(x).get((y + i) % grid.get(x).size())));
		}

		// down
		for (int j = 1; j <= body.getFovUnder(); j++) {
			if (x + j < grid.size()) {
				allPerception.add(new Percept(grid.get(x + j).get(y)));
			} else {

				allPerception.add(new Percept(new Cell(-1, -1, TypeObject.EMPTY)));
			}
		}

		// left up
		for (int i = 1; i <= body.getFovLeft(); i++) {
			for (int j = 1; j <= body.getFovUp(); j++) {
				if (x - j >= 0) {
					allPerception
							.add(new Percept(grid.get(x - j).get((y - i + grid.get(x).size()) % grid.get(x).size())));
				} else {

					allPerception.add(new Percept(new Cell(-1, -1, TypeObject.WALL)));
				}
			}
		}

		// right up
		for (int i = 1; i <= body.getFovRight(); i++) {
			for (int j = 1; j <= body.getFovUp(); j++) {
				if (x - j >= 0) {
					// System.out.println(x - j + " " + y + i);// a vérif
					allPerception.add(new Percept(grid.get(x - j).get((y + i) % grid.get(x).size())));
				} else {

					allPerception.add(new Percept(new Cell(-1, -1, TypeObject.WALL)));
				}
			}
		}

		// right down
		for (int i = 1; i <= body.getFovRight(); i++) {
			for (int j = 1; j <= body.getFovUnder() - 1; j++) {
				if ((x + j) < grid.size()) {
					allPerception.add(new Percept(grid.get(x + j).get((y + i) % grid.get(x).size())));
				} else {

					allPerception.add(new Percept(new Cell(-1, -1, TypeObject.EMPTY)));
				}
			}
		}

		// left down
		for (int i = 1; i <= body.getFovLeft(); i++) {
			for (int j = 1; j <= body.getFovUnder() - 1; j++) {
				if ((x + j) < grid.size()) {
					allPerception
							.add(new Percept(grid.get(x + j).get((y - i + grid.get(x).size()) % grid.get(x).size())));
				} else {

					allPerception.add(new Percept(new Cell(-1, -1, TypeObject.EMPTY)));
				}
			}
		}

		return allPerception;
	}

	// with an id and a move, the body is moved
	public synchronized boolean moveBody(int id, PossibleMove move) {

		LemmingBody body = searchBody(id).getBody();
		int p = searchBody(id).getPosition();
		int x = body.getX();
		int y = body.getY();
		System.out.println("position :" + (x + 1) + "," + (y + 1));
		//System.out.println("accélération : " + body.getFall());
		
				
		// the case if half land
		if(body.isDigging()){
			grid.get(x + 1).get(y).setType(TypeObject.EMPTY);
			body.setDigging(false);
			// every body on this case fall
			fallingBodyOne(body);
			/*for (int i = 0; i < grid.get(x).get(y).getListOfBodyInCell().size(); i++) {
				grid.get(x).get(y).getListOfBodyInCell().get(p).fall();
				fallingBodyOne(body);
			}*/
			return true;
		}
		
		// check if the body is on a land
		else if (isLand(x + 1, y) || isHalf(x + 1, y)) {
			// all moves allowed
			if (move == PossibleMove.MOVEBACKWARD) {
				body.increaseFatigue();
				if (body.getOrientation().equals(Orientation.RIGHT)) {
					return moveLeft(body, p);
				} else {
					return moveRight(body, p);
				}
			} else if (move == PossibleMove.MOVEFORWARD) {
				if (body.getOrientation().equals(Orientation.LEFT)) {
					return moveLeft(body, p);
				} else {
					return moveRight(body, p);
				}
		/*	} else if (move == PossibleMove.CLIMBFORWARD) {
				return climbingBody(body, p, false);
			} else if (move == PossibleMove.CLIMBBACKWARD) {
				return climbingBody(body, p, true);*/
			} else if (move == PossibleMove.DIG) {
				// the case is full Land
				if ((grid.get(x + 1).get(y).getType().equals(TypeObject.LAND))) {
					grid.get(x + 1).get(y).setType(TypeObject.HALF);
					body.setOrientation(Orientation.DOWN);
					body.setDigging(true);
					addInListOfChanges(new Point(x+1,y));
					return false;
					
				} else {
					return false;
				}
			}

			// body climbing

		} else if (body.isClimbing() && move.equals(PossibleMove.CLIMBFORWARD)) {

			return climbingBody(body, p, false);

		} else if (body.isClimbing() && move.equals(PossibleMove.CLIMBBACKWARD)) {

			body.increaseFatigue();
			return climbingBody(body, p, true);

			// body parachute & falling
		} else if (x + 2 < grid.size()) {
				if(move.equals(PossibleMove.PARACHUTE)){
					body.activateParachute();
				}
				//System.out.println("tombe");
				fallingBody(body, p);
				statusBody(body);
				return true;
			}
		 else if (x + 1 < grid.size()) {
			fallingBody(body, p);
			return true;
		} else {
			grid.get(x).get(y).getListOfBodyInCell().remove(p);
//			System.out.println("le body est parti dans l'espace! fonction Move");
			addInListOfChanges(new Point(x,y));
			killLemming(body);
			return false;
		}
		this.bodiesModified++;
		return false;

	}

	public synchronized boolean moveLeft(LemmingBody body, int p) {
		int x = body.getX();
		int y = body.getY();
		int futurY = (y - 1 + grid.get(x).size()) % grid.get(x).size();
		if ( accessibleCase(x, futurY)) {
			grid.get(x).get(futurY).getListOfBodyInCell().add(body);
			grid.get(x).get(y).getListOfBodyInCell().remove(p);
			body.setY(futurY);
			body.setOrientation(Orientation.LEFT);
			addInListOfChanges(new Point(x,y));
			addInListOfChanges(new Point(x,futurY));
			statusBody(body);
			return true;
		} else {
			return false;
		}
	}

	public  synchronized boolean moveRight(LemmingBody body, int p) {
		int x = body.getX();
		int y = body.getY();
		int futurY = (y + 1) % grid.get(x).size();
		if (accessibleCase(x, futurY)) {
			grid.get(x).get(futurY).getListOfBodyInCell().add(body);
			grid.get(x).get(y).getListOfBodyInCell().remove(p);
			body.setY(futurY);
			body.setOrientation(Orientation.RIGHT);
			addInListOfChanges(new Point(x,y));
			addInListOfChanges(new Point(x,futurY));
			statusBody(body);
			return true;
		} else {
			return false;
		}

	}

	// Action after any move
	public synchronized void statusBody(LemmingBody body) {

		int x = body.getX();
		int y = body.getY();

		// if the body is falling
		if (accessibleCase(x + 1, y) && !body.isClimbing() && !body.statusParachute()) {
			body.fall();
			body.setOrientation(Orientation.DOWN);
		}
		//check if the lemmming crashes himself.
		else if (body.getFall() >= 3 && grid.get(x+1).get(y).getType().equals(TypeObject.LAND)) {
			body.setOrientation(Orientation.DEAD);
			grid.get(x).get(y).getListOfBodyInCell().remove(grid.get(x).get(y).getListOfBodyInCell().indexOf(body));
			addInListOfChanges(new Point(x,y));
			killLemming(body);
		}
		// Body on the Exit
		else if (isExit(x, y)) {
			body.winner();
			System.out.println("Winner!!");
			outLemming(body);
			// if the body is on a land nothing to do except disable parachute
			// and stop climbing
		} else if (isLand(x + 1, y)) {
			if (body.getOrientation().equals(Orientation.DOWN)) {
				if (body.isClimbing()) {
					body.increaseFatigue();
					body.getUp();
				} else {
					body.getUp();
					body.resetFatigue();
				}
			}

		}
		
		if(Orientation.DOWN.equals(body.getOrientation())  &&  !isLand(x , (y+1)%grid.get(0).size()) && !isLand(x, (y-1 + grid.get(0).size())%grid.get(0).size())){
			body.resetFatigue();
		}
	}

	public synchronized void fallingBody(LemmingBody body, int p) {

		int x = body.getX();
		int y = body.getY();

		grid.get(x).get(y).getListOfBodyInCell().get(p).setOrientation(Orientation.DOWN);
		grid.get(x + 1).get(y).getListOfBodyInCell().add(grid.get(x).get(y).getListOfBodyInCell().get(p));
		grid.get(x).get(y).getListOfBodyInCell().remove(p);
		// ajout pour changer la position du lemming
		body.setX(x + 1);
		addInListOfChanges(new Point(x,y));
		addInListOfChanges(new Point(x+1,y));
	}
	
	public synchronized void fallingBodyOne(LemmingBody body) {

		int x = body.getX();
		int y = body.getY();

		grid.get(x + 1).get(y).getListOfBodyInCell().add(body);
		body.setX(x+1);
		grid.get(x).get(y).getListOfBodyInCell().remove(grid.get(x).get(y).getListOfBodyInCell().indexOf(body));
		
		addInListOfChanges(new Point(x,y));
		addInListOfChanges(new Point(x+1,y));
	}
	
	public synchronized void fallingBodyAll(List<LemmingBody> bodies) {

		int x = bodies.get(0).getX();
		int y = bodies.get(0).getY();
		
		for (LemmingBody body:bodies){
				grid.get(x + 1).get(y).getListOfBodyInCell().add(body);
				body.setX(x+1);
		}
		bodies.clear();
		addInListOfChanges(new Point(x,y));
		addInListOfChanges(new Point(x+1,y));

	}

	// ************* pas vérif *********************** les changements ici n'apparaitront pas 
	public synchronized boolean climbingBody(LemmingBody body, int p, boolean type) {
		// True = Backward
		// False = Forward
		int x = body.getX();
		int y = body.getY();

		if (type) { // BACKWARD
			if (body.getOrientation().equals(Orientation.LEFT)) { // Try Jump
																	// Right
																	// First
				if (canJump(x, y, false)) { // Try to jump right
					grid.get(x + 1).get(y - 1).getListOfBodyInCell().add(body);
					grid.get(x).get(y).getListOfBodyInCell().remove(p);
					body.setX(x + 1);
					body.setY(y - 1);
					body.setOrientation(Orientation.RIGHT);
					statusBody(body);
					return true;
				} else if (canClimb(x, y)) { // Try to climb
					grid.get(x).get(y - 1).getListOfBodyInCell().add(body);
					grid.get(x).get(y).getListOfBodyInCell().remove(p);
					body.setY(y - 1);
					body.setOrientation(Orientation.RIGHT);
					body.startClimbing();
					statusBody(body);
					return true;
				} else { // Stay where you are
					body.startClimbing();
					return false;
				}
			} else {
				if (canJump(x, y, true)) { // Try to jump left
					grid.get(x - 1).get(y - 1).getListOfBodyInCell().add(body);
					grid.get(x).get(y).getListOfBodyInCell().remove(p);
					body.setX(x - 1);
					body.setY(y - 1);
					body.setOrientation(Orientation.LEFT);
					statusBody(body);
					return true;
				} else if (canClimb(x, y)) { // Try to climb
					grid.get(x).get(y - 1).getListOfBodyInCell().add(body);
					grid.get(x).get(y).getListOfBodyInCell().remove(p);
					body.setY(y - 1);
					body.setOrientation(Orientation.LEFT);
					body.startClimbing();
					statusBody(body);
					return true;
				} else { // Stay where you are
					body.startClimbing();
					return false;
				}
			}
		} else { // FORWARD
			if (body.getOrientation().equals(Orientation.RIGHT)) { // Try Jump
																	// Right
																	// First
				if (canJump(x, y, false)) { // Try to jump right
					grid.get(x + 1).get(y - 1).getListOfBodyInCell().add(body);
					grid.get(x).get(y).getListOfBodyInCell().remove(p);
					body.setX(x + 1);
					body.setY(y - 1);
					body.startClimbing();
					statusBody(body);
					return true;
				} else if (canClimb(x, y)) { // Try to climb
					grid.get(x).get(y - 1).getListOfBodyInCell().add(body);
					grid.get(x).get(y).getListOfBodyInCell().remove(p);
					body.setY(y - 1);
					body.startClimbing();
					statusBody(body);
					return true;
				} else { // Stay where you are
					body.startClimbing();
					return false;
				}
			} else {
				if (canJump(x, y, true)) { // Try to jump left
					grid.get(x - 1).get(y - 1).getListOfBodyInCell().add(body);
					grid.get(x).get(y).getListOfBodyInCell().remove(p);
					body.setX(x - 1);
					body.setY(y - 1);
					body.startClimbing();
					statusBody(body);
					return true;
				} else if (canClimb(x, y)) { // Try to climb
					grid.get(x).get(y - 1).getListOfBodyInCell().add(body);
					grid.get(x).get(y).getListOfBodyInCell().remove(p);
					body.setY(y - 1);
					body.startClimbing();
					statusBody(body);
					return true;
				} else { // Stay where you are
					body.startClimbing();
					return false;
				}
			}
		}
	}

	public synchronized boolean accessibleCase(int x, int y) {
		System.out.println("accessible : " + x + "," + y);
		if (grid.get(x).get(y).getType().equals(TypeObject.EMPTY)
				|| grid.get(x).get(y).getType().equals(TypeObject.ENTRY)
				|| grid.get(x).get(y).getType().equals(TypeObject.EXIT)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean canClimb(int x, int y) {
		if (x > 0 && y > 0 && x < grid.size()) {
			if (accessibleCase(x, y - 1) && (isLand(x - 1, y - 1) || isLand(x + 1, y - 1))) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	// Jump is go on diagonal left-up or right-up
	public synchronized boolean canJump(int x, int y, boolean side) {
		// TRUE = LEFT
		// FALSE = RIGHT
		if (side) { // LEFT
			if (x > 0 && y > 0) {
				if (accessibleCase(x, y - 1) && isLand(x - 1, y) && accessibleCase(x - 1, y - 1)) {
					return true;
				} else {
					return false;
				}
			} else
				return false;

		} else { // RIGHT
			if (x < grid.size() && y > 0) {
				if (accessibleCase(x, y - 1) && isLand(x + 1, y) && accessibleCase(x - 1, y + 1)) {
					return true;
				} else {
					return false;
				}
			} else
				return false;
		}
	}

	public synchronized boolean isLand(int x, int y) {
//		System.out.println("check ou? " + x + " " + y);
		if (x >= grid.size()) {
			return false;
		}
		if ( grid.get(x).get(y).getType().equals(TypeObject.LAND)) {
			return true;
		} else {
			return false;
		}
	}
	
	public synchronized boolean isHalf(int x, int y) {
		if (x >= grid.size()) {
			return false;
		}
		if ( grid.get(x).get(y).getType().equals(TypeObject.HALF)) {
			return true;
		} else {
			return false;
		}
	}

	public  synchronized boolean isExit(int x, int y) {
		if (grid.get(x).get(y).getType().equals(TypeObject.EXIT)) {
			return true;
		} else {
			return false;
		}
	}

	public synchronized void killLemming(LemmingBody body) {
		addDead();
		numberOfBody--;
		// destruct the body
		this.listOfBody.remove(this.listOfBody.indexOf(body));
		this.emitEvent(new GarbageAgent(body.getId()));
	}

	public synchronized void outLemming(LemmingBody body) {
		//the agent isn't needed anymore when the lemming body is out
		addOut();
		this.emitEvent(new GarbageAgent(body.getId()));
	}

	public  List<LemmingBody> getListOfBody() {
		return listOfBody;
	}

	public int getDeads() {
		return deads;
	}

	public List<Point> getListOfChanges() {
		return ListOfChanges;
	}
	
	protected synchronized void addInListOfChanges(Point p){
		
		if(this.ListOfChanges.indexOf(p)==-1){
			this.ListOfChanges.add(p);
		}
	}

	public int getBodiesModified() {
		return bodiesModified;
	}

	public void restBodiesModified(int bodiesModified) {
		this.bodiesModified = 0;
	}

	
	
}
