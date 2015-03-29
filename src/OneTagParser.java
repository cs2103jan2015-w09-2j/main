import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import java.lang.String;

import com.joestelmach.natty.*;

public class OneTagParser {
	private static final String INVALID_EDIT_COMMAND = "INVALID ERROR COMMAND!";
	private static final int DUMMY_VALUE = -1;
	private static final int NUM_ZERO = 0;
	private static final int POS_ZERO = 0;
	private static final int SIZE_ONE = 1;
	private static final int NUM_ONE = 1;
	private static final int POS_ONE = 1;
	private static final int POS_TWO = 2;
	private static final int NUM_TWO = 2;
	private static final int POS_THREE = 3;
	private static final int SIZE_THREE = 3;
	private static final int POS_FOUR = 4; 
	private static final int SIZE_DATE_TIME = 5;
	private static final int POS_FIVE = 5;
	
	
	//TO SPLIT A STRING
	private static final int INPUT_SPLIT_THIRD = 2;
	private static final int INPUT_SPLIT_FIRST = 0;
	private static final int INPUT_SPLIT_SECOND = 1;	
	private static final int INPUT_SPLIT_FOURTH = 3;	
	
	//String used in parser
	private static final String COLON = ":";
	private static final String SPACE = " ";
	
	//Error Messages
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
	
	//Msg Indicating Presence of Time
	private static final String FROM = "from";
	private static final String TO = "to";
	private static final String BY = "by";
	private static final String AT = "at";
	private static final String ON = "on";
	private static final String IN = "in";

	//@author A0108436H
	/**Takes input String from Logic and sends it for sorting.
	 * 
	 * @param input String given by Logic
	 * @return Cmd after parsing
	 */
	public Cmd toCmd(String input) {
		return sortCmd(removeWhiteSpace(input), getNumWords(input));
	}
	//@author A0108436H
	/** Determines if user has entered a "one-word" command("Home","Done","Today" e.t.c) or commands with many words ("add task")
	 * @param input
	 * @param numWord
	 * @return Cmd that contains object.
	 */
	private Cmd sortCmd(String input, int numWords) {
		//Enter Error Handling for Zero Words here
		if(isOneWordCommand(numWords)){
			return parseOneWordCmd(input);
		}
		else{
			return separateCmdFromMsg(input);
		}
	}
	//@author A0108436H
	/**Separates the command from the message and sends the information for parsing.
	 * 
	 * @param input
	 * @return
	 */
	private Cmd separateCmdFromMsg(String input) {
		String[] inputArr = separateFirstWordFromRest(input);	
		return determineCmd(getCommand(setEditType(inputArr)),getMessage(setEditTaskNum(inputArr)));
	}
	//@author A0108436H
	/**Determines which cmd the user has input and sends it for parsing.
	 * 
	 * @param cmd
	 * @param message
	 */
	private Cmd determineCmd(COMMAND_TYPE cmd, String message){
		switch (cmd) {
		case ADD:
			return new AddCmd(parseMsgforAddCmd(message));
			
		case EDIT:
			return new EditCmd(parseEditNUM(message),parseEditDate(message));
			return null;
		case DELETE: 	
			return new DeleteCmd(parseNum(message));
		case DONE : 
			return new CompletedCmd(parseNum(message));
		case SEARCH :
			  return new SearchCmd(message);
		case SAVE : 
			 return new SaveCmd(message);
		default:
			throw new Error(INVALID_MSG);
		}		
	}
	//@author:A0108436H
	/**Returns Task Num to be edited.
	 * 
	 * @param message
	 * @return int task number.
	 */
	
	private int parseEditNUM(String message) {
		if(isEditDateTime(message)){
			String[] separateWords = message.split(SPACE,INPUT_SPLIT_FOURTH);
			return parseNum(setEditTaskNum(separateWords));
		}
		else{
			String[] inputArr = separateFirstWordFromRest(message);	
			return parseNum(setEditType(inputArr));
		}
	}
	//@author:A0108436H
	/**Returns Task object with dates editted.
	 * @param message
	 * @return
	 */
	private Task parseEditDate(String message) {
		boolean isChangeDateTime = isEditDateTime(message);
		if(isChangeDateTime){
			int[] infoDateTime = new int[SIZE_DATE_TIME];
			String[] separateWords = message.split(SPACE,INPUT_SPLIT_FOURTH);
			String editType = setEditType(separateWords);
			EDIT_TYPE command = getEditCmd(editType);
			String taskNum = setEditTaskNum(separateWords);
			String editDateTime = setEditDateTime(separateWords);
			switch(command){
			case DEADLINE:
					infoDateTime = parseDateTime();
					return new Task(-1,-1,-1,-1,-1,infoDateTime[0],infoDateTime[1],infoDateTime[2],infoDateTime[3],infoDateTime[4],null);
			case STARTTIME:
					infoDateTime = parseDateTime();
					return new Task(-1,-1,-1,infoDateTime[3],infoDateTime[4],-1,-1,-1,-1,-1,null);
			case STARTDATE:
					infoDateTime = parseDateTime();
					return new Task(infoDateTime[0],infoDateTime[1],infoDateTime[2],-1,-1,-1,-1,-1,-1,-1,null);
			case ENDTIME:
					infoDateTime = parseDateTime();
					return new Task(-1,-1,-1,-1,-1,-1,-1,-1,infoDateTime[3],infoDateTime[4],null);
			case ENDDATE:
					infoDateTime = parseDateTime();
					return new Task(-1,-1,-1,-1,-1,infoDateTime[0],infoDateTime[1],infoDateTime[2],infoDateTime[3],infoDateTime[4],null);
			
			case INVALID:
					throw new Error(INVALID_EDIT_COMMAND);
			}
		}
		else{
			String[] editTaskDescription = separateFirstWordFromRest(message);
			String taskDescription = editTaskDescription[POS_TWO];
			return new Task(taskDescription);
		}
	}
	//@author:A0108436H
	/**Sets Date/Time String to be edited.
	 * @param separateWords
	 * @return String Date/Time
	 */
	private String setEditDateTime(String[] separateWords) {
		return separateWords[INPUT_SPLIT_THIRD];
	}
	//@author:A0108436H
	/**Sets the Task Number to be edited.
	 * @param separateWords
	 * @return
	 */
	private String setEditTaskNum(String[] separateWords) {
		return separateWords[INPUT_SPLIT_SECOND];
	}
	//@author: A0108436H
	/**Sets the setEditType.
	 * 
	 * @param separateWords
	 * @return
	 */
	private String setEditType(String[] separateWords) {
		return separateWords[INPUT_SPLIT_FIRST];
	}
	//@author: A0108436H
	/**This method converts the integer of String type to integer of Int type
	 * 
	 * @param message (type String)
	 * @return integer value of the string
	 */
	private int parseNum(String message) {
		return Integer.parseInt(message);
	}
	//@author A0108436H
	/**Determines what type of edit.
	 * 
	 * @param message
	 * @return
	 */
	private Task parseEdit(String message) {
		if(isEditDateTime(message)){
			return parseEditDateTime(message);
		}
		else{
			String[] editTaskDescription = separateFirstWordFromRest(message);
			return new EditCmd(Integer.parseInt(editTaskDescription[POS_ZERO]),new Task(editTaskDescription[POS_TWO]));
		}	
	}
	//@author:A0108436H
	/**This method determines if the user has entered to modify task or wants to change time
	 * 
	 * @param message
	 * @return true if user wants to modify tasks and false if otherwise.
	 */
	private boolean isEditDateTime(String message){
		return message.contains(DEADLINE) || message.contains(STARTTIME)||message.contains(ENDTIME)||message.contains(STARTDATE)||message.contains(ENDDATE);
	}
	
	//@author:A0108436H
	/** This task takes in string message and returns for parsing.
	 * 
	 * @param message
	 * @return Task object to Logic.
	 */	
	private Task parseMsgforAddCmd(String message) {
		return initializeVariablesforParsing(convertStringIntoWords(message),message);
	}
	//@author:A0108436H
	/**This method initializes the variables needed for parsing an add task and send it for parsing.
	* @param word
	* @param message
	*/
	private Task initializeVariablesforParsing(String[] word, String message) {
		String parsedDate = null;
		String testWord = null , dateString = null,taskDescription = null;	
		boolean isDeadlineTask = false;
		boolean isTimedTask = false;
		int[] dateTime = createDateTimeArray();
		int[] endDateTime = createDateTimeArray();
		int[] startDateTime = createDateTimeArray();
		Parser dateParser = new Parser();
		int posKeyword = setPositionOfKeyword(message) ;	
		return parseWords(parsedDate, testWord,  dateString,  taskDescription,  isDeadlineTask,  isTimedTask, dateTime , endDateTime, startDateTime,dateParser, posKeyword, word, message);
		}
	//@author:A0108436H
	/**This method iterates through the string for type words and proceeds to parse them accordingly.
	 * @param word
	 * @param Task
	 */
	private Task parseWords(String parsedDateString, String testWord,String dateString, String taskDescription,boolean isDeadlineTask, 
							boolean isTimedTask, int[] dateTime,int[] endDateTime, int[] startDateTime, Parser dateParser,int posKeyword, String[] word, String message) {
		for(int count =shiftOneLeft(getNumWords(message)); count >= NUM_ZERO; count--){
		testWord =removeWhiteSpace(word[count]);
		if(checkTimedTask(testWord)){
			isTimedTask = true; 
			dateString = word[shiftOneRight(count)] + SPACE ;
			if(isFirstKeywordFound(posKeyword, word)){
				dateString = getDateTimeString(dateString, posKeyword, word,count);
				List<DateGroup> listOfDates = performParsing(dateString, dateParser);
				parsedDateString = getDateTimeinString(parsedDateString,listOfDates);
				endDateTime = getArrayOfDateAndTime(parsedDateString);
				posKeyword = updatePosKeyword(count);
			}else{
				dateString = getNewDateTimeString(dateString, posKeyword, word,count);
				List<DateGroup> listOfDates = performParsing(dateString, dateParser);
				parsedDateString = getDateTimeinString(parsedDateString,listOfDates);
				startDateTime = getArrayOfDateAndTime(parsedDateString);
				posKeyword = updatePosKeyword(count);
				taskDescription = getTaskDescription(posKeyword, word);
		}
			return new Task(startDateTime[POS_ZERO], startDateTime[POS_ONE], startDateTime[POS_TWO], startDateTime[POS_THREE], startDateTime[POS_FOUR], 
					endDateTime[POS_ZERO],endDateTime[POS_ONE],endDateTime[POS_TWO],endDateTime[POS_THREE],endDateTime[POS_FOUR], taskDescription);
	}
		else if(isDeadlineTask(testWord)){
			isDeadlineTask = true;
			posKeyword = count;
			dateString = getDateString(word, message, count);
			List<DateGroup> listOfDates = performParsing(dateString,dateParser);
			parsedDateString = getDateTimeinString(parsedDateString,listOfDates);
			int[] deadlineDateAndTime = getArrayOfDateAndTime(parsedDateString);
			taskDescription = getTaskDescription(posKeyword,word);
			return new Task(deadlineDateAndTime[POS_ZERO], deadlineDateAndTime[POS_ONE], deadlineDateAndTime[POS_TWO], deadlineDateAndTime[POS_THREE],deadlineDateAndTime[POS_FOUR],taskDescription);	
		}
	}
		if(isFloatingTask(isDeadlineTask, isTimedTask)){
			taskDescription=getTaskDescription(word.length,word);
			return new Task(taskDescription);
		}
		throw new Error(ERROR_ADD_CMD);
	}
	private String getDateString(String[] word, String message, int count) {
		String dateString;
		dateString = word[shiftOneRight(count)] + SPACE;
		dateString = getParseDate(dateString, word, message, count);
		return dateString;
	}
	//@author:A0108436H
	/**This method subtracts one from the number of Words in the string.
	 * 
	 * @param numWords
	 * @return int numWords - 1.
	 */
	private int shiftOneLeft(int position) {
		return position - NUM_ONE;
	}
	//@author: A0108436H
	/**This method converts String into Words
	* 
	* @param message
	* @return String Array of Words
	 */
	private String[] convertStringIntoWords(String message) {
		return splitIntoWords(message);
	}
	
	//@author A0108436H
	/**This method creates an array of size 5 to be used for date and time purposes.
	* @return integer array of size 5.
	*/
	private int[] createDateTimeArray() {
	return new int[SIZE_DATE_TIME];
	}	
	//@author A0108436H
	/**This method sets the Position of the Keyword to the last position of the array.
	*
	* @param message
	* @return position of array.
	*/
	private int setPositionOfKeyword(String message) {
	return decrementByOne(message);
	}
	private int decrementByOne(String message) {
		return getNumWords(message) - NUM_ONE;
	}
	
	//@author:A0108436H
	/**Shifts position in array to one place right.
	 * 
	 * @param position
	 * @return position +1
	 */
	private int shiftOneRight(int position) {
		return position + NUM_ONE;
	}
	//@author A0108436H
	/**This method determines if the task is floating or not
	 * 
	 * @param isDeadlineTask
	 * @param isTimedTask
	 * @return true if floating task and false if otherwise.
	 */
	private boolean isFloatingTask(boolean isDeadlineTask, boolean isTimedTask) {
		return isDeadlineTask == false && isTimedTask == false;
	}	
	//@author:A0108436H
	/**This method returns the getParseDate
	 * 
	 * @param parseDate
	 * @param word
	 * @param message
	 * @param count
	 * @return
	 */
	private String getParseDate(String parseDate, String[] word,String message, int count) {
		for(int i = shiftTwoRight(count) ; i < getNumWords(message); i++)
		{
			parseDate += word[i] + SPACE;
		}
		return parseDate;
	}
	//@author A0108436H
	/**Returns true if testWord is "BY","AT","ON","IN" for deadlined task.
	 * 
	 * @param testWord
	 */
	private boolean isDeadlineTask(String testWord){
		return (testWord.equalsIgnoreCase(BY)||(testWord.equalsIgnoreCase(AT)) || (testWord.equalsIgnoreCase(ON)) || (testWord.equalsIgnoreCase(IN)));
	}
	//@author A0108436H
	/**Returns description of Task.
	* @param posKeyword
	* @param word
	 * @return taskDescription
	* 
	*/
	private String getTaskDescription(int posKeyword, String[] word) {
		String taskDescription;
		taskDescription = word[POS_ZERO] + SPACE ;
		for(int i = POS_ONE; i < posKeyword ; i++){
		taskDescription += word[i] + SPACE;			
		}
		return taskDescription;
	}
	//@author:A0108436H
	/**Converts the Date into a String.
	 * @param testDate
	 * @param groups
	 * @return testDate
	 */	
	private String getDateTimeinString(String testDate, List<DateGroup> groups) {
		for(DateGroup group:groups) {
			List<Date> dates = group.getDates();
		//	System.out.println("Size of dates in dates : "+dates.size());
			testDate = dates.get(POS_ZERO).toString();
			break;
		}
		return testDate;
	}
	//@author: A0108436H
	/**Converts the string into the List<DateGroup> (ACTUAL DATE PARSING!).
	 * @param parseDate
	 * @param dateParser
	 * @return List<DateGroup>
	 */
	private List<DateGroup> performParsing(String parseDate, Parser dateParser) {
		return dateParser.parse(parseDate);
	}
	//@author: A0108436H
	/**Returns the string of date and time for the "from DATE&TIME" component as "to DATE&TIME" has been parsed.
	 * @param parseDate
	 * @param posKeyword
	 * @param word
	 * @param count
	 * @return parseDate.
	 */	
	private String getNewDateTimeString(String parseDate, int posKeyword,
			String[] word, int count) {
		for(int i = shiftTwoRight(count); i < posKeyword; i++)	{
				parseDate += (word[i] + SPACE);
		}
		return parseDate;
	}
	
	private static int[] getArrayOfDateAndTime(String testDate) {
		//Sun Jan 25 12:17:00 SGT 2015
		int[] info = new int[SIZE_DATE_TIME];
		String[] content = testDate.split(SPACE);
		String dateAndTime = content[SIZE_THREE];
		String[] Time = dateAndTime.split(COLON);		
		info[POS_ZERO] = Integer.parseInt(content[POS_FIVE]);
		info[POS_ONE] = convertStringMonthToIntMonth(content[POS_ONE]);
		info[POS_TWO] =  Integer.parseInt(content[POS_TWO]);
		info[POS_THREE] = Integer.parseInt(Time[POS_ZERO]);
		info[POS_FOUR] = Integer.parseInt(Time[POS_ONE]);
		return info;
		}
	//@author: A0108436H
	/**This method returns the date and time string which can be parsed by the parser.
	 * 
	 * @param parseDate
	 * @param posKeyword
	 * @param word
	 * @param count
	 * @return parseDate
	 */
	private String getDateTimeString(String parseDate, int posKeyword,String[] word, int count) {
		for(int i = shiftTwoRight(count); i <= posKeyword; i++){
			parseDate += (word[i] + " ");	
		}
		return parseDate;
	}
	//@author:A0108436H
	/**Shifts two position to the right.
	 * 
	 * @param position
	 * @return position moved two position right
	 */
	private int shiftTwoRight(int position) {
		return position + NUM_TWO;
	}
	//@author A0108436H
	/**This method parses the Date and Time
	* @param word
	* @param Task
	 */
	private int[] parseDateAndTime(String testDate, String parseDate,Parser dateParser) {
		List<DateGroup> groups = performParsing(parseDate, dateParser);
		testDate = getTestDate(testDate, groups);
		return getDateAndTime(testDate);
	}
	//@author A0108436H
	/**Returns true if testWord is the first Keyword found and false if it had been found already.
	 * @param posKeyword,word
	 * @param true if the first keyword and false if keyword has already been found.
	 */			
	private boolean isFirstKeywordFound(int posKeyword, String[] word) {
		return posKeyword == word.length- NUM_ONE;
	}	
	//@author A0108436H
	/**Determines whether cmd is 'View' ,'Help', 'Undo', 'Exit' and returns appropriate Cmd object.
	 * 
	 * @param input
	 * @return Cmd 
	 */
	private Cmd parseOneWordCmd(String input){
		COMMAND_TYPE command = getCommand(input);
		//This can be re-factored further.
		switch (command){
		case HOME: 
		case DONE:
		case TODAY:
		case UPCOMING:
		case SOMEDAY:
					returnViewCmd(input);
					break;
		case HELP: 
					returnHelpCmd();
					break;
		case UNDO:
					returnUndoCmd();
					break;
		case EXIT:
					returnExitCmd();
					break;
		//Need to add assumption here.
		default:
			throw new Error("invalid");
	}	
	//@author A0108436H
	/**Exit the program.
	 * 
	 */	
	private void returnExitCmd() {
		System.exit(0);
	}
	//@author A0108436H
	/**Returns UndoCmd object to Logic.
	 * 
	 * @return UndoCmd();
	 */
	private UndoCmd returnUndoCmd() {
		return new UndoCmd();
	}
	//@author A0108436H
	/**Returns HelpCmd Object back to Logic
	 * 
	 * @return HelpCmd();
	 */	
	private Cmd returnHelpCmd() {
		return new HelpCmd();
	}		
	//@author A0108436H
	/**Returns ViewCmd Object back to Logic
	 * 
	 * @param: input 
	 * @return:ViewCmd(view)
	 */
	private Cmd returnViewCmd(String input) {
		String view = input;
		return new ViewCmd(view);
	}
	//@author A0108436H
	/**Returns true if num of Words in String is 1. False if otherwise.
	 * 
	 * @param numWord
	 * @return true if num of Words is one and false if number of words is more than one.
	 */
	private boolean isOneWordCommand(int numWord) {
		return (numWord == SIZE_ONE);
	}
	//@author:A0108436H
	/**Returns the number of words that the user has typed it.
	 * 
	 * @param input
	 * @return number of words that the user has entered into the command prompt.
	 */
	private int getNumWords(String  input) {
		String[] inputArr = splitIntoWords(input);
		return inputArr.length;
	}
	//@author: A0108436H
	/**Split a string into Words
	 * 
	 * @param input
	 * @return string array of words in the string
	 */	
	private String[] splitIntoWords(String input) {
		return input.split(SPACE);
	}
	//author A0108436H
	/** This method returns copy of the string, with leading and trailing whitespace omitted.
	 * 
	 * @param input string 
	 * @return input string with leading and trailing whitespace omitted
	 */
	private String removeWhiteSpace(String input) {
		return input.trim();
	}
	//@author A0108436H
	/**This method separates the first word from the remaining string.
	 * 
	 * @param input
	 * @return String[] contains the first word and the remianing string.
	 */
	private String[] separateFirstWordFromRest(String input) {
		return input.split(SPACE,INPUT_SPLIT_THIRD);
	}
	//@author:A0108436H
	/**This methods separates the 

	private static int parseNum(String message) {
		return Integer.parseInt(message);
	}
	*/
	//@author : A0108436H
	/** This method converts the MONTH_TYPE to the position they occupy in the year (Jan == 1, Dec == 12)
	 * 
	 * @param month
	 * @return Number of month
	 */
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
	}
	//@author A0108436H
	/**This method updates the posKeyword to reflect the new position of keyword
	 * 
	 * @param count
	 * @return posKeyword, the new position of keyword.
	 */
	private static int updatePosKeyword(int count) {
		int posKeyword;
		posKeyword = count;
		return posKeyword;
	}

	//@author A0108436H
	/**This method return true if the word tested is either a "from" or "to".
	 * 
	 * @param testWord
	 * @return
	 */
	private static boolean checkTimedTask(String testWord) {
		return testWord.equalsIgnoreCase(FROM)|| testWord.equalsIgnoreCase(TO);
	}

	
	//@author A0108436H
	/**This method converts the editString into an EDIT_TYPE.
	 * 
	 * @param testWord
	 * @return EDIT_TYPE
	 */
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
	//@author:A0108436H
	/**This method changes the month value from a String to a MONTH_TYPE value
	 * 
	 * @param month
	 * @return MONTH_TYPE
	 * */
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
	//@author: Chun How + A0108436H
	/**This method converts the user comand into COMMAND_TYPE
	 * 
	 * @param input
	 * @return COMMAND_TYPE
	 */
	private static COMMAND_TYPE getCommand(String input){
		if (input == null){
			throw new Error(INVALID_CMD_MSG);
		}
		if (input.equalsIgnoreCase(HOME)) {
			return COMMAND_TYPE.HOME;
		}else if (input.equalsIgnoreCase(DONE)) {
			return COMMAND_TYPE.DONE;
		}else if(input.equalsIgnoreCase(TODAY)){
			return COMMAND_TYPE.TODAY;
		}else if(input.equalsIgnoreCase(SOMEDAY)){
			return COMMAND_TYPE.SOMEDAY;
		}else if(input.equalsIgnoreCase(UPCOMING)){
			return COMMAND_TYPE.UPCOMING;
		}else if (input.equalsIgnoreCase(ADD)) {
			return COMMAND_TYPE.ADD;
		} else if (input.equalsIgnoreCase(EXIT)) {
			return COMMAND_TYPE.EXIT;
		} else if (input.equalsIgnoreCase(SEARCH)){
			return COMMAND_TYPE.SEARCH;
		} else if (input.equalsIgnoreCase(SAVE)){
			return COMMAND_TYPE.SAVE;
		} else if (input.equalsIgnoreCase(EDIT)){
			return COMMAND_TYPE.EDIT;
		} else if (input.equalsIgnoreCase(UNDO)){
			return COMMAND_TYPE.UNDO;
		} else if (input.equalsIgnoreCase(HELP)){
			return COMMAND_TYPE.HELP;
		} else if (input.equalsIgnoreCase(DELETE)){
			return COMMAND_TYPE.DELETE;
		} else {
			return COMMAND_TYPE.INVALID;
		}
}	
	private static String getMessage(String input) {
		return input;
	}
}

