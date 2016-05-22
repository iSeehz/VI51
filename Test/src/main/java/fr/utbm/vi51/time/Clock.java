package fr.utbm.vi51.time;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.Timer;

public class Clock extends JLabel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2168576722264651288L;
	private Timer timer;
	
	public Clock() {
		super("" + Calendar.getInstance());
		timer = new Timer(1000, this);
		timer.start( );
	}
	
	public void actionPerformed(ActionEvent ae) {
	    setText(String.format("%tT", Calendar.getInstance()));
	  }

	public Timer getTimer() {
		return timer;
	}
	
	
}
