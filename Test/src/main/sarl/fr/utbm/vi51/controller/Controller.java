package fr.utbm.vi51.controller;

import java.util.UUID;

import fr.utbm.vi51.event.AllLemmingDig;
import fr.utbm.vi51.event.AllLemmingHalfTurn;
import fr.utbm.vi51.event.ChangeLevel;
import fr.utbm.vi51.event.ChangeLevelAndRebuildModel;
import fr.utbm.vi51.event.KillOneLemming;
import fr.utbm.vi51.event.StartSimulation;
import fr.utbm.vi51.event.StepByStepSimulation;
import fr.utbm.vi51.event.StopSimulation;
import fr.utbm.vi51.event.TerminateSimulation;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.EventSpace;
import io.sarl.util.Scopes;

/**
 * 
 * Allows the GUI to send commands to the Environment
 *
 * 
 */
public class Controller {

	private final EventSpace space;
	private final UUID uuid;
	private final Address address;
	private final Address addressReceiver;
	
	public Controller(EventSpace space, Address adr) {
		this.space = space;
		this.uuid = UUID.randomUUID();
		this.address = new Address(space.getID(), this.uuid);
		this.addressReceiver = adr;
	}

	/**
	 * Starts a new simulation
	 * 
	 */
	public void newSimulation() {
		this.emitEvent(new StartSimulation());
//		System.out.println("lancement simulation"); 
	}
	
	public void stopSimulation() {
		this.emitEvent(new StopSimulation());
//		System.out.println("fin simulation"); 
	}
	
	public void changeLevel(String level) {
		this.emitEvent(new ChangeLevelAndRebuildModel(level));
		System.out.println("change level : " + level); 
	}
	
	public void preparationToQuit(){
		this.emitEvent(new TerminateSimulation());
	}
	
	public void stepByStep() {
		this.emitEvent(new StepByStepSimulation());
//		System.out.println("coup par coup");
	}
	
	
	private void emitEvent(Event event) {
		event.setSource(this.address);
		this.space.emit(event, Scopes.addresses(this.addressReceiver));
	}
	
	public void OrderAllLemmingDig() {
		this.emitEvent(new AllLemmingDig());
	}
	
	public void OrderAllLemmingHalfTurn() {
		this.emitEvent(new AllLemmingHalfTurn());

	}
	
	public void OrderKillRandomLemming() {
		this.emitEvent(new KillOneLemming());

	}

	
}