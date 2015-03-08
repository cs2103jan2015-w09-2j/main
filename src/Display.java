import java.util.ArrayList;


public class Display {
	private ArrayList<Task> collectionOfTasks;
	private String message;
	
	public Display (ArrayList<Task> collectionOfTasks, String message){
		this.collectionOfTasks = collectionOfTasks;
		this.message = message;
	}
	
	public String getTasks(){
		String tasks = collectionOfTasks.toString();
		return tasks;
	}
	
	public String getMessage(){
		return message;
	}
}
