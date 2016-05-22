package fr.utbm.vi51.controller;

import java.util.UUID;

import fr.utbm.vi51.event.ChangeLevel;
import fr.utbm.vi51.event.StartSimulation;
import fr.utbm.vi51.event.StopSimulation;
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
		this.emitEvent(new ChangeLevel(level));
		System.out.println("change level : " + level); 
	}
	
	private void emitEvent(Event event) {
		event.setSource(this.address);
		this.space.emit(event, Scopes.addresses(this.addressReceiver));
	}

}