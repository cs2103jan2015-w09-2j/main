import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class Data{

	private static Data data;
	private ArrayList<Task> myList;
	
	//Constructor
	private Data(){
		myList = new ArrayList<Task>();
	}
	
	/**
	 * Returns an instance of this class
	 */
	public static Data getInstance(){
		if(data == null){
			data = new Data();
		}
		return data;
	}
		
	/**
	 * Returns an arraylist containing all of the task in this list.
	 */
	public ArrayList<Task> getData(){
		return myList;
	}
		
	/**
	 * Returns an arraylist containing today task in this list.
	 */
	public ArrayList<Task> getToday(){
		ArrayList<Task> todayList = new ArrayList<Task>();
		
		for(Task task : myList){
			if(task.isTodayTask() && !task.getIsCompleted()){
				todayList.add(task);
			}
		}
		Collections.sort(todayList, new EndTimeComparator());
		return todayList;
	}
	
	/**
	 * Returns an arraylist containing upcoming task in this list.
	 */
	public ArrayList<Task> getUpcoming(){
		ArrayList<Task> upcomingList = new ArrayList<Task>();
		
		for(Task task : myList){
			if(task.isUpcomingTask() && !task.getIsCompleted()){
				upcomingList.add(task);
			}
		}
		Collections.sort(upcomingList, new EndTimeComparator());
		return upcomingList;
	}
	
	/**
	 * Returns an arraylist containing someday task in this list.
	 */
	public ArrayList<Task> getSomeday(){
		ArrayList<Task> somedayList = new ArrayList<Task>();
		
		for(Task task : myList){
			if(task.isSomedayTask() && !task.getIsCompleted()){
				somedayList.add(task);
			}
		}
		return somedayList;
	}
	
	/**
	 * Returns an arraylist containing completed task in this list.
	 */
	public ArrayList<Task> getCompleted(){
		ArrayList<Task> completedList = new ArrayList<Task>();
		
		for(Task task : myList){
			if(task.getIsCompleted()){
				completedList.add(task);
			}
		}
		return completedList;
	}
	
	/**
	 * Returns an arraylist containing task containing text(not case sensitive) in this list.
	 * 
	 * @param text String to search for
	 */
	public ArrayList<Task> getSearched(String text){
		ArrayList<Task> searchedList = new ArrayList<Task>();

		for(Task task : myList){	
			if(task.getDescription().toLowerCase().contains(text.toLowerCase())){
				searchedList.add(task);
			}
		}
		return searchedList;	
	}
		
	/**
	 * Replace the current list with myList.
	 * 
	 * @param myList the arraylist whose task are to be placed into this list
	 */
	public void set(ArrayList<Task> myList){
		this.myList = myList;
	}
	
	/**
	 * Appends the specified task to the end of this list.
	 * 
	 * @param task task to be appended to this list
	 */
	public boolean add(Task task){
		return myList.add(task);
	}
	
	/**
	 * Removes the task at the specified position in this list.
	 * 
	 * @param task task to be remove
	 */
	public boolean remove(Task task){
		return myList.remove(task);
	}
	
	public void update(Task currTask, Boolean isCompleted){
		currTask.setIsCompleted(isCompleted);
	}
	
	public void update(Task task, String newDescription){
		if(newDescription != null){
			task.setDescription(newDescription);
		}
		task.setStart(null);
		task.setEnd(null);
	}
	
	public void update(Task task, String newDescription, LocalDateTime newStart, LocalDateTime newEnd){
		if(newDescription != null){
			task.setDescription(newDescription);
		}
		if(newStart != null){
			task.setStart(newStart);
		}
		if(newEnd != null){
			task.setEnd(newEnd);
		}
	}
			
}
