package fr.utbm.vi51.qlearning;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

import fr.utbm.vi51.model.PossibleMove;
import fr.utbm.vi51.parser.JSONReadAndWriteQLearning;

public class MoveProb {
	private final float coeff = 0.1f;
	private Long ProbaMat[][];
	private Long EvalMat[][];
	private JSONReadAndWriteQLearning json;
	private PossibleMove [] convertingIntToPossibleMove = {PossibleMove.PARACHUTE,PossibleMove.MOVEFORWARD,PossibleMove.MOVEBACKWARD,
			PossibleMove.CLIMBFORWARD,PossibleMove.CLIMBBACKWARD,PossibleMove.DIG};
	
	public MoveProb() {
		this.json = new JSONReadAndWriteQLearning();
		this.ProbaMat = json.getProbabilities();
		this.EvalMat = json.getEvaluation();
		
	}

	public Long[] getProba(int state) {
		
		return ProbaMat[state];
	}

	public synchronized  void reevaluate(int prevstate, PossibleMove prevaction, int currstate) {
		long max_Val = 0;
		int action_Val;
		for (int i = 0; i < ProbaMat[currstate].length; i++) {
			max_Val = Math.max(ProbaMat[currstate][i], max_Val);
		}
		switch (prevaction) {
		case PARACHUTE:
			action_Val = 0;
			break;
		case MOVEFORWARD:
			action_Val = 1;
			break;
		case MOVEBACKWARD:
			action_Val = 2;
			break;
		case CLIMBFORWARD:
			action_Val = 3;
			break;
		case CLIMBBACKWARD:
			action_Val = 4;
			break;
		default:
			action_Val = 5;
		}

		ProbaMat[prevstate][action_Val] = (long) Math.round(EvalMat[prevstate][action_Val] + coeff * max_Val);
	}

	public void write() {
		json.writeFile();
	}

	public PossibleMove randomWeigthChoice(int state){
		
		double totalWeigth = 0;
		int randomIndex = -1;
		int count = 0;
		double random;
		
		Long [] possibleChoice = this.getProba(state);
		for(int i=0;i<possibleChoice.length;i++){
			totalWeigth += possibleChoice[i];
		}
		random = Math.random() * totalWeigth;

		while(randomIndex==-1){
			random -= Math.max(possibleChoice[count], 0);
			if(random<=0.0d){
				randomIndex = count;
			}
			count++;
		}
        return convertingIntToPossibleMove[randomIndex];

	}
}


