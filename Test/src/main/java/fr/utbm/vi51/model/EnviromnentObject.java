package fr.utbm.vi51.model;

public abstract class EnviromnentObject {

	private int x;
	private int y;
	
	public EnviromnentObject(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
		
}
