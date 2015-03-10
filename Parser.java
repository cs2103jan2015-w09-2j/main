import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.logging.*;
import java.lang.String;
import java.text.DateFormat;
import java.util.Date;

public class Parser {
	private static final String HOMEVIEW_COMMAND = " ";
	private static final String ADD_COMMAND = "add";
	private static final String MODIFY_COMMAND = "modify";
	private static final String SAVE_COMMAND = "save";
	private static final String UNDO_COMMAND = "undo";
	private static final String HELP_COMMAND = "help";
	private static final String EXIT_COMMAND = "exit";
	private static final String DONE_COMMAND = "done";
	
	/*This commands are used to determine what tasks are given to us*/
	private static final String TEST_DEADLINE = "(?i)by ";
	private static final String TEST_FIXED = "(?i)from ";
	private static final String PATTERN_BY = ".*(?i)by(?-i) .+";
	private static final String PATTERN_FROM = ".*(?i)from(?-i) .+";
	
	private static Logger logger = Logger.getLogger("Parser");
	
	/**
	 * this compares the different command words
	 * @param: input userInput, the String typed in by the User
	 * @return UserInput after parsing
	 */
	public Input parse(String inputString)
	{
		logger.log(Level.INFO, "into parsing the string");
		Input userInput = new Input();
		/*check if user wants to enter view mode.*/
		if(testViewCommand(inputString))
		{
			return returnViewObject(inputString, userInput);
		}	
		if(inputString.startsWith(ADD_COMMAND))
		{
			return returnAddTask(inputString, userInput);
		}
			
	}
	private Input returnViewObject(String inputString, Input userInput)
	{	
		userInput.setViewCommand();
		if(inputString == "#")
		{
			userInput.setHashTagView();
		}
		else if(inputString == "@")
		{
			userInput.setPeopleView();
		}
		else if(inputString == "done")
		{
			userInput.setCompletedTaskView();
		}
		else if(inputString == "")
		{
			userInput.setHomeView();
		}
		return userInput;
	}
	private boolean testViewCommand(String inputString) {
		if(inputString == "#" || inputString == "@" || inputString == "done" || inputString == "")
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	private Input returnAddTask(String inputString, Input userInput) {
		{		
			userInput.setAddTask();
			/*Determine what type of task it is*/
			inputString = inputString.substring(3);
			inputString.trim();
			
			Pattern pattern = Pattern.compile(PATTERN_BY);
			Matcher matcher = pattern.matcher(inputString);
			
			if(matcher.matches())
			{
				userInput.setDeadlineTask();
				return parseDeadlineTask(userInput,inputString);
			}
			else
			{
				Pattern patternFrom = Pattern.compile(PATTERN_FROM);
				Matcher matcherFrom = patternFrom.matcher(inputString);
			
				if(matcherFrom.matches())
				{
				userInput.setFixedTask();
				return parseFixedTask(userInput,inputString);
				}
				else
				{
					userInput.setFloatingTask();
					return parseFloatingTask(userInput,inputString);
				}
			}		
		}
	}
	private Input parseDeadlineTask(Input userInput, String inputString)
	{
//		log.info("Deadline for Task Found");
				String taskDescription = null;
				String taskDeadline;
				DateFormat df = new SimpleDateFormat("dd MMM yyyy");
				Date taskDate = null;
				String[] content = inputString.split(TEST_DEADLINE,2);
				taskDescription = content[0].trim();
				userInput.setTaskDescription(taskDescription);
				taskDeadline = content[1].trim();
				taskDate = df.parse(taskDeadline);
				userInput.setDate(taskDate);
				return userInput;
	}
	private Input parseFloatingTask(Input userInput, String inputString)
	{
		String taskDescription = null; 
		userInput.setTaskDescription(taskDescription);
		return userInput;
	}
	
	private Input parseFixedTask(Input userInput, String inputString)
	{
		DateFormat df = new SimpleDateFormat("dd MMMMM yyyy"); 
		Date startDate=null;
		Date endDate = null;
		   
		   String[] content = inputString.split("from",2);
		   String taskDescription = content[0];
		   userInput.setTaskDescription(taskDescription);
		   String timeInfo = content[1];
		   String[] content1 = timeInfo.split("to",2);
		   String tempStartDate = content1[0].trim();
		   try 
		    {
		     startDate = df.parse(tempStartDate);
		     userInput.setDate(startDate);
		    } catch (ParseException e) 
		    {
		      e.printStackTrace();
		    }
		   String tempEndDate = content1[1].trim();
		   try
		   {
		     endDate = df.parse(tempEndDate);
		   }catch(ParseException e)
		   {
		     e.printStackTrace();
		   }
	}
}
				
				
				
				
