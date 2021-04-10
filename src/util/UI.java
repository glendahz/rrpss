package util;

public abstract class UI {
	/**
	 * Print the main options that the user can choose from in this UI.
	 */
	public abstract void displayOptions();
	
	/**
	 * Set the main controller for this UI.
	 * The controller will allow the UI to interact with the underlying data.
	 * @param ctrl
	 */
	public abstract void setController(Controller ctrl);
}
