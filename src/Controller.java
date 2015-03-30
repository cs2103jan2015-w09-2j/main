
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
	}
		
	public static Controller getInstance(){
		if(controller == null){
			controller = new Controller();
		}
		return controller;
	}
	
	public boolean executeCommand(String input) {
		
		try{
			OneTagParser oneTag = new OneTagParser(input);
			Cmd cmd = oneTag.toCmd();
			cmd.execute();
			
			if(cmd instanceof ModifiableCmd){
				history.add((ModifiableCmd)cmd);
			}
		}
		catch(IndexOutOfBoundsException ex){
			display.setMessage(MESSAGE_INVALID_INDEX);
			return false;
		}
		
		return true;
	}

}