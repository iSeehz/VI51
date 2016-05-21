package fr.utbm.vi51.model;

public final class LemmingProperties {
	private int coordinatex;
	private int coordinatey;
	private int fovUp;
	private int fovUnder;
	private int fovLeft;
	private int fovRight;
	
	public LemmingProperties(int a, int b, int c, int d, int e, int f){
		this.coordinatex = a;
		this.coordinatey = b;
		this.fovUp = c;
		this.fovUnder = d;
		this.fovLeft = e;
		this.fovRight = f;
	}
	
	public int getX(){
		return coordinatex;
	}
	
	public int getY(){
		return coordinatey;
	}
	
	public int getfovUp(){
		return fovUp;
	}
	
	public int getfovUnder(){
		return fovUnder;
	}
	
	public int getfovLeft(){
		return fovLeft;
	}
	
	public int getfovRight(){
		return fovRight;
	}
	
	public void setX(int x){
		this.coordinatex = x;
	}
	
	public void setY(int y){
		this.coordinatey = y;
	}
	
}
