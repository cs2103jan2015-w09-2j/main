
public class Controller {
			
	private static Controller controller = null;
	private Storage file;
	
	private Controller(){
		file = new Storage();
		Data data = Data.getInstance();
		Display display = Display.getInstance();
		
		data.set(file.getData());	
				
		display.setView(new DateView());
	}
		
	public static Controller getInstance(){
		if(controller == null){
			controller = new Controller();
		}
		return controller;
	}
	
	public boolean executeCommand(String input) {
		Cmd cmd = OneTagParser.toCmd(input);
		return cmd.execute();
	}

}