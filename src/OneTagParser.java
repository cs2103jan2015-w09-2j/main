import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Map;

import com.joestelmach.natty.*;

public class OneTagParser {
	private static final String Home = "Home";
	private static final String Done = "Done";
	private static final String Today = "Today";
	private static final String Upcoming = "Upcoming";
	private static final String Someday = "Someday";
	private static final int INPUT_SPLIT_SIZE = 2;
	private static final int INPUT_SPLIT_FIRST = 0;
	private static final int INPUT_SPLIT_SECOND = 1;
	private static final String STRING_FROM = "from";
	private static final String STRING_TO = "to";
	
	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Hello! Welcome to parser");
		String testString;
		for(int i = 0; i < 100 ; i++)
		{
			System.out.print("Enter command : ");
			testString = sc.nextLine();
			Cmd testCmd = toCmd(testString);
		//	Cmd testCmd = toCmd("add test string from 23 Jan 2015 12:15 to 25 Jan 2015 12:17");
		}
	}

	public static Cmd toCmd(String input) {
		input.trim();
		String[] inputArr = input.split(" ", INPUT_SPLIT_SIZE);
	//	System.out.println("Command : "+inputArr[0]);
		String firstWord = inputArr[0].trim();
		if(checkIfViewCmd(firstWord))
		{
			String view = firstWord;
			return new ViewCmd(view);
		}
		else if(checkHelpCmd(firstWord))
		{
			return new HelpCmd();
		}
		else if(firstWord.equals("Undo"))
		{
			return new UndoCmd();
		}
		COMMAND_TYPE command = getCommand(inputArr[INPUT_SPLIT_FIRST]);
		System.out.println(command);
		String message = getMessage(inputArr[INPUT_SPLIT_SECOND]);
		switch (command) {
		case ADD:
			return new AddCmd(parseMsg(message));
		case EDIT:
			return null;
		case DELETE: 	
			return new DeleteCmd(parseNum(message));
		case DONE : 
			return new CompletedCmd(parseNum(message));
		case SEARCH :
			  return null;
		case SAVE : 
			 String storageLocation = message;
			 return new SaveCmd(storageLocation);
		case EXIT:
			System.exit(0);
		default:
			throw new Error("invalid");
		}
	}

	private static boolean checkHelpCmd(String firstWord) {
		return firstWord.equals("Help");
	}
//	search 23 Jan 
/*
	private static String parseSearchString(String message) {
		String testDate = null;
		System.out.println(message);
		Parser dateParser = new Parser();
		List<DateGroup> groups = dateParser.parse(message);
		if(groups != null)
		{
			//user has entered a date task          
			for(DateGroup group:groups) 
			{
				List<Date> dates = group.getDates();
				System.out.println("Size of dates in dates : "+dates.size());
				testDate = dates.get(0).toString();
				System.out.println("Date parsed : "+testDate);
				break;
			}//public SearchCmd(int searchYear , int searchMonth , int searchDay , int searchHour, int searchMinute, String keyword)
			int[] infoDT = findTime(testDate);
			if(message.contains(":"))
			{
				return SearchCmd(infoDT[0],infoDT[1],infoDT[2],infoDT[3], infoDT[4]);
			}
			else
			{
				return SearchCmd(infoDT[0],infoDT[1],infoDT[2],-1,-1);
			}
			for(int content : informationDateAndTime)
			{
				System.out.println(content+"_");
			}
			if(testDate == null)
			{
				 System.out.println("The user has entered a task");
				 System.out.println(message);			 
			}
		}
		else
		{
			//Simply return the message to the user.
			System.out.println("return SearchCmd("+message+")");
		}
	
		
		return null;
	}
*/
	

	private static boolean checkIfViewCmd(String firstWord) {
		return firstWord.equals(Home) || firstWord.equals(Done) || firstWord.equals(Today) || firstWord.equals(Upcoming) || firstWord.equals(Someday);
	}	
	private static int parseNum(String message) {
		return Integer.parseInt(message);
	}
	private static Task parseMsg(String userInput){
			int printOnce = 1;
			boolean isDeadlineTask = false;
			boolean isTimedTask = false;
			String taskDescription;
			int[] infoTime = new int[5];
			int[] endDateAndTime = new int[5];
			String testDate = null;
			Parser dateParser = new Parser();
			String testWord , parseDate;
		//	testPrintUserInput(userInput);
			String[] content = userInput.split(" ");
			int posKeyword = content.length - 1 ;
		//	System.out.println("Poskeyword before entering array : "+posKeyword);
		//	testPrintContentArray(content);
			for(int count = content.length - 1 ; count >= 0 ; count--)
			{
				testWord = content[count].trim();
				if(checkTimedTask(testWord))
				{
					if(printOnce != 0)
					{
						System.out.println("Task Type : Timed Task");
						printOnce = 0;
					}
					isTimedTask = true; 
				//	testPrintLocationOfKeyword(content, count);
			//		System.out.println("Position of keyword is : "+posKeyword);					
					parseDate = content[count+1] + " ";
					if(posKeyword == content.length - 1)
					{
						for(int i = (count+2); i <= posKeyword; i++)
						{
							parseDate += (content[i] + " ");
							
						}
						List<DateGroup> groups = dateParser.parse(parseDate);
						for(DateGroup group:groups) 
						{
							List<Date> dates = group.getDates();
					//		System.out.println("Size of dates in dates : "+dates.size());
							testDate = dates.get(0).toString();
							break;
						}
						endDateAndTime = findTime(testDate);
		//				testPrintDateAndTimeInformation(endDateAndTime);
						posKeyword = updatePosKeyword(count);
		//				testPrintDateParseString(parseDate);
			//			testPrintDateObject(dateParser, parseDate);	
					}
					else
					{
						for(int i = (count+2); i < posKeyword; i++)
						{
							
							parseDate += (content[i] + " ");
						}
						List<DateGroup> groups = dateParser.parse(parseDate);
						for(DateGroup group:groups) 
						{
							List<Date> dates = group.getDates();
						//	System.out.println("Size of dates in dates : "+dates.size());
							testDate = dates.get(0).toString();
							break;
						}
						int[] startDateAndTime = findTime(testDate);
						//testPrintDateAndTimeInformation(startDateAndTime);
						posKeyword = updatePosKeyword(count);
						taskDescription = content[0] + " ";
						for(int i = 1; i < posKeyword ; i++)
						{
						taskDescription += content[i] + " ";			
						}
	//					System.out.println("Task description : "+taskDescription);
						System.out.println("return Task("+startDateAndTime[0]+","+startDateAndTime[1]+","+startDateAndTime[2]+","+startDateAndTime[3]+","+startDateAndTime[4]+","+endDateAndTime[0]+","+endDateAndTime[1]+","+endDateAndTime[2]+","+endDateAndTime[3]+","+endDateAndTime[4]+","+taskDescription+")");
						
				//		return Task(startDateAndTime[0], startDateAndTime[1], startDateAndTime[2], startDateAndTime[3], startDateAndTime[4], 
				//				endDateAndTime[0],endDateAndTime[1],endDateAndTime[2],endDateAndTime[3],endDateAndTime[4], taskDescription);
					}
				}
				else if(testWord.equals("by"))
				{
					if(printOnce != 0)
					{
						System.out.println("Task Entered : Deadline Task");
						printOnce = 0 ;
					}
					isDeadlineTask = true;
					posKeyword = count; 
				//	System.out.println("Type of task : deadline task");
				//	System.out.println("Location of by : "+count);
					taskDescription = content[0] + " ";
					parseDate = content[count+1] + " ";
					for(int i = count + 2 ; i < content.length; i++)
					{
						parseDate += content[i] + " ";
					}
				//	System.out.println(parseDate);
					
					for(int i = 1; i < posKeyword ; i++)
					{
					taskDescription += content[i] + " ";			
					}
				//	System.out.println("Task Description : "+taskDescription);
					List<DateGroup> groups = dateParser.parse(parseDate);
					for(DateGroup group:groups) 
					{
						List<Date> dates = group.getDates();
						//System.out.println("Size of dates in dates : "+dates.size());
						testDate = dates.get(0).toString();
						//System.out.println(testDate);
						break;
					}
					int[] deadlineDateAndTime = findTime(testDate);
				//	testPrintDateAndTimeInformation(deadlineDateAndTime);
					 System.out.println("return Task("+deadlineDateAndTime[0]+","+deadlineDateAndTime[1]+","+deadlineDateAndTime[2]+","+deadlineDateAndTime[3]+","+deadlineDateAndTime[4]+","+taskDescription+")");				
				//	return Task(deadlineDateAndTime[0], deadlineDateAndTime[1], deadlineDateAndTime[2], deadlineDateAndTime[3],deadlineDateAndTime[4],
				//		taskDescription);	
				}
			}	
				if(isDeadlineTask == false && isTimedTask == false)
				{
					System.out.println("Task Type : Floating Task");
					taskDescription = content[0];
					for(int i = 1 ; i < content.length ; i++)
					{
						taskDescription += content[i] +" ";
					}
					System.out.println("return task("+taskDescription+")");
					return null;
				}
				return null;
	}
		
	/*
			String[] content = userInput.split("");
			System.out.println(content[0]);
			for(int i = (content.length - 1) ; i >= 0 ; i--)
			{
				if(content[i].equals("from")
				{
					checkNum(content, i);	
			}
			String description;
		    String stringStartTime, stringEndTime;
		    int[] startTime = new int[5];
		    int[] endTime = new int[5];
		    
		    if(userInput.charAt(2) == '/')
		    {
		    	startTime = convertToInt(userInput);
		    	return new Task(startTime[0], startTime[1],startTime[2], startTime[3], startTime[4], -1,-1,-1,-1,-1,null);
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
		    */
		  

	private static void testPrintDateAndTimeInformation(int[] infoTime) {
		System.out.println("Year : "+infoTime[0]);
		System.out.println("Month : "+infoTime[1]);
		System.out.println("Day : "+infoTime[2]);
		System.out.println("Hour : "+infoTime[3]);
		System.out.println("Minute : "+infoTime[4]);
	}

	private static int[] findTime(String testDate) {
		//Sun Jan 25 12:17:00 SGT 2015
		int[] info = new int[5];
		String[] content = testDate.split(" ");
		String dateAndTime = content[3];
		String[] Time = dateAndTime.split(":");
		
		info[0] = Integer.parseInt(content[5]);
		info[1] = convertStringMonthToIntMonth(content[1]);
		info[2] =  Integer.parseInt(content[2]);
		info[3] = Integer.parseInt(Time[0]);
		info[4] = Integer.parseInt(Time[1]);
		
		return info;
	}

	private static int convertStringMonthToIntMonth(String month) {
		switch(month)
		{
			case "Jan":
				return 1; 	
			case "Feb":
				return 2;	
			case "Mar":
				return 3;
			case "Apr":
					return 4;	
			case "May":
				return 5; 	
			case "Jun":
				return 6;	
			case "Jul":
				return 7;
			case "Aug":
				return 8; 	
			case "Sep":
				return 9; 	
			case "Oct":
				return 10; 	
			case "Nov":
				return 11; 	
			case "Dec":
				return 12;	
			default:
				return 0; 
		}
	}

	private static void testPrintDateObject(Parser dateParser, String parseDate) {
		List<DateGroup> groups = dateParser.parse(parseDate);
		System.out.println("Number of date.groups : "+groups.size());
		for(DateGroup group:groups) {
		List<Date> dates = group.getDates(); 
		 System.out.println("Size of list : "+dates.size());
		System.out.println("Number of dates involved : "+dates.size());
		for(int i = 0; i < dates.size();i++)
		{
			System.out.println(dates.get(i).toString());
		}
		}
	}

	private static int updatePosKeyword(int count) {
		int posKeyword;
		posKeyword = count;
		return posKeyword;
	}

	private static void testPrintDateParseString(String parseDate) {
		System.out.println("printParseDate :"+parseDate);
	}

	private static String initializeParseDateString(String[] content, int count) {
		String parseDate;
		parseDate = content[count+1] + " ";
		return parseDate;
	}

	private static boolean checkTimedTask(String testWord) {
		return testWord.equals(STRING_FROM) || testWord.equals(STRING_TO);
	}

	private static void testPrintLocationOfKeyword(String[] content, int count) {
		System.out.println("\nKeyword Detected : "+content[count]+"\nat location : "+count);
	}

	private static void testPrintContentArray(String[] content) {
		System.out.println("testContentArray : ");
		for(String test : content)
			System.out.print(test+"_");
	}

	private static void testPrintUserInput(String userInput) {		
		System.out.println("testPrintUserInput :"+userInput);
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
		} else if (input.equalsIgnoreCase("search")){
			return COMMAND_TYPE.SEARCH;
		} else if (input.equalsIgnoreCase("save")){
			return COMMAND_TYPE.SAVE;
		} else if (input.equalsIgnoreCase("edit")){
			return COMMAND_TYPE.EDIT;
		} else if (input.equalsIgnoreCase("undo")){
			return COMMAND_TYPE.UNDO;
		} else if (input.equalsIgnoreCase("help")){
			return COMMAND_TYPE.HELP;
		} else if (input.equalsIgnoreCase("done")){
			return COMMAND_TYPE.DONE;
		} else if (input.equalsIgnoreCase("search")){
			return COMMAND_TYPE.SEARCH;
		} else if (input.equalsIgnoreCase("delete")){
			return COMMAND_TYPE.DELETE;
		} else {
			return COMMAND_TYPE.INVALID;
		}
}
		
	private static String getMessage(String input) {
		return input;
	}
}
