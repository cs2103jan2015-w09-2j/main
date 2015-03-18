import java.util.ArrayList;


public class Display {

	private static DateView viewsOfDays;
	private static String message;
	private static int i;
	
	
	public  Display(View dateView, String msg){
		viewsOfDays = (DateView)dateView;
		message = msg;
		
	}
	
	public static String getToday(){
		String tasksForToday = getTask(viewsOfDays.getToday());
		return tasksForToday;
	}
	
	public static String getUpcoming(){
		String upcomingTasks = getTask(viewsOfDays.getUpcoming());
		return upcomingTasks;
	}

	public static String getSomeday(){
		String tasksForSomeday = getTask(viewsOfDays.getSomeday());
		return tasksForSomeday;

	}
	
	
	public static String getTask(ArrayList<Task> taskArray){
		String tasks = "";
		for (Task task : taskArray){
		    i++;
			tasks +=  i+"."+ task.toString() + "\n";
		}
		return tasks;
	}
	
	public static String getMessage(){
		return message;
	}
	

}
