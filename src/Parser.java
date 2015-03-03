
public class Parser {
	
	private static final int INPUT_SPLIT_SIZE = 2;
	private static final int INPUT_SPLIT_FIRST = 0;
	private static final int INPUT_SPLIT_SECOND = 1;
	
	String text;
	
	public Parser(String input){
		text = input;
	}
	
	public String getCommand(){
		String[] splitString = splitFirstWord(text);
		String firstWord = splitString[INPUT_SPLIT_FIRST];
		
		return firstWord;		
	}
	
	public String getMessage(){
		String newWord = "";
		
		String[] splitString = splitFirstWord(text);
		
		if(splitString.length == INPUT_SPLIT_SIZE){
			newWord = splitString[INPUT_SPLIT_SECOND];
		}
		
		return newWord;	
	}
	
	public COMMAND_TYPE getCommandType() {
		String commandTypeString = getCommand();
		
		if (commandTypeString == null)
			throw new Error("command type string cannot be null!");

		if (commandTypeString.equalsIgnoreCase("display")) {
			return COMMAND_TYPE.DISPLAY;
		} else if (commandTypeString.equalsIgnoreCase("add")) {
			return COMMAND_TYPE.ADD;
		} else if (commandTypeString.equalsIgnoreCase("exit")) {
		 	return COMMAND_TYPE.EXIT;
		} else {
			return COMMAND_TYPE.INVALID;
		}
	}
	
	private static String[] splitFirstWord(String text){
		return text.split(" ", INPUT_SPLIT_SIZE);
	}
	
}
