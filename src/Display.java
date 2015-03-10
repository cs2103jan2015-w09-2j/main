import java.util.ArrayList;


public class Display {

	private static DateView viewsOfDays;
	private static ArrayList<Task> collectionOfTasks;
	private static String message;
	private static int i;
	
	public  Display(DateView dateView, String msg){
		viewsOfDays = dateView;
		message = msg;
	}
	
	public Display(ArrayList<Task> arrayList, String messageEmpty) {
		collectionOfTasks = arrayList;
		message = messageEmpty;
		
	}
	public static String getToday(){
		String tasksForToday = "";
		i=0;
		for (Task task : viewsOfDays.getToday()){
			i++;
			tasksForToday +=  i+"."+task.toString() + "\n";
		}
		return tasksForToday;
	}
	
	public static String getUpcoming(){
		String upcomingTasks = "";
		i=0;
		for (Task task : viewsOfDays.getUpcoming()){
			i++;
			upcomingTasks +=  task.toString() + "\n";
		}
		return upcomingTasks;
	}

	public static String getSomeday(){
		String tasksForSomeday = "";
		i=0;
		for (Task task : viewsOfDays.getSomeday()){
		    i++;
			tasksForSomeday +=  i+"."+ task.toString() + "\n";
		}
		return tasksForSomeday;
	}
	
	
	public String getMessage(){
		return message;
	}
	

}
