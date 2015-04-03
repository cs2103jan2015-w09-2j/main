
public class Controller {
	
	protected static final String MESSAGE_INVALID_INDEX = "The number you enter is invalid";
	
	private static Controller controller = null;
	
	private Storage file;
	Data data = Data.getInstance();
	Display display = Display.getInstance();
	History history = History.getInstance();
	
	private Controller(){
		file = new Storage();
		data.set(file.getData());
		
		display.setView(new DateView());
		history.add(new ViewCmd("home"));
	}
		
	public static Controller getInstance(){
		if(controller == null){
			controller = new Controller();
		}
		return controller;
	}
	
	public boolean executeCommand(String input) {
		
		try{
			OneTagParser oneTagParser = new OneTagParser(input);
			Cmd cmd = oneTagParser.toCmd();
			cmd.execute();
			
			if(!(cmd instanceof UndoCmd)){
				history.add(cmd);
			}
		}
		catch(IndexOutOfBoundsException ex){
			display.setMessage(MESSAGE_INVALID_INDEX);
			return false;
		}
		
		return true;
	}

}