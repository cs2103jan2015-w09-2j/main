import java.io.FileNotFoundException;
import java.io.IOException;

public class Controller {
	
	// message
	private static final String MESSAGE_ADD = "added to %1$s: “%2$s”";
	private static final String MESSAGE_DELETE = "deleted from %1$s: “%2$s”";
	private static final String MESSAGE_CLEAR = "all content deleted from %1$s";
	private static final String MESSAGE_EMPTY = "%1$s is empty";
	private static final String MESSAGE_EMPTY_SEARCH = "no text matches “%1$s”";
	private static final String MESSAGE_INVALID = "command is invalid";
	private static final String MESSAGE_NO_VALUE = "please indicate word to %1$s";
	private static final String MESSAGE_NO_FILE = "file not available";
			
	private Data myList;
	private Storage file;
	

	public Controller(){
		file = new Storage();
		myList = new Data(file.getData());
	}
		
	public String executeCommand(String input)
			throws IOException, FileNotFoundException, Error {
		
        Parser ps = new Parser(input);
        
		COMMAND_TYPE commandType = ps.getCommandType();
		
		switch(commandType){
		case DISPLAY :
			return display();
		case ADD:
			return add(ps.getMessage());
		case EDIT :
			return edit(ps.getMessage());
		case EXIT :
			System.exit(0);
		default :
			throw new Error(MESSAGE_INVALID);
		}
	}

	private String display() {
		if(myList.isEmpty()){
			return String.format(MESSAGE_EMPTY, file);
		}
		else{
			return myList.toString();
		}
	}
			
	private String add(Task input) {
        file.append(input); 
        myList.add(input);
        
        return String.format(MESSAGE_ADD, file, input);
	}
	
	private String edit(String input){
		if(myList.isEmpty()){
			return String.format(MESSAGE_EMPTY, file);
		}
		else{
			Task task = myList.get("input 1");
			task.setSomething("input 2", "input 3", "input 4");
			return myList.toString();
		}
		
	}
		
}