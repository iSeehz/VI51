package fr.utbm.vi51.model;

import java.util.ArrayList;
import java.util.List;

public class Cell extends EnviromnentObject{

	private TypeObject type;
	private List<LemmingBody> listOfBodyInCell;
	
	public Cell(int x, int y, TypeObject type) {
		// TODO Auto-generated constructor stub
		super(x,y);
		this.type = type;
		listOfBodyInCell = new ArrayList<>();
	}

	public TypeObject getType() {
		return type;
	}

	public void setType(TypeObject type) {
		this.type = type;
	}

	public List<LemmingBody> getListOfBodyInCell() {
		return listOfBodyInCell;
	}

	public void setListOfBodyInCell(List<LemmingBody> listOfBodyInCell) {
		this.listOfBodyInCell = listOfBodyInCell;
	}
	
	
	
}
