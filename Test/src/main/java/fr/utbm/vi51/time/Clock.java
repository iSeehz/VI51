package fr.utbm.vi51.time;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;

import javax.swing.JLabel;
import javax.swing.Timer;

public class Clock extends JLabel implements ActionListener {


private Timer timer;
private Time begin;
private boolean paused;


public Clock() {
timer = new Timer(1000, this);
begin = new Time(0, 0, 0);
this.setText(this.toString());
paused=true;	
timer.start();
}

/*lance le comptage*/
public void start() {
	paused=false;
}


/*met le comptage sur pause*/
public void pause(){
   paused=true;
}

/*remet le temps à 0 sans lancer le comptage*/
public void restart(){
begin.setSeconds(0);
this.setText(this.toString());
}


@Override
public void actionPerformed(ActionEvent Ae) {
	if (!paused){
			begin.setSeconds(begin.getSeconds()+1);
			this.setText(this.toString());
	}
}

public String toString(){
String curt = (begin.getHours()>9?""+begin.getHours():"0"+begin.getHours());
curt = curt + ":" + (begin.getMinutes()>9?""+begin.getMinutes():"0"+begin.getMinutes());
curt = curt + ":" + (begin.getSeconds()>9?""+begin.getSeconds():"0"+begin.getSeconds());
return curt;
}


}