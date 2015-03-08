import java.util.ArrayList;


public class Display {
	private static ArrayList<Task> collectionOfTasks;
	private static String message;
	
	public  Display(ArrayList<Task> arrayList, String msg){
		collectionOfTasks = arrayList;
		message = msg;
	}
	
	public static String getTasks(){
		String tasks = collectionOfTasks.toString();
		return tasks;
	}
	
	public String getMessage(){
		return message;
	}
}
