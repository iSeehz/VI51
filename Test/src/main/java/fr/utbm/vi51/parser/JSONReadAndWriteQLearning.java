package fr.utbm.vi51.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONReadAndWriteQLearning {
	
	private Long[][] probabilities;
	private Long[][] evaluation;
	private String pathProba;
	public JSONReadAndWriteQLearning() {
	
		
		pathProba = System.getProperty("user.dir") + "/src/main/resources/fr/utbm/vi51/qlearning/Proba.txt";
		String pathEval = System.getProperty("user.dir") + "/src/main/resources/fr/utbm/vi51/qlearning/Evaluation.txt";
			
		this.probabilities = readFile(pathProba, this.probabilities);
		this.evaluation =  readFile(pathEval, this.evaluation);
		
	}
	
	public Long[][] readFile(String path,  Long[][] tab){
		
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(new FileReader(
			        path));
			JSONObject jsonObject = (JSONObject) obj;
	        int height = Integer.parseInt((String) jsonObject.get("Height"));
	        int width = Integer.parseInt((String) jsonObject.get("Width"));
	        List<List<Long>> list = new ArrayList<>();
	        JSONArray JSONcontent = (JSONArray) jsonObject.get("Content");
	        
			Iterator<List<Long>> iterator = JSONcontent.iterator();
	        while (iterator.hasNext()) {
	        	list.add(iterator.next() );
	        }
	        
	        tab = new Long[list.size()][list.get(0).size()];
	        
	        for (int i = 0 ;i<list.size();i++){
	        	
	        	for(int j = 0 ; j<list.get(0).size();j++){
	     
	        		tab[i][j] = list.get(i).get(j);
	        	}
	        }
	               
	      return tab;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tab;
	}

	public Long[][] getProbabilities() {
		return probabilities;
	}

	public Long[][] getEvaluation() {
		return evaluation;
	}
	
	public void writeFile(){//in Proba.txt
		
		
	}
	
	
}
