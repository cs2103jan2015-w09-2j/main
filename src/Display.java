import java.util.ArrayList;


public class Display {

	private static DateView viewsOfDays;
	private static ArrayList<Task> collectionOfTasks;
	private static String message;
	
	public  Display(DateView dateView, String msg){
		viewsOfDays = dateView;
		message = msg;
	}
	
	public Display(ArrayList<Task> arrayList, String messageEmpty) {
		collectionOfTasks = arrayList;
		message = messageEmpty;
		
	}

	public static String getSomeday(){
		String tasksForSomeday = "";
		for (Task task : viewsOfDays.getSomeday()){
			tasksForSomeday +=  task.toString() + "\n";
		}
		return tasksForSomeday;
	}
	
	public static String getToday(){
		
		String tasksForToday = "";
		for (Task task : viewsOfDays.getToday()){
			tasksForToday +=  task.toString() + "\n";
		}
		return tasksForToday;
	}
	
	public static String getUpcoming(){
		String upcomingTasks = "";
		for (Task task : viewsOfDays.getUpcoming()){
			upcomingTasks +=  task.toString() + "\n";
		}
		return upcomingTasks;
	}
	
	public String getMessage(){
		return message;
	}
	

}
