package fr.utbm.vi51.model;

public class LemmingBody extends EnviromnentObject{

	private int fovUp;
	private int fovUnder;
	private int fovLeft;
	private int fovRight;
	
	private int fall;
	
	private boolean parachute;
	
	private Integer id;
	
	public LemmingBody(int id ,int x, int y) {
		// TODO Auto-generated constructor stub
		super(x,y);
		
		this.id = id;
		//field of View of the body
		fovUp = 1;
		fovRight = 1;
		fovLeft = 1;
		fovUnder = 4;
		
		this.fall = 0;
		
		this.parachute = false;
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
	}
	
	public void disactivateParachute(){
		this.parachute = false;
	}
	public void fall(){
		this.fall ++;
	}
	
}
