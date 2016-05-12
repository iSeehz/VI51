package fr.utbm.vi51.model;

import java.util.EventObject;
import java.util.Map;
import java.util.UUID;

import org.eclipse.xtext.xbase.lib.Pure;

public class EnvironmentEvent extends EventObject {

	private static final long serialVersionUID = -8054100646829546393L;

	private final int time;
	
	private final int width;
	
	private final int height;

	private final Map<Cell, LemmingBody> objects;

	public EnvironmentEvent(UUID source, int time, int width, int height, Map<Cell, LemmingBody> objects) {
		super(source);
		this.time = time;
		this.width = width;
		this.height = height;
		this.objects = objects;
	}
	
	@Pure
	public int getTime() {
		return this.time;
	}
	
	@Pure
	public int getWidth() {
		return this.width;
	}
	
	@Pure
	public int getHeight() {
		return this.height;
	}

	@Pure
	public Map<Cell, LemmingBody> getObjects() {
		return this.objects;
	}

}