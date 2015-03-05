import java.util.PriorityQueue;


public class Display {
	private PriorityQueue<String> collectionOfTasks;
	private String message;
	
	public Display(PriorityQueue<String> collectionOfTasks, String message){
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
