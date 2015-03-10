
public class Parser {
	
	private static final int INPUT_SPLIT_SIZE = 2;
	private static final int INPUT_SPLIT_FIRST = 0;
	private static final int INPUT_SPLIT_SECOND = 1;
	
	public static Cmd toCmd(String input){
		String[] inputArr = input.split(" ", INPUT_SPLIT_SIZE);
		COMMAND_TYPE command = getCommand(inputArr[INPUT_SPLIT_FIRST]);
		String message = getMessage(inputArr[INPUT_SPLIT_SECOND]);
		
		switch(command){
		case DISPLAY :
			return new ViewCmd();
		case ADD:
			return new AddCmd(new Task(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, message));
		case EDIT :
			return new EditCmd(1, new Task());
		case EXIT :
			System.exit(0);
		default :
			throw new Error("invalid");
		}
	}
	
	private static COMMAND_TYPE getCommand(String input){	
		if (input == null)
			throw new Error("command type string cannot be null!");

		if (input.equalsIgnoreCase("display")) {
			return COMMAND_TYPE.DISPLAY;
		} else if (input.equalsIgnoreCase("add")) {
			return COMMAND_TYPE.ADD;
		} else if (input.equalsIgnoreCase("exit")) {
		 	return COMMAND_TYPE.EXIT;
		} else {
			return COMMAND_TYPE.INVALID;
		}		
	}
	
	private static String getMessage(String input){	
		return input;	
	}
}
