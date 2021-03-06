package fr.utbm.vi51.parser;

import java.awt.Point;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import fr.utbm.vi51.model.Cell;
import fr.utbm.vi51.model.TypeObject;


public class JSONReadAndConvertingFromLevelFile {

	/**
	 * Parse the json.txt and converting the data to gridModel 
	 * 
	 * 
	 * Conversion type Integer -> Cell
	 * 0 : ENTRY
	 * 1 : EMPTY 
	 * 2 :LAND 
	 * 3 :EXIT
	 */
	
	private Integer height;
	private Integer width;
	private List<List<Long>> content;
	private Point entryPosition;
	private Point exitPosition;
	public JSONReadAndConvertingFromLevelFile(String level) {
		
		JSONParser parser = new JSONParser();
		
		try {
			
			entryPosition = null;
			exitPosition = null;
			String localPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
			String path = localPath.substring(0, localPath.lastIndexOf("target"));
			
			
			Object obj = parser.parse(new FileReader(
	                path + "src/main/resources/fr/utbm/vi51/level/"
	                + level));

	        JSONObject jsonObject = (JSONObject) obj;
	        
	        this.content = new ArrayList<>();
	        this.height = Integer.parseInt((String) jsonObject.get("Height"));
	        this.width = Integer.parseInt((String) jsonObject.get("Width"));
	        JSONArray JSONcontent = (JSONArray) jsonObject.get("Content");
	        
			Iterator<List<Long>> iterator = JSONcontent.iterator();
            while (iterator.hasNext()) {
                this.content.add(iterator.next() );
            }
                   
//	        System.out.println(height);
//	        System.out.println(width);
//	        System.out.println(content);       
	        
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	public List<List<Cell>> convertingContentForGame(){
		
		List<List<Cell>> contentForGame = new ArrayList<>();
		
		
		
		for(int i=0;i<this.height;i++){
			
			List<Cell> listCell = new ArrayList<Cell>();
			
			for(int j=0;j<this.width;j++){
//				System.out.println((this.content.get(i).get(j)).intValue());
				switch((this.content.get(i).get(j)).intValue()){
				
					case 0: listCell.add(new Cell(i, j, TypeObject.ENTRY));
							entryPosition = new Point(i,j);
						break;
						
					case 1: listCell.add(new Cell(i, j, TypeObject.EMPTY));
					
						break;
					
					case 2: listCell.add(new Cell(i, j, TypeObject.LAND));
						
						break;
						
					case 3: listCell.add(new Cell(i, j, TypeObject.EXIT));
							exitPosition = new Point(i,j);
						break;
				
					default:
				}
			}
			
			contentForGame.add(listCell);
		}
		return contentForGame;
	}
	
	public Integer getHeigth() {
		return height;
	}

	public Integer getWidth() {
		return width;
	}

	public List<List<Cell>> getContentForGame() {
		return convertingContentForGame();
	}


	public Point getEntryPosition() {
		return entryPosition;
	}


	public Point getExitPosition() {
		return exitPosition;
	}

	

}
