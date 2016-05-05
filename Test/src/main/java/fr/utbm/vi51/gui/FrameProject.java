package fr.utbm.vi51.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import fr.utbm.vi51.model.EnvironmentModel;

public class FrameProject extends JFrame {
	
	/**
	 * Main Frame project
	 */
	private static final long serialVersionUID = -8346526694391516641L;
	
	private EnvironmentModel environment;
	
	private JSplitPane split;
	private MainPanel mainPanel;
	private OptionPanel optionPanel;
	public FrameProject(EnvironmentModel model) {
		
		//initialize object
		this.environment = model;
		mainPanel = new MainPanel(model.getGrid());
		optionPanel = new OptionPanel();
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainPanel, optionPanel);
		
		// set the Frame 
		this.setTitle("Lemming Game");
		this.setSize(1000,800);
		this.setLocation(10,200);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
		
		//set the OptionPanel
		split.setResizeWeight(1.0);
		
		//add scroll to avoid object unreachable in the frame
		this.add(split);
		addWindowListener(new WindowAdapter() {
		  	public void windowClosing(WindowEvent e) {
			   System.exit(0);
		  	}	
		} );
		
		//Listener for the controlPanel
		this.mainPanel.getControlPanel().getPlayButton().addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        System.out.println("Play");
		    }
		});
		
		this.mainPanel.getControlPanel().getPauseButton().addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        System.out.println("Pause");
		    }
		});
		
		this.mainPanel.getControlPanel().getStepBystepButton().addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        System.out.println("Step By Step");
		    }
		});
		
		
	}
	public EnvironmentModel getEnvironment() {
		return environment;
	}
}