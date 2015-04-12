
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.Parser;
import com.joestelmach.natty.DateGroup;

//@author A0108436H
public class OneTagParser {
	private static final String BACKSLASH = "/";
	//private static final String INVALID_EDIT_COMMAND = "INVALID ERROR COMMAND!";
	//	private static final int DUMMY_VALUE = -1;
	private static final int NUM_ZERO = 0;
	private static final int POS_ZERO = 0;
	//	private static final int SIZE_ONE = 1;
	private static final int NUM_ONE = 1;
	private static final int POS_ONE = 1;
	private static final int POS_TWO = 2;
	//	private static final int NUM_TWO = 2;
	private static final int POS_THREE = 3;
	private static final int SIZE_THREE = 3;
	private static final int POS_FOUR = 4; 
	private static final int SIZE_DATE_TIME = 5;
	private static final int POS_FIVE = 5;

	
	//TO SPLIT A STRING
	private static final int INPUT_SPLIT_THIRD = 2;
	private static final int INPUT_SPLIT_FIRST = 0;
	private static final int INPUT_SPLIT_SECOND = 1;	
	//private static final int INPUT_SPLIT_FOURTH = 3;	

	//String used in parser
	private static final String COLON = ":";
	private static final String SPACE = " ";

	//Error Messages: WILL BE HANDLED BY CONTROLLER.
	private static final String INVALID_MSG = "invalid";
	private static final String INVALID_CMD_MSG = "command type string cannot be null!";
	private static final String ERROR_EDIT = "Edit cannot be a null";
	private static final String ERROR_MONTH = "Month cannot be a null value";
	private static final String ERROR_ADD_CMD = "ERROR DETECTED IN PARSING FOR ADDING TASK";

	//Commands
	private static final String HOME= "home";
	private static final String DONE = "done";
	private static final String TODAY = "today";
	private static final String SOMEDAY = "someday";
	private static final String UPCOMING = "upcoming";
	private static final String ADD = "add";
	private static final String EXIT = "exit";
	private static final String SEARCH = "search";
	private static final String SAVE = "save";
	private static final String EDIT = "edit";
	private static final String UNDO = "undo";
	private static final String HELP= "help";
	private static final String DELETE = "delete";

	//Msg Indicating Presence of Time
	private static final String FROM = "from";
	private static final String TO = "to";
	private static final String BY = "by";
	private String input;
	
	public OneTagParser(String input) {
		this.input = input.trim();
	}
	/**Takes input String from Logic and sends it for sorting.
	 * 
	 * @param input String given by Logic
	 * @return Cmd after parsing
	 */
	public Cmd toCmd() {
		String[] inputArr = input.split(SPACE);
		if(inputArr.length == NUM_ONE) {
			return parseOneWordCmd();
		}else {
			return parseLongInput();
		}
	}
	/**Determines whether cmd is 'View' ,'Help', 'Undo', 'Exit' and returns appropriate Cmd object.
	 * 
	 * @param input
	 * @return Cmd 
	 */
	private Cmd parseOneWordCmd() {
		COMMAND_TYPE command = getCommand(input);
		//This can be re-factored further.
		switch (command) {
		case UNDO:
			return new UndoCmd();
		case EXIT:
			System.exit(0);
		default:
			throw new IllegalArgumentException();
		}
	}
	/**Separates the command from the message and sends the information for parsing.
	 * 
	 * @param input
	 * @return
	 */
	private Cmd parseLongInput() {
		String[] inputArr = input.split(SPACE,INPUT_SPLIT_THIRD);	
		String userCommand = inputArr[INPUT_SPLIT_FIRST];
		COMMAND_TYPE command = getCommand(userCommand);
		String message = inputArr[INPUT_SPLIT_SECOND].trim();
		switch(command) {
		case ADD:
			return new AddCmd(parseMsgforAddCmd(message));
		case EDIT:
			return parseEditDate(message);
		case DELETE: 	
			return new DeleteCmd(parseNum(message));
		case DONE : 
			return new CompletedCmd(parseNum(message));
		case SEARCH :
			return new SearchCmd(message);
		case SAVE : 
			return new SaveCmd(message);
		case HOME: 
		case DONE:
		case TODAY:
		case UPCOMING:
		case SOMEDAY:
		case HELP:
			return new ViewCmd(command,numForView(message));
		default:
			throw new Error(INVALID_MSG);
		}		
	}
	/** This task takes in string message and returns for parsing.
	 * 
	 * @param message
	 * @return Task object to Logic.
	 */	
	private Task parseMsgforAddCmd(String message) {
		System.out.println("Message into parseMsgforAddCMd");
		String[] word = message.split(SPACE);
		String testWord = "" , dateString = null,taskDescription = null;	
		LocalDateTime endDateTime = null, startDateTime = null , deadlineDateTime = null; 
		boolean isTimedTask = false, isDeadlineTask = false;
		int posKeyword = word.length - NUM_ONE;	
		
		for(int count = posKeyword ; count >= NUM_ZERO; count--) {
			testWord =word[count].trim();
			if(isTimedTask(testWord)){ 
				dateString = word[count+1] + SPACE;
				if(posKeyword == word.length- NUM_ONE) {
					dateString = getDateTimeString(dateString, posKeyword, word, count).trim();
					if(isValidDateTime(dateString)) {
						isTimedTask = true;
						endDateTime = parseDate(dateString);
						posKeyword = count;
					}
				}
				else {
					dateString = getNewDateTimeString(dateString, posKeyword, word,count);
					startDateTime = parseDate(dateString);
					posKeyword = count;
					taskDescription = getTaskDescription(posKeyword, word);
					return new Task(startDateTime, endDateTime, taskDescription);
				}
			}
			else if(isDeadlineTask(testWord)) {
				posKeyword = count;
				dateString = getDateString(word, message, count);
				if(isValidDateTime(dateString)){
					isDeadlineTask = true;
					deadlineDateTime = parseDate(dateString);
					taskDescription = getTaskDescription(posKeyword,word);
					return new Task(deadlineDateTime,taskDescription);
				}
			}
		}
		
		
		if(isFloatingTask(isDeadlineTask, isTimedTask)) {
			taskDescription=getTaskDescription(word.length,word);
			return new Task(taskDescription);
		}
		throw new Error(ERROR_ADD_CMD);
	}
	

	private boolean isValidDateTime(String dateString) {
		LocalDateTime testDate;
		String[] words = dateString.split(SPACE);
		int count = words.length; 
		for(String element: words) {
			testDate = parseDate(element.trim());
			if(testDate != null) {
				count--;
				continue;
			}else if(testDate == null) {
				if(checkIfInteger(element)) {
					count--; 
					continue;
				}else{
				return false;
				}
			}
		}
		return true; 
	}

	private boolean checkIfInteger(String element) {
		 try { 
		        Integer.parseInt(element); 
		    } catch(NumberFormatException e) { 
		        return false; 
		    } catch(NullPointerException e) {
		        return false;
		    }
		    // only got here if we didn't return false
		    return true;
	}
	/**Parses the dateString and return an array with date & time information.
	 * 
	 * @param dateString
	 * @param dateParser
	 * @return infoDateTime
	 */
	private LocalDateTime parseDate(String dateString) {
		Instant instant = null;
		Parser dateParser = new Parser();
		List<DateGroup> listOfDates = dateParser.parse(dateString);
		for(DateGroup group:listOfDates) {	
			List<Date> dates = group.getDates();
			Date thisDate = dates.get(POS_ZERO);
			instant = Instant.ofEpochMilli(thisDate.getTime());
			LocalDateTime test = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
			break;
		}
		if(instant == null) {
			return null;
		}else {
		LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	    return dateTime;	
	}
	}
	/**Returns the dateString containing the date to be sent for parsing.
	 * 
	 * @param word
	 * @param message
	 * @param count
	 * @return dateString
	 */
	private String getDateString(String[] word, String message, int count) {
		String dateString;
		dateString = word[count+1] + SPACE;
		dateString = getParseDate(dateString, word, message, count);
		return dateString;
	}
	/**Returns Task object with dates edited.
	 * @param message
	 * @return	
	 */
	private EditCmd parseEditDate(String message) {
		message = message.trim();
		LocalDateTime dateTime;
		String[] inputArr = message.split(SPACE,INPUT_SPLIT_THIRD);
		int index = Integer.parseInt(inputArr[0].trim());
		String userChanges = inputArr[1].trim();
		if(userChanges.contains(BY)) {
			mainloop: 
			if(userChanges.lastIndexOf(BY) == 0) {//by something.
				if(userChanges.contains("to")||userChanges.contains("from")){
					/*edit 1 by J.K. Rowling from <time> 
					 * edit 1 by J.K. Rowling to <time>
					 *edit 1 by J.K. Rowling from <date/time> to <date/time>
					 */
					break mainloop;
				}
				/*Sample Cases: edit <index> by <string>
				 * 1.edit 1 by April 25 2015 (user changes date)
				 * 2.edit 1 by J.K.Rowling (user changes task name to "by J.K.Rowling")
				 * 3.edit 1 by  J.K.Rowling someday (2 + change task to someday)
				 * 4.edit 1 by J.K Rowling by April 25 2015.
				 */ 
				String dateTimeString = userChanges.substring(2).trim();
				if(isValidDateTime(dateTimeString)) {
					//Case 1
					dateTime = parseDate(dateTimeString);
					return new EditCmd(index, dateTime, 3);
				}else{//by task description.
					String taskDescription = userChanges;
					if(taskDescription.endsWith("someday")) {
						taskDescription = taskDescription.substring(0, taskDescription.lastIndexOf("someday"));
						taskDescription.trim();
						return new EditCmd(index, taskDescription,true);
					}
					//Case 2
					return new EditCmd(index,taskDescription);
				}
			}else{
				/*Sample Case: (edit <index> <string> by <string>)
				 * 1. edit 1 pick up Johnson by tomorrow
				 * 2. edit 1 read CSLR by Thomas Cormen
				 * 3. edit 1 read CSLR by Thomas Cormen someday 
				 */
				int posLastBy = userChanges.lastIndexOf(BY);
				String taskDescription = userChanges.substring(0,posLastBy).trim();
				String dateTimeString = userChanges.substring(posLastBy+2).trim();
		/*		String[] splitBy = userChanges.split(BY);
				String taskDescription = splitBy[0].trim();
				String dateTimeString = splitBy[1].trim();*/
				
				/*Code: 
				 * edit 1 read CLRS by Thomas Cormen from today to tomorrow.
				 */
				if(isValidDateTime(dateTimeString)) {
					//Case 1 
					dateTime = parseDate(dateTimeString);
					return new EditCmd(index, taskDescription,dateTime,2);	
				}else{
					if(userChanges.endsWith("someday")) {
						//Case 3
						taskDescription = userChanges.substring(0, userChanges.lastIndexOf("someday"));
						taskDescription.trim();
						return new EditCmd(index, taskDescription,true);
					}else{
						//Case 2.
						taskDescription = userChanges;
						return new EditCmd(index,taskDescription);
					}
				}	
			}
		}else if(userChanges.contains("from")) {
			int posLastFrom = userChanges.lastIndexOf("from");
			if(posLastFrom == 0){
				String from = userChanges.substring(0,4);
				String dateTimeString = userChanges.substring(4).trim();
				if(dateTimeString.contains("to")) {
					int posLastTo = dateTimeString.lastIndexOf("to");
					String stringFrom = dateTimeString.substring(0,posLastTo-1).trim();
					String stringTo = dateTimeString.substring(posLastTo + 2).trim(); 
					if(isValidDateTime(stringFrom) && isValidDateTime(stringTo)){
						LocalDateTime startDateTime = parseDate(stringFrom);
						LocalDateTime endDateTime = parseDate(stringTo);
						return new EditCmd(index,startDateTime,endDateTime);
					}else if(!isValidDateTime(stringFrom) && !isValidDateTime(stringTo)) {
						String taskDescription = userChanges; 
						if(taskDescription.endsWith("someday")) {
							taskDescription = taskDescription.substring(0, taskDescription.lastIndexOf("someday"));
							taskDescription.trim();
							return new EditCmd(index, taskDescription,true);
						}
						return new EditCmd(index, userChanges);
					}else if(!isValidDateTime(stringFrom) && isValidDateTime(stringTo)) {
						String taskDescription = from + stringFrom; 
						LocalDateTime endDateTime = parseDate(stringTo);
						return new EditCmd(index,taskDescription,endDateTime,2);
					}//Do not worry abt it being a to time. Let it pass to the to.
				}else{
					if(isValidDateTime(dateTimeString)) {
						LocalDateTime startDateTime = parseDate(dateTimeString);
						return new EditCmd(index,startDateTime,1);
					}else{
						String taskDescription = userChanges; 
						if(taskDescription.endsWith("someday")) {
							taskDescription = taskDescription.substring(0, taskDescription.lastIndexOf("someday"));
							taskDescription.trim();
							return new EditCmd(index, taskDescription,true);
						}
						return new EditCmd(index, userChanges);
					}
				}
			}else {
					String taskDescription = userChanges.substring(0,userChanges.lastIndexOf("from")).trim();
					String dateTimeString = userChanges.substring(userChanges.lastIndexOf("from")+4).trim();
					if(dateTimeString.contains("to")) {
						int lastPosTo = dateTimeString.lastIndexOf("to");
						String startDateTimeString = dateTimeString.substring(0,lastPosTo-1).trim();
						String endDateTimeString = dateTimeString.substring(lastPosTo + 2).trim();
						if(isValidDateTime(startDateTimeString) && isValidDateTime(endDateTimeString)) {
							LocalDateTime startDateTime = parseDate(startDateTimeString);
							LocalDateTime endDateTime = parseDate(endDateTimeString);
							return new EditCmd(index,taskDescription,startDateTime,endDateTime);
						}else if(!isValidDateTime(startDateTimeString) && isValidDateTime(endDateTimeString)) {
							LocalDateTime endDateTime = parseDate(dateTimeString);
							taskDescription = userChanges.substring(0,userChanges.lastIndexOf("to")).trim();
							return new EditCmd(index, taskDescription,endDateTime,2);
						}
					}
					if(isValidDateTime(dateTimeString)) {
						LocalDateTime startDateTime = parseDate(dateTimeString);
						return new  EditCmd(index,taskDescription,startDateTime,1);
					}else{
						taskDescription = userChanges; 
						if(taskDescription.endsWith("someday")) {
							taskDescription = taskDescription.substring(0, taskDescription.lastIndexOf("someday"));
							taskDescription.trim();
							return new EditCmd(index, taskDescription,true);
						}
						return new EditCmd(index,userChanges);
					}
				}
			}else if(userChanges.contains("to")) {
				int posLastTo = userChanges.lastIndexOf("to");
				if(posLastTo == 0) {
					String to = userChanges.substring(0,1);
					String dateTimeString = userChanges.substring(2).trim();
					if(isValidDateTime(dateTimeString)) {
						LocalDateTime endDateTime = parseDate(dateTimeString);
						return new EditCmd(index,endDateTime,2);
					}else {
						String taskDescription = userChanges; 
						if(taskDescription.endsWith("someday")) {
							taskDescription = taskDescription.substring(0, taskDescription.lastIndexOf("someday"));
							taskDescription.trim();
							return new EditCmd(index, taskDescription,true);
						}
						return new EditCmd(index,taskDescription);
					}
				}else {
					String taskDescription = userChanges.substring(0,posLastTo).trim();
					String dateTimeString = userChanges.substring(posLastTo + 2).trim();
					if(isValidDateTime(dateTimeString)){
						LocalDateTime endDateTime = parseDate(dateTimeString);
						return new EditCmd(index,taskDescription,endDateTime,2);
					}else{
						taskDescription = userChanges;
						if(taskDescription.endsWith("someday")){
							taskDescription = taskDescription.substring(0, taskDescription.lastIndexOf("someday"));
							taskDescription.trim();
							return new EditCmd(index, taskDescription,true);
						}
						return new EditCmd(index,taskDescription);
					}
				}
			}
		String taskDescription = userChanges.trim();
		if(taskDescription.endsWith("someday")) {
			taskDescription = taskDescription.substring(0, taskDescription.lastIndexOf("someday"));
			taskDescription.trim();
			return new EditCmd(index, taskDescription,true);
		}
		return new EditCmd(index,taskDescription);
		
	}
	
	/**This method returns the getParseDate
	 * 
	 * @param parseDate
	 * @param word
	 * @param message
	 * @param count
	 * @return
	 */
	private String getParseDate(String parseDate, String[] word,String message, int count) {
		for(int i = count + 2 ; i < getNumWords(message); i++) {
			parseDate += word[i] + SPACE;
		}
		return parseDate;
	}

	/**Returns description of Task.
	 * @param posKeyword
	 * @param word
	 * @return taskDescription
	 * 
	 */
	private String getTaskDescription(int posKeyword, String[] word) {
		String taskDescription;
		taskDescription = word[POS_ZERO] + SPACE ;
		for(int i = POS_ONE; i < posKeyword ; i++) {
			taskDescription += word[i] + SPACE;			
		}
		return taskDescription;
	}

	/**Converts the Date into a String.
	 * @param groups
	 * @return testDate
	 */	
	private String getDateTimeinString(List<DateGroup> groups) {
		String testDate = null;
		Date parseResult = null;
		for(DateGroup group:groups) {
			List<Date> dates = group.getDates();
			parseResult = dates.get(POS_ZERO);
			testDate = dates.get(POS_ZERO).toString();
			break;
		}
		return testDate;
	}

	/**This method returns the date and time string which can be parsed by the parser.
	 * 
	 * @param parseDate
	 * @param posKeyword
	 * @param word
	 * @param count
	 * @return parseDate
	 */
	private String getDateTimeString(String parseDate, int posKeyword,String[] word, int count) {
		for(int i = count + 2; i <= posKeyword; i++) {
			parseDate += (word[i] + " ");	
		}
		return parseDate;
	}

	/**Returns the string of date and time for the "from DATE&TIME" component as "to DATE&TIME" has been parsed.
	 * @param parseDate
	 * @param posKeyword
	 * @param word
	 * @param count
	 * @return parseDate.
	 */	
	private String getNewDateTimeString(String parseDate, int posKeyword,
			String[] word, int count) {
		for(int i = count + 2; i < posKeyword; i++)	{
			parseDate += (word[i] + SPACE);
		}
		return parseDate;
	}	
	/**This method converts the integer of String type to integer of Int type
	 * 
	 * @param message (type String)
	 * @return integer value of the string
	 */
	private int parseNum(String message) {
		if(isValidDateTime(message)) {
			return Integer.parseInt(message);
		}else{
			return -1; 
		}
	}
	/**This method converts the integer of String type to integer of Int type for ViewCmd
	 * 
	 * @param message (type String)
	 * @return integer value of the string
	 */
	
	
	private int numForView(String element) {
		int num;
		 try { 
		        num = Integer.parseInt(element); 
		        if(num == 0){
		        	return 1;
		        }
		        else{
		        	return num;
		        }
		    } catch(NumberFormatException e) { 
		        return -1;
		    } catch(NullPointerException e) {
		        return -1;
		    }
	}
	/**Returns the number of words that the user has typed it.
	 * 
	 * @param input
	 * @return number of words that the user has entered into the command prompt.
	 */
	private int getNumWords(String  input) {	
		String inputArr[]=input.split(SPACE);
		return inputArr.length;
	}

	/**This method return true if the word tested is either a "from" or "to".
	 * 
	 * @param testWord
	 * @return
	 */
	private static boolean isTimedTask(String testWord) {
		return testWord.equalsIgnoreCase(FROM)|| testWord.equalsIgnoreCase(TO);
	}
	/**Returns true if testWord is "BY","AT","ON","IN" for deadlined task.
	 * 
	 * @param testWord
	 */
	private boolean isDeadlineTask(String testWord) {
		return (testWord.equalsIgnoreCase(BY));
	}
	/**Returns true if testWord is "BY","AT","ON","IN" for deadlined task.
	 * 
	 * @param testWord
	 */
	private boolean isTaskTime(String testWord) {
		return (testWord.equalsIgnoreCase(BY)|| testWord.equalsIgnoreCase(FROM)|| testWord.equalsIgnoreCase(TO));
	}
	/**This method determines if the task is floating or not
	 * 
	 * @param isDeadlineTask
	 * @param isTimedTask
	 * @return true if floating task and false if otherwise.
	 */
	private boolean isFloatingTask(boolean isDeadlineTask, boolean isTimedTask) {
		return isDeadlineTask == false && isTimedTask == false;
	}	
/**
	/**This method converts the user comand into COMMAND_TYPE
	 * 
	 * @param input
	 * @return COMMAND_TYPE
	 */
	private static COMMAND_TYPE getCommand(String input) {
		assert(input == null);
		if (input.equalsIgnoreCase(HOME)) {
			return COMMAND_TYPE.HOME;
		}else if (input.equalsIgnoreCase(DONE)) {
			return COMMAND_TYPE.DONE;
		}else if(input.equalsIgnoreCase(TODAY)) {
			return COMMAND_TYPE.TODAY;
		}else if(input.equalsIgnoreCase(SOMEDAY)) {
			return COMMAND_TYPE.SOMEDAY;
		}else if(input.equalsIgnoreCase(UPCOMING)) {
			return COMMAND_TYPE.UPCOMING;
		}else if (input.equalsIgnoreCase(ADD)) {
			return COMMAND_TYPE.ADD;
		} else if (input.equalsIgnoreCase(EXIT)) {
			return COMMAND_TYPE.EXIT;
		} else if (input.equalsIgnoreCase(SEARCH)) {
			return COMMAND_TYPE.SEARCH;
		} else if (input.equalsIgnoreCase(SAVE)) {
			return COMMAND_TYPE.SAVE;
		} else if (input.equalsIgnoreCase(EDIT)) {
			return COMMAND_TYPE.EDIT;
		} else if (input.equalsIgnoreCase(UNDO)) {
			return COMMAND_TYPE.UNDO;
		} else if (input.equalsIgnoreCase(HELP)) {
			return COMMAND_TYPE.HELP;
		} else if (input.equalsIgnoreCase(DELETE)) {
			return COMMAND_TYPE.DELETE;
		} else if (input.equalsIgnoreCase(UPCOMING)) {
			return COMMAND_TYPE.UPCOMING;
		} else {
			return COMMAND_TYPE.INVALID;
		}
	}	
	@SuppressWarnings("unused")
	private static String getMessage(String input) {
		return input;
	}
}

/*
/** This method converts the MONTH_TYPE to the position they occupy in the year (Jan == 1, Dec == 12)
 * 
 * @param month
 * @return Number of month
 *
private static int convertStringMonthToIntMonth(MONTH_TYPE month) {
	switch(month){
	case JAN:
		return MONTH_ONE; 	
	case FEB:
		return MONTH_TWO;
	case MAR:
		return MONTH_THREE;
	case APR:
		return MONTH_FOUR;
	case MAY:
		return MONTH_FIVE;
	case JUN:
		return MONTH_SIX;
	case JUL:
		return MONTH_SEVEN;
	case AUG:
		return MONTH_EIGHT;
	case SEP:
		return MONTH_NINE; 	
	case OCT:
		return MONTH_TEN;
	case NOV:
		return MONTH_ELEVEN;
	case DEC:
		return MONTH_TWELVE;
	default:
		return MONTH_DEFAULT; 
	}
}*/







/*		String taskDescription = userChanges.substring(0,posLastFrom).trim();
String dateTimeString = userChanges.substring(posLastFrom + 1).trim();
if(dateTimeString.contains("to")){
  int posLastTo = userChanges.lastIndexOf("to");
  String stringFrom = dateTimeString.substring(0,posLastTo).trim();
  String stringTo = dateTimeString.substring(posLastTo+1).trim();
  if(isValidDateTime(stringFrom) && isValidDateTime(stringTo)){
	  LocalDateTime startTime = parseDateTime(stringFrom);
	  LocalDateTime endTime = parseDateTime(stringTo);
	  return new _________
			  
			  
			  
			  
  }
  
  '
}else if(isValidDateTime(dateTimeString)){
	dateTime = parseDate(dateTimeString);
	return new EditCmd(index,parseDate,2);
}

/*	return null;
}
return null;
}
*/






/*
if(userChanges.contains(BY)||userChanges.contains(TO)||userChanges.contains(FROM)){//Means contains a keywords.
if(userChanges.contains(BY) && !userChanges.contains(FROM) && !userChanges.contains(TO)){
	//PARSE DEADLINE TASK 
}else if(userChanges.contains(BY) && userChanges.contains(FROM) && !userChanges.contains(TO))''
		
		
		)





}




return null;
}

/*
}
String endDateTimeString, startDateTimeString; 
String deadlineDateTimeString;
boolean isToTime = false;
boolean isFromTime = false;
boolean isTaskDescription = false;
String toDo = inputArr[1];
System.out.println("toDo : "+toDo);

String testWord,dateString;
String taskDescription = null;
int posKeyword = toDo.length() - NUM_ONE;
LocalDateTime endDateTime = null;
LocalDateTime startDateTime = null, deadlineDateTime = null;
Parser dateParser = new Parser();
if(toDo.contains(FROM)|| toDo.contains(TO) || toDo.contains(BY)){
System.out.println("Keyword Detected");
String[] word = toDo.split(SPACE);
for(int count =toDo.length - 1 ; count >= NUM_ZERO; count--){
	testWord =word[count].trim();
	if(isDeadlineTask(testWord)){
		posKeyword = count;
		dateString = getDateString(word, toDo, count);
		deadlineDateTimeString = parseDate(dateString,dateParser);
		deadlineDateTime = getDateTimeFormat(deadlineDateTimeString);
		taskDescription = getTaskDescription(posKeyword,word);
		return new EditCmd(index,deadlineDateTime,2);
	}
}*/

//	taskDescription = inputArr[1];
//	return new EditCmd(index,taskDescription) ;
	/*if(checkTimedTask(testWord)){
		System.out.println("This is a check timed task");
		dateString = word[count+1] + SPACE ;
		if(posKeyword == toDo.length()- NUM_ONE){
			System.out.println("To timing is working");
			dateString = getDateTimeString(dateString, posKeyword, word,count);
			endDateTimeString = parseDate(dateString, dateParser);
			endDateTime = getDateTimeFormat(endDateTimeString);
			isToTime = true;
			posKeyword = count;
		}else{
			dateString = getNewDateTimeString(dateString, posKeyword, word,count);
			startDateTimeString = parseDate(dateString,dateParser);
			startDateTime = getDateTimeFormat(startDateTimeString); 
			isFromTime = true;
			posKeyword = count;
		    taskDescription = getTaskDescription(posKeyword, word);
		    isTaskDescription = true;
		}
}*//*else if(isDeadlineTask(testWord)){
posKeyword = count;
dateString = getDateString(word, message, count);
deadlineDateTimeString = parseDate(dateString,dateParser);
deadlineDateTime = getDateTimeFormat(deadlineDateTimeString);
taskDescription = getTaskDescription(posKeyword,word);
return new EditCmd(index,deadlineDateTime,2);
}*
/*/	
/*if(isToTime == true && isFromTime == false && isTaskDescription == false){
return new EditCmd(index,endDateTime,2);
}else if (isToTime == false && isFromTime == true && isTaskDescription == false){
return new EditCmd(index,startDateTime,1);
}else if(isToTime == true && isFromTime == false && isTaskDescription == true){
return new EditCmd(index,taskDescription,endDateTime,2);
}else if(isToTime == false && isFromTime == true && isTaskDescription == true){
return new EditCmd(index,taskDescription,endDateTime,2);
}else if(isToTime == true && isFromTime == true && isTaskDescription == true){
System.out.println("Test");
return new EditCmd(index,taskDescription, startDateTime, endDateTime);
}else if(isToTime == false && isFromTime == false && isTaskDescription == true){
return new EditCmd(index, taskDescription);
}
}*/


//System.out.println("Testing edit Cmd");

/*
	private LocalDateTime getDateTimeFormat(String endDateTimeString) {
		int[] test = getDateAndTime(endDateTimeString);
		LocalDateTime dateTime = LocalDateTime.of(test[0],test[1],test[2],test[3],test[4]);
		return dateTime;
	}
*/
/*
/**This method converts the editString into an EDIT_TYPE.
 * 
 * @param testWord
 * @return EDIT_TYPE
 *
private static EDIT_TYPE getEditCmd(String editString){
	if(editString == null){
		throw new Error(ERROR_EDIT);
	}
	if(editString.equalsIgnoreCase(DEADLINE)){
		return EDIT_TYPE.DEADLINE;
	}else if(editString.equalsIgnoreCase(ENDTIME)){
		return EDIT_TYPE.ENDTIME;
	}else if(editString.equalsIgnoreCase(ENDDATE)){
		return EDIT_TYPE.ENDDATE;
	}else if(editString.equalsIgnoreCase(STARTDATE)){
		return EDIT_TYPE.STARTDATE;
	}else if(editString.equalsIgnoreCase(STARTTIME)){
		return EDIT_TYPE.STARTTIME;
	}else{
		return EDIT_TYPE.INVALID;
	}
}


/**This method changes the month value from a String to a MONTH_TYPE value
 * 
 * @param month
 * @return MONTH_TYPE
 * 
private static MONTH_TYPE getMonth(String month){
	if(month == null){
		throw new Error(ERROR_MONTH);
	}
	if(month.equalsIgnoreCase(JAN)){
		return MONTH_TYPE.JAN;
	}else if(month.equalsIgnoreCase(FEB)){
		return MONTH_TYPE.FEB;
	}else if(month.equalsIgnoreCase(MAR)){
		return MONTH_TYPE.MAR;
	}else if(month.equalsIgnoreCase(APR)){
		return MONTH_TYPE.APR;
	}else if(month.equalsIgnoreCase(MAY)){
		return MONTH_TYPE.MAY;
	}else if(month.equalsIgnoreCase(JUN)){
		return MONTH_TYPE.JUN;
	}else if(month.equalsIgnoreCase(JUL)){
		return MONTH_TYPE.JUL;
	}else if(month.equalsIgnoreCase(AUG)){
		return MONTH_TYPE.AUG ;
	}else if(month.equalsIgnoreCase(SEP)){
		return MONTH_TYPE.SEP;
	}else if(month.equalsIgnoreCase(OCT)){
		return MONTH_TYPE.OCT;
	} else if(month.equalsIgnoreCase(NOV)){
		return MONTH_TYPE.NOV;
	} else if(month.equalsIgnoreCase(DEC)){
		return MONTH_TYPE.DEC;
	} else{
		return MONTH_TYPE.INVALID;
	}
}

//Edit Commands to use
	private static final String DEADLINE = "deadline";
	private static final String STARTTIME = "starttime";
	private static final String ENDTIME = "endtime";
	private static final String ENDDATE = "enddate";
	private static final String STARTDATE = "startdate";

	//Months
	private static final String JAN = "Jan";
	private static final String FEB = "Feb";
	private static final String MAR = "Mar";
	private static final String APR = "Apr";
	private static final String MAY = "May";
	private static final String JUN = "Jun";
	private static final String JUL = "Jul";
	private static final String AUG = "Aug";
	private static final String SEP = "Sep";
	private static final String OCT = "Oct";
	private static final String NOV = "Nov";
	private static final String DEC = "Dev";

	private static final int MONTH_ONE = 1;
	private static final int MONTH_TWO = 2;
	private static final int MONTH_THREE = 3; 
	private static final int MONTH_FOUR = 4;
	private static final int MONTH_FIVE = 5;
	private static final int MONTH_SIX = 6;
	private static final int MONTH_SEVEN = 7; 
	private static final int MONTH_EIGHT = 8;
	private static final int MONTH_NINE = 9; 
	private static final int MONTH_TEN = 10;
	private static final int MONTH_ELEVEN = 11; 
	private static final int MONTH_TWELVE = 12;
	private static final int MONTH_DEFAULT = 0;

private String swapChar(String dateTimeString) {
String[] words = dateTimeString.split(SPACE);
String properDate = " "; 
String temp = words[0];
words[0] = words[1];
words[1] = temp;
for(String element: words) {
	properDate += (element + " ");
}	
return properDate;	
}
if(dateString.contains("/") || dateString.contains("-")){
System.out.println("2 Here");
int posMonth = dateString.indexOf("/");
String month = dateString.substring(0,posMonth).trim();
System.out.println("month : "+month);
int posDay = dateString.indexOf('/', posMonth);
String day = dateString.substring(posMonth+1,posDay);
System.out.println("day : "+day);
}

/*private static int[] getDateAndTime(String testDate) {
//Sun Jan 25 12:17:00 SGT 2015
int[] info = new int[SIZE_DATE_TIME];
String[] content = testDate.split(SPACE);
String dateAndTime = content[SIZE_THREE];
String[] Time = dateAndTime.split(COLON);		
info[POS_ZERO] = Integer.parseInt(content[POS_FIVE]);
MONTH_TYPE month = getMonth(content[POS_ONE]);
info[POS_ONE] = convertStringMonthToIntMonth(month);
info[POS_TWO] =  Integer.parseInt(content[POS_TWO]);
info[POS_THREE] = Integer.parseInt(Time[POS_ZERO]);
info[POS_FOUR] = Integer.parseInt(Time[POS_ONE]);
return info;
}
*This method determines if the user has entered to modify task or wants to change time
 * 
 * @param message
 * @return true if user wants to modify tasks and false if otherwise.
 *
private boolean isEditDateTime(String message) {
	return message.contains(DEADLINE) || message.contains(STARTTIME)||message.contains(ENDTIME)||message.contains(STARTDATE)||message.contains(ENDDATE);
}*/
