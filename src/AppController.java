public class AppController {

	private boolean quit;
	private MainMenuUI mainMenu;
	private MainController mainCtrl;
	
	public AppController() {
		
		quit = false;
		mainMenu = new MainMenuUI();
		mainCtrl = new MainController();
		MainMenu.setMainController(mainCtrl);
		
		mainCtrl.setContext(this);
		
	}
	
	//start running the application
		public void run()
		{
			while(!quit)
			{
				mainMenu.displayMenu();
			}
		}
		
		//signal to stop the app
		protected void stop()
		{
			quit = true;
		}
	
}
