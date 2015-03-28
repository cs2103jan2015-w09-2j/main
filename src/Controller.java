
public class Controller {
			
	private static Controller controller = null;
	private Storage file;
	
	public Controller(){
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
		History history = History.getInstance();
		
		Cmd cmd = OneTagParser.toCmd(input);
		cmd.execute();
		
		if(cmd instanceof ModifiableCmd){
			history.add((ModifiableCmd)cmd);
		}
		
		return true;
	}

}