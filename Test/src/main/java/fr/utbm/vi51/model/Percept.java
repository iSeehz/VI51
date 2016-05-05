package fr.utbm.vi51.model;

public class Percept {

	private final Cell cell;
	public Percept(Cell cell) {
		this.cell = cell;
	}
	
	public TypeObject getType() {
		return this.cell.getType();
	}
	
	public int getX() {
		return this.cell.getX();
	}

	public int getY() {
		return this.cell.getY();
	}

}
