
public class Display {

	private static DateView viewsOfDays;
	private static String message;
	private static int i;
	
	
	public  Display(View dateView, String msg){
		viewsOfDays = (DateView)dateView;
		message = msg;
		
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
	
	
	public static String getMessage(){
		return message;
	}
	

}
