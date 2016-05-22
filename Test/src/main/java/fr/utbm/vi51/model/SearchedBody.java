package fr.utbm.vi51.model;

public class SearchedBody {
	private LemmingBody body;
	private int position;
	
	
	
	public SearchedBody (LemmingBody b, int p){
		this.body = b;
		this.position = p;
	}
	
	public LemmingBody getBody(){
		return this.body;
	}
	
	public int getPosition(){
		return this.position;
	}
}
