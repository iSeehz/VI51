import javax.swing.JFrame;

import fr.utbm.vi51.gui.FrameProject;
import fr.utbm.vi51.model.EnvironmentModel;

public class mainProject {

	public static void main(String[] args) {
		EnvironmentModel m = new EnvironmentModel();
				
		FrameProject frame = new FrameProject(m);
		
	}

}
