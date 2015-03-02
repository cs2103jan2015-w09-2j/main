
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
			
	private static Data myList;
	private static Storage file;
	

	public Controller(){
		file = new Storage();
		myList = new Data(file.getData());
	}
		
	public static String executeCommand(String input) {
		
        //Parser ps = new Parser(input);
        
		COMMAND_TYPE commandType = COMMAND_TYPE.ADD;
		
		switch(commandType){
		case DISPLAY :
			return display();
		case ADD:
			return add(new Task(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, input));
		case EDIT :
			return edit(0, 2);
		case EXIT :
			System.exit(0);
		default :
			throw new Error(MESSAGE_INVALID);
		}
	}

	private static String display() {
		if(myList.isEmpty()){
			return String.format(MESSAGE_EMPTY, file);
		}
		else{
			return myList.toString();
		}
	}
			
	private static String add(Task input){ 
        myList.add(input);
        writeToFile();
        
        return String.format(MESSAGE_ADD, file, input);
	}
	
	private static String edit(int index, int changes){
		if(myList.isEmpty()){
			return String.format(MESSAGE_EMPTY, file);
		}
		else{
			Task task = myList.get(index);
			task.setEndDate(changes);
			writeToFile();
			return myList.toString();
		}
		
	}
	
	private static void writeToFile() {
		//file.writeToFile(myList.getData());
	}
		
}