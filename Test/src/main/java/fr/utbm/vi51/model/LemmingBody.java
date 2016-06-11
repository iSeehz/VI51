package fr.utbm.vi51.model;

public class LemmingBody extends EnviromnentObject{

	private int fovUp;
	private int fovUnder;
	private int fovLeft;
	private int fovRight;
	
	private int fall;
	private Orientation orientation;
	private boolean parachute;
	private boolean climbing;
	private boolean winner;
	private boolean digging;
	private float fatigue;
	
	private Integer id;
	
	public LemmingBody(int id ,int x, int y) {
		super(x,y);
		
		this.id = id;
		//field of View of the body
		fovUp = 1;
		fovRight = 1;
		fovLeft = 1;
		fovUnder = 4;
		
		this.fall = 0;
		this.orientation = Orientation.RIGHT;
		this.parachute = false;
		this.climbing = false;
		this.winner = false;
		this.digging = false;
		this.fatigue = 1; 
	}
	
	public LemmingBody(int id ,int x, int y, int f, boolean p) {
		super(x,y);
		
		this.id = id;
		fovUp = 1;
		fovRight = 1;
		fovLeft = 1;
		fovUnder = 4;
		
		this.fall = f;
		this.parachute = p;
		this.climbing = false;
		this.winner = false;
		this.fatigue = 1;
	}
		
	
	
	public boolean isDigging() {
		return digging;
	}

	public void setDigging(boolean digging) {
		this.digging = digging;
	}

	public int getId() {
		return id;
	}


	public int getFovUp() {
		return fovUp;
	}

	public int getFovUnder() {
		return fovUnder;
	}


	public int getFovLeft() {
		return fovLeft;
	}

	public int getFovRight() {
		return fovRight;
	}
	
	public void activateParachute(){
		this.parachute = true;
		this.fall = 0;
	}
	
	public void disactivateParachute(){
		this.parachute = false;
		this.fall = 0;
	}
	
	public boolean statusParachute(){
		return this.parachute;
	}
	public void fall(){
		this.fall ++;
	}
	public int getFall(){
		return this.fall;
	}
	
	public void setOrientation(Orientation m){
		this.orientation = m;
	}
	
	public Orientation getOrientation(){
		return this.orientation;
	}
	
	public boolean isClimbing(){
		return this.climbing;
	}
	
	public void startClimbing(){
		this.climbing = true;
	}
	
	public void stopClimbing(){
		this.climbing = false;
	}
	
	public void winner(){
		this.winner = true;
	}
	
	public boolean isWinner(){
		return this.winner;
	}
	
	public void resetFatigue(){
		this.fatigue = 1;
	}
	
	public void increaseFatigue(){
		this.fatigue ++;
	}
	
	public void increaseFatigueWhenChangesSide(){
		this.fatigue += 0.8;
	}
	
	public float getFatigue(){
		return this.fatigue;
	}
	
	//change orientation when getting up
	public void getUp(){
		if (this.fatigue % 2 == 1){
			this.orientation = Orientation.RIGHT;
		} else {
			this.orientation = Orientation.LEFT;
		}
	}
}
