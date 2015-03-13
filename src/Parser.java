public class Parser {
	private static final int INPUT_SPLIT_SIZE = 2;
	private static final int INPUT_SPLIT_FIRST = 0;
	private static final int INPUT_SPLIT_SECOND = 1;

	public static Cmd toCmd(String input) {
		String[] inputArr = input.split(" ", INPUT_SPLIT_SIZE);
		COMMAND_TYPE command = getCommand(inputArr[INPUT_SPLIT_FIRST]);
		String message = getMessage(inputArr[INPUT_SPLIT_SECOND]);
		switch (command) {
		case DISPLAY:
			return new ViewCmd();
		case ADD:
			return new AddCmd(parseMsg(message));
		case EDIT:
			inputArr = message.split(" ",INPUT_SPLIT_SIZE);
			return new EditCmd(Integer.parseInt(inputArr[INPUT_SPLIT_FIRST]), parseMsg(inputArr[INPUT_SPLIT_SECOND]));
		case EXIT:
			System.exit(0);
		default:
			throw new Error("invalid");
		}
	}
		
	private static Task parseMsg(String userInput){
			String description;
		    String stringStartTime, stringEndTime;
		    int[] startTime = new int[5];
		    int[] endTime = new int[5];
		    
		    if(userInput.charAt(2) == '/')
		    {
		    	startTime = convertToInt(stringStartTime);
		    	return new Task(startTime[0], startTime[1],startTime[2], startTime[3], startTime[4], -1,-1,-1,-1,null);
		    }
		   // String test = "pick up boiboi from 23/01/2015 23:15 to 24/01/2015 13:14" ;
		    String[] content = userInput.split("from");
		    description = content[0].trim();
		    content = content[1].split("to");
		    stringStartTime = content[0].trim();
		    stringEndTime = content[1].trim();
		    startTime = convertToInt(stringStartTime);
		    endTime = convertToInt(stringEndTime);
		    return new Task(startTime[0], startTime[1],startTime[2], startTime[3], startTime[4], endTime[0], endTime[1],endTime[2], endTime[3], endTime[4], description);
		  }
	private static int[] convertToInt(String stringTime){
		    int[] intTime = new int[5];
		    String[] content = stringTime.split(" ");
		    String[] time = content[0].split("/");
		    for(int i = 0 ; i < time.length; i++)
		    {
		      intTime[i] = Integer.parseInt(time[i]);
		    }
		    time = content[1].split(":");
		    intTime[3] = Integer.parseInt(time[0]);
		    intTime[4] = Integer.parseInt(time[1]);
		    return intTime;
		} 
	private static COMMAND_TYPE getCommand(String input) {
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
	private static String getMessage(String input) {
		return input;
	}
}
