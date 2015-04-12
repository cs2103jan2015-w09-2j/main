import java.io.IOException;
import java.util.logging.Level;

//@author A0111867A
public class Controller {
	
	protected static final String MESSAGE_INVALID_INDEX = "The number you enter is invalid";
	protected static final String MESSAGE_FILE_ACCESS_NOT_ALLOWED = "Error with oneTag.json file access";
	protected static final String NAME_CLASS_STORAGE = "storage";
	private static Controller controller = null;
	
	private Storage file;
	Data data = Data.getInstance();
	Display display = Display.getInstance();
	History history = History.getInstance();
	OneTagLogger logger = OneTagLogger.getInstance();
	
	private Controller(){
		try{
		file = new Storage();
		data.set(file.getData());
		}catch(IOException ex){
			logger.log(Level.WARNING, NAME_CLASS_STORAGE, MESSAGE_FILE_ACCESS_NOT_ALLOWED);
		}
		
		display.set(new HomeView());
	}
	
	/**
	 * Returns an instance of this class
	 */
	public static Controller getInstance(){
		if(controller == null){
			controller = new Controller();
		}
		return controller;
	}
	
	/**
	 * Parse the input and update file and display.
	 * 
	 * @param input raw string input by user
	 */
	public void executeCommand(String input) {
		
		try{
			OneTagParser oneTagParser = new OneTagParser(input);
			Cmd cmd = oneTagParser.toCmd();
			cmd.execute();
			
			if(!(cmd instanceof UndoCmd)){
				history.add(cmd);
			}
		}
		catch(IndexOutOfBoundsException ex){
			display.set(MESSAGE_INVALID_INDEX);
		}
	}

}