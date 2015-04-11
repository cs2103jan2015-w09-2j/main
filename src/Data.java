import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class Data{

	private static Data data;
	private ArrayList<Task> myList;
	
	private Display display = Display.getInstance();
	
	//Constructor
	private Data(){
		myList = new ArrayList<Task>();
	}
	
	public static Data getInstance(){
		if(data == null){
			data = new Data();
		}
		return data;
	}
		
	//getter
	public ArrayList<Task> getData(){
		return myList;
	}
		
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
	
	public ArrayList<Task> getSomeday(){
		ArrayList<Task> somedayList = new ArrayList<Task>();
		
		for(Task task : myList){
			if(task.isSomedayTask() && !task.getIsCompleted()){
				somedayList.add(task);
			}
		}
		return somedayList;
	}
	
	public ArrayList<Task> getCompleted(){
		ArrayList<Task> completedList = new ArrayList<Task>();
		
		for(Task task : myList){
			if(task.getIsCompleted()){
				completedList.add(task);
			}
		}
		return completedList;
	}
	
	public ArrayList<Task> getSearched(String text){
		ArrayList<Task> searchedList = new ArrayList<Task>();

		for(Task task : myList){	
			if(task.getDescription().toLowerCase().contains(text.toLowerCase())){
				searchedList.add(task);
			}
		}
		return searchedList;	
	}
		
	//setter
	public void set(ArrayList<Task> myList){
		this.myList = myList;
		display.getView().update();
	}
	
	public boolean add(Task input){
		Boolean isAdded = myList.add(input);
		display.getView().update();
		
		return isAdded;
	}
		
	public boolean remove(Task task){
		Boolean isRemoved = myList.remove(task);
		display.getView().update();
		
		return isRemoved;
	}
	
	public void update(Task currTask, Boolean isCompleted){
		currTask.setIsCompleted(isCompleted);
		display.getView().update();
	}
	
	public void update(Task task, String newDescription){
		if(newDescription != null){
			task.setDescription(newDescription);
		}
		task.setStart(null);
		task.setEnd(null);
		display.getView().update();
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
		display.getView().update();
	}
			
}
