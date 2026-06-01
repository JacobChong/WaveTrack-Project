package edu.sdmesa.cisc191;

/**
 * Lead Author(s):
 * @author Jacob Chong
 *
 * Other contributors:
 * None
 *
 * References:
 * Morelli, R., & Walde, R. (2016). Java, Java, Java: Object-Oriented Problem Solving.
 * Retrieved from https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 *
 * Version/date: Week 8
 *
 * Responsibilities of class:
 * WaveTrackGUIRunner starts the full WaveTrack program.
 * It creates the model, view, file handler, and controller.
 */
public class WaveTrackGUIRunner
{
	/**
	 * Starts the GUI program.
	 * This creates the MVC parts and connects them together.
	 *
	 * @param args not used
	 */
	public static void main(String[] args)
	{
		// WaveTrackGUIRunner has-a InventoryManager object.
		// This is the model part of MVC.
		InventoryManager manager = new InventoryManager();

		// WaveTrackGUIRunner has-a WaveTrackView object.
		// This is the view part of MVC.
		WaveTrackView view = new WaveTrackView();

		// WaveTrackGUIRunner has-a InventoryFileHandler object.
		// This supports text file input and output.
		InventoryFileHandler fileHandler = new InventoryFileHandler();

		// WaveTrackGUIRunner creates a controller that has-a model, view, and file handler.
		// The controller connects user button clicks to the program logic.
		new WaveTrackController(manager, view, fileHandler);
	}
}