package fr.utbm.vi51.model;

public class MovedBody {
	private LemmingBody body;
	private int x;
	private int y;
	
	public MovedBody (LemmingBody b, int a, int c){
		this.body = b;
		this.x = a;
		this.y = c;
	}
	
	public LemmingBody getBody(){
		return this.body;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
}
