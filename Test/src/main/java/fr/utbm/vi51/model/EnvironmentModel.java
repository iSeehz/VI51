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

	public EnvironmentModel(String level, int numberOfBody) {

		this.setGrid(level, numberOfBody);

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
		while (grid.get(listOfBody.get(p).getX()).get(listOfBody.get(p).getY()).getListOfBodyInCell().get(k)
				.getId() != id) {
			k++;
		}
		return new SearchedBody(listOfBody.get(p), k);

	}
	/* oldVersion */
	/*
	 * public SearchedBody searchBody (int id){ int i = 0; int j = 0; int k = 0;
	 * do { j = 0;d while (grid.get(i).get(j).getType().name() != "EMPTY" &&
	 * grid.get(i).get(j).getListOfBodyInCell().get(k).getId() != id && j <
	 * this.grid.get(0).size()){ if
	 * (grid.get(i).get(j).getListOfBodyInCell().size() != 0){ // check if there
	 * is any body in the cell k = 0; while
	 * (grid.get(i).get(j).getListOfBodyInCell().get(k).getId() != id && k <
	 * grid.get(i).get(j).getListOfBodyInCell().size()){ k++; } } j++; } i++; }
	 * while (grid.get(i).get(j).getListOfBodyInCell().get(k).getId() != id && i
	 * < this.grid.size());
	 * 
	 * 
	 * return new SearchedBody(grid.get(i).get(j).getListOfBodyInCell().get(k),
	 * k); }
	 */

	public List<Percept> getPerception(int id) {
		/*
		 * Disposition of the perception --> 00 00 00 | 00 XY 00 v 00 00 00 00
		 * 00 00 00 00 00 00
		 * 
		 */
		List<Percept> list = null;
		LemmingBody body = searchBody(id).getBody();
		int x = body.getX();
		int y = body.getY();

		for (int i = 0; i == body.getFovLeft(); i++) {
			for (int j = 0; j == body.getFovUp(); j++) {
				if (x - i >= 0 && y - i >= 0) {
					list.add(new Percept(grid.get(x - i).get(y - j)));
				}
			}
		}
		for (int i = 0; i == body.getFovRight(); i++) {
			for (int j = 0; j == body.getFovUp(); j++) {
				if (i + j != 0 && x + i < grid.size() && y - i >= 0) {
					list.add(new Percept(grid.get(x + i).get(y - j)));
				}
			}
		}

		for (int i = 0; i == body.getFovLeft(); i++) {
			for (int j = 0; j == body.getFovUnder(); j++) {
				if (i + j != 0 && i + j <= body.getFovUnder() && x - i >= 0 && y - i < grid.get(x).size()) {
					list.add(new Percept(grid.get(x - i).get(x + j)));
				}
			}
		}

		for (int i = 0; i == body.getFovRight(); i++) {
			for (int j = 0; j == body.getFovUnder(); j++) {
				if (i + j != 0 && i + j <= body.getFovUnder() && x + i > grid.size() && y - i < grid.get(x).size()) {
					list.add(new Percept(grid.get(x + i).get(x + j)));
				}
			}
		}
		return list;
	}

	// with an id and a move, the body is moved
	public boolean moveBody(int id, PossibleMove move) {

		LemmingBody body = searchBody(id).getBody();
		int p = searchBody(id).getPosition();

		int x = body.getX();
		int y = body.getY();
		
		// check if the body is on a land
		if (grid.get(x).get(y).getType().equals("LAND")){
			switch (move.toString()) {

			case "LEFT":

				if ((x > 0) && (grid.get(x - 1).get(y).getType().name().equals("EMPTY"))) {
					grid.get(x - 1).get(y).getListOfBodyInCell().add(body);
					grid.get(x).get(y).getListOfBodyInCell().remove(p);
					body.setX(x - 1);
					body.setOrientation(Orientation.LEFT);
					return true;
				}
				break;
			case "RIGHT":

				if ((x < grid.size()) && (grid.get(x + 1).get(y).getType().name().equals("EMPTY"))) {
					grid.get(x + 1).get(y).getListOfBodyInCell().add(body);
					grid.get(x).get(y).getListOfBodyInCell().remove(p);
					body.setX(x + 1);
					body.setOrientation(Orientation.RIGHT);
					return true;
				}
				break;

			case "CLIMB":

				if ((y > 0) && (grid.get(x).get(y - 1).getType().name().equals("EMPTY"))) {
					grid.get(x).get(y - 1).getListOfBodyInCell().add(body);
					grid.get(x).get(y).getListOfBodyInCell().remove(p);
					body.setY(y - 1);
					body.setOrientation(Orientation.UP);
					return true;
				}
				break;

			case "PARACHUTE":

				if ((y < grid.get(x).size()) && (grid.get(x).get(y + 1).getType().name().equals("EMPTY"))) {
					grid.get(x).get(y + 1).getListOfBodyInCell().add(body);
					grid.get(x).get(y).getListOfBodyInCell().remove(p);
					body.setOrientation(Orientation.DOWN);
					return true;
				}

				break;

			case "DIG":

				if ((y < grid.get(x).size()) && (grid.get(x).get(y + 1).getType().name().equals("LAND"))) {
					grid.get(x).get(y + 1).setType(TypeObject.HALF);
					body.setOrientation(Orientation.DOWN);
				} else if (grid.get(x).get(y + 1).getType().name().equals("HALF")) {
					grid.get(x).get(y + 1).setType(TypeObject.EMPTY);
					// every body on this case fall
					for (int i = 0; i < grid.get(x).get(y).getListOfBodyInCell().size(); i++) {
						// mutex ?
						grid.get(x).get(y).getListOfBodyInCell().get(i).fall();
						grid.get(x).get(y + 1).getListOfBodyInCell().add(grid.get(x).get(y).getListOfBodyInCell().get(i));
						grid.get(x).get(y).getListOfBodyInCell().remove(i);

					}
					return true;
				}
				break;

			default:

				return false;
			}
			return false;
		}
		

	}

	public void fallingBody(LemmingBody body) {

	}

	public void statusBody(MovedBody m) {
		LemmingBody body = m.getBody();
		int x = m.getX();
		int y = m.getY();
		int p = 0;
		int i = 0;
		if (((y + 1) < grid.get(x + 1).size()) && grid.get(x).get(y + 1).getType().equals("EMPTY")) {
			while (grid.get(x).get(y).getListOfBodyInCell().get(p).getId() != body.getId()
					&& p < grid.get(x).get(y).getListOfBodyInCell().size()) {
				p++;
			}
			while (((y + 1) < grid.get(x + i).size()) && grid.get(x).get(y + i).getType().equals("EMPTY")) {

			}

		}
	}

	public void activiateParachute(int id) {
		LemmingBody body = searchBody(id).getBody();
		int p = searchBody(id).getPosition();
		grid.get(body.getX()).get(body.getY()).getListOfBodyInCell().get(p).activateParachute();
	}

	public void disactiviateParachute(int id) {
		LemmingBody body = searchBody(id).getBody();
		int p = searchBody(id).getPosition();
		grid.get(body.getX()).get(body.getY()).getListOfBodyInCell().get(p).disactivateParachute();
	}

}
