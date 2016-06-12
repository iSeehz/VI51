package fr.utbm.vi51.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

	}

	public Cell getEntry() {
		return entry;
	}

	public Cell getExit() {
		return exit;
	}

	// SearchedBody return the position of the body in the list of a Cell
	public synchronized LemmingBody searchBody(int id) {
		LemmingBody body = null;
		int i = 0;
		while (i < this.getListOfBody().size() && body == null) {

			if (id == this.getListOfBody().get(i).getId()) {

				body = this.getListOfBody().get(i);
			}
			i++;
		}
		return body;

	}

	public synchronized List<Percept> getPerception(int id) {
		/*
		 * Disposition of the perception --> 08 02 09 01 00 03 13 04 10 14 05 12
		 * 15 06 12 07
		 */
		List<Percept> allPerception = new ArrayList<Percept>();

		LemmingBody body = searchBody(id);
		int x = body.getX();
		int y = body.getY();
		allPerception.add(new Percept(grid.get(x).get(y)));

		// left
		for (int i = 1; i <= body.getFovLeft(); i++) {
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

		LemmingBody body = searchBody(id);
		if(body==null){return false;}
		int x = body.getX();
		int y = body.getY();

		System.out.println(move);
		
		

		

		if (body.getOrientation().equals(Orientation.DOWN)){
			if(!isLand(x, (y + 1) % grid.get(0).size())	&& !isLand(x, (y - 1 + grid.get(0).size()) % grid.get(0).size())) {
				body.resetFatigue();
		
			}
		}
		if (!(move == PossibleMove.CLIMBBACKWARD || move == PossibleMove.CLIMBFORWARD)) {
			body.stopClimbing();
		}
		

		// the case if half land
		if (body.isDigging()) {
			grid.get(x + 1).get(y).setType(TypeObject.EMPTY);
			body.setDigging(false);
			// every body on this case fall
			fallingBodyOne(body);
			/*
			 * for (int i = 0; i <
			 * grid.get(x).get(y).getListOfBodyInCell().size(); i++) {
			 * grid.get(x).get(y).getListOfBodyInCell().get(p).fall();
			 * fallingBodyOne(body); }
			 */
			return true;
		}
		if (move.equals(PossibleMove.SUICIDE)) {
			grid.get(x).get(y).getListOfBodyInCell().remove(grid.get(x).get(y).getListOfBodyInCell().indexOf(body));
			addInListOfChanges(new Point(x, y));
			killLemming(body);
		}
		// check if the lemming want to kill himself

		// check if the body is on a land
		else if (isLand(x + 1, y) || isHalf(x + 1, y)) {
			// all moves allowed
			if (move == PossibleMove.MOVEBACKWARD) {
				body.increaseFatigue();
				if (body.getOrientation().equals(Orientation.RIGHT)) {
					return moveLeft(body);
				} else {
					return moveRight(body);
				}
			} else if (move == PossibleMove.MOVEFORWARD) {
				if (body.getOrientation().equals(Orientation.LEFT)) {
					return moveLeft(body);
				} else {
					return moveRight(body);
				}
			} else if (move == PossibleMove.CLIMBFORWARD) {
				return climbingBody(body);
			} else if (move == PossibleMove.CLIMBBACKWARD) {
				body.increaseFatigue();
				if (body.getOrientation().equals(Orientation.RIGHT)) {
					body.setOrientation(Orientation.LEFT);
				} else if (body.getOrientation().equals(Orientation.LEFT)) {
					body.setOrientation(Orientation.RIGHT);
				}
				return climbingBody(body);
			} else if (move == PossibleMove.DIG) {
				body.resetFatigue();
				if ((grid.get(x + 1).get(y).getType().equals(TypeObject.LAND))) {
					grid.get(x + 1).get(y).setType(TypeObject.HALF);
					body.setOrientation(Orientation.DOWN);
					body.setDigging(true);
					addInListOfChanges(new Point(x + 1, y));
					return false;

				} else {
					return false;
				}
			}

			// body climbing

		} else if (body.isClimbing() && move.equals(PossibleMove.CLIMBFORWARD)) {

			return climbingBody(body);

		} else if (x + 2 < grid.size()) {
			if (move.equals(PossibleMove.PARACHUTE)) {
				body.activateParachute();
			}
			fallingBodyOne(body);
			statusBody(body);
			return true;
		} else if (x + 1 < grid.size()) {
			fallingBodyOne(body);
			return true;
		} else {
			grid.get(x).get(y).getListOfBodyInCell().remove(grid.get(x).get(y).getListOfBodyInCell().indexOf(body));
			addInListOfChanges(new Point(x, y));
			killLemming(body);
			return false;
		}

		this.bodiesModified++;
		return false;

	}

	public synchronized boolean moveLeft(LemmingBody body) {
		int x = body.getX();
		int y = body.getY();
		int futurY = (y - 1 + grid.get(x).size()) % grid.get(x).size();
		if (accessibleCase(x, futurY)) {
			grid.get(x).get(futurY).getListOfBodyInCell().add(body);
			grid.get(x).get(y).getListOfBodyInCell().remove(grid.get(x).get(y).getListOfBodyInCell().indexOf(body));
			body.setY(futurY);
			body.setOrientation(Orientation.LEFT);
			if ((y - 1) < 0) {
				body.increaseFatigueWhenChangesSide();
			}
			addInListOfChanges(new Point(x, y));
			addInListOfChanges(new Point(x, futurY));
			statusBody(body);
			return true;
		} else {
			return false;
		}
	}

	public synchronized boolean moveRight(LemmingBody body) {
		int x = body.getX();
		int y = body.getY();
		int futurY = (y + 1) % grid.get(x).size();
		if (accessibleCase(x, futurY)) {
			grid.get(x).get(futurY).getListOfBodyInCell().add(body);
			// grid.get(x).get(y).getListOfBodyInCell().remove(p);
			grid.get(x).get(y).getListOfBodyInCell().remove(grid.get(x).get(y).getListOfBodyInCell().indexOf(body));
			body.setY(futurY);
			body.setOrientation(Orientation.RIGHT);
			if ((y + 1) >= grid.get(x).size()) {
				body.increaseFatigueWhenChangesSide();
			}
			addInListOfChanges(new Point(x, y));
			addInListOfChanges(new Point(x, futurY));
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
		// check if the lemmming crashes himself.
		else if (body.getFall() >= 3 && grid.get(x + 1).get(y).getType().equals(TypeObject.LAND)) {
			grid.get(x).get(y).getListOfBodyInCell().remove(grid.get(x).get(y).getListOfBodyInCell().indexOf(body));
			addInListOfChanges(new Point(x, y));
			killLemming(body);
		}
		// Body on the Exit
		else if (isExit(x, y)) {
			body.winner();
			outLemming(body);

		}

	}

	// make one body fall
	public synchronized void fallingBodyOne(LemmingBody body) {

		int x = body.getX();
		int y = body.getY();

		grid.get(x + 1).get(y).getListOfBodyInCell().add(body);
		body.setX(x + 1);
		grid.get(x).get(y).getListOfBodyInCell().remove(grid.get(x).get(y).getListOfBodyInCell().indexOf(body));

		addInListOfChanges(new Point(x, y));
		addInListOfChanges(new Point(x + 1, y));
	}

	//make all bodies on cell fall
	public synchronized void fallingBodyAll(List<LemmingBody> bodies) {

		int x = bodies.get(0).getX();
		int y = bodies.get(0).getY();

		for (LemmingBody body : bodies) {
			grid.get(x + 1).get(y).getListOfBodyInCell().add(body);
			body.setX(x + 1);
		}
		bodies.clear();
		addInListOfChanges(new Point(x, y));
		addInListOfChanges(new Point(x + 1, y));

	}

	//computes the climbing if possible, look game rules to get more explanations
	// test if can climb then if on top or not then move
	public synchronized boolean climbingBody(LemmingBody body) {
		int x = body.getX();
		int y = body.getY();
		int ysize = this.getGrid().get(0).size();
		if (x == 0) {
			statusBody(body);
			return (false);
		} else if (this.getGrid().get(x - 1).get(y).getType() == TypeObject.WALL
				|| this.getGrid().get(x - 1).get(y).getType() == TypeObject.LAND) {
			statusBody(body);
			return (false);
		} else if (body.getOrientation() == Orientation.LEFT
				&& this.getGrid().get(x).get((y - 1 + ysize) % ysize).getType() != TypeObject.LAND
				|| body.getOrientation() == Orientation.RIGHT
						&& this.getGrid().get(x).get((y + 1) % ysize).getType() != TypeObject.LAND) {
			body.stopClimbing();
			statusBody(body);
			return (false);
		} else {
			body.startClimbing();
			if (body.getOrientation() == Orientation.LEFT) {
				if (this.getGrid().get(x - 1).get((y - 1 + ysize) % ysize).getType() == TypeObject.LAND) {
					body.setX(x - 1);
					this.getGrid().get(x).get(y).getListOfBodyInCell()
							.remove(this.getGrid().get(x).get(y).getListOfBodyInCell().indexOf(body));
					this.getGrid().get(x - 1).get(y).getListOfBodyInCell().add(body);
					if(((x-1)==0 || grid.get(x-2).get(y).getType()==TypeObject.LAND))
					{body.increaseFatigue();}
					this.addInListOfChanges(new Point(x, y));
					this.addInListOfChanges(new Point(x - 1, y));
				} else {
					body.setX(x - 1);
					body.setY(y - 1);
					if (body.getY() == (ysize - 1)) {
						body.increaseFatigueWhenChangesSide();
					}
					this.getGrid().get(x).get(y).getListOfBodyInCell()
							.remove(this.getGrid().get(x).get(y).getListOfBodyInCell().indexOf(body));
					this.getGrid().get(x - 1).get((y - 1 + ysize) % ysize).getListOfBodyInCell().add(body);
					this.addInListOfChanges(new Point(x, y));
					this.addInListOfChanges(new Point(x - 1, (y - 1 + ysize) % ysize));
				}
			} else {
				if (this.getGrid().get(x - 1).get((y + 1) % ysize).getType() == TypeObject.LAND) {
					body.setX(x - 1);
					this.getGrid().get(x).get(y).getListOfBodyInCell()
							.remove(this.getGrid().get(x).get(y).getListOfBodyInCell().indexOf(body));
					this.getGrid().get(x - 1).get(y).getListOfBodyInCell().add(body);
					this.addInListOfChanges(new Point(x, y));
					this.addInListOfChanges(new Point(x - 1, y));
					if((x-1)==0 || grid.get(x-2).get(y).getType()==TypeObject.LAND)
					{body.increaseFatigue();}
				} else {
					body.setX(x - 1);
					body.setY((y + 1) % ysize);
					if (body.getY() == 0) {
						body.increaseFatigueWhenChangesSide();
					}
					this.getGrid().get(x).get(y).getListOfBodyInCell()
							.remove(this.getGrid().get(x).get(y).getListOfBodyInCell().indexOf(body));
					this.getGrid().get(x - 1).get((y+1)%ysize).getListOfBodyInCell().add(body);
					this.addInListOfChanges(new Point(x, y));
					this.addInListOfChanges(new Point(x - 1, (y + 1) % ysize));
				}
			}
		}
		statusBody(body);
		return true;
	}

	public synchronized boolean accessibleCase(int x, int y) {
		if (grid.get(x).get(y).getType().equals(TypeObject.EMPTY)
				|| grid.get(x).get(y).getType().equals(TypeObject.ENTRY)
				|| grid.get(x).get(y).getType().equals(TypeObject.EXIT)) {
			return true;
		} else {
			return false;
		}
	}

	public synchronized boolean isLand(int x, int y) {
		if (x >= grid.size()) {
			return false;
		}
		if (grid.get(x).get(y).getType().equals(TypeObject.LAND)) {
			return true;
		} else {
			return false;
		}
	}

	public synchronized boolean isHalf(int x, int y) {
		if (x >= grid.size()) {
			return false;
		}
		if (grid.get(x).get(y).getType().equals(TypeObject.HALF)) {
			return true;
		} else {
			return false;
		}
	}

	public synchronized boolean isExit(int x, int y) {
		if (grid.get(x).get(y).getType().equals(TypeObject.EXIT)) {
			return true;
		} else {
			return false;
		}
	}

	public synchronized void killLemming(LemmingBody body) {
		killLemmingBody(body);
		this.emitEvent(new GarbageAgent(body.getId()));
	}

	public synchronized void killLemmingBody(LemmingBody body) {
		addDead();
		numberOfBody--;
		// destruct the body
		this.listOfBody.remove(this.listOfBody.indexOf(body));
	}

	public synchronized void outLemming(LemmingBody body) {
		// the agent isn't needed anymore when the lemming body is out
		addOut();
		this.emitEvent(new GarbageAgent(body.getId()));
	}

	public List<LemmingBody> getListOfBody() {
		return listOfBody;
	}

	public int getDeads() {
		return deads;
	}

	public List<Point> getListOfChanges() {
		return ListOfChanges;
	}

	protected synchronized void addInListOfChanges(Point p) {

		if (this.ListOfChanges.indexOf(p) == -1) {
			this.ListOfChanges.add(p);
		}
	}

	public int getBodiesModified() {
		return bodiesModified;
	}

	public void restBodiesModified(int bodiesModified) {
		this.bodiesModified = 0;
	}

	public int generateRandomIndexBody() {

		Random rand = new Random();
		int random = rand.nextInt(this.numberOfBody);
		return this.listOfBody.get(random).getId();

	}

}
