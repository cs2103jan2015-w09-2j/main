import java.util.ArrayList;
import java.util.Collections;

public class Data{

	private static Data data;
	private ArrayList<Task> myList;
	
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
	
	public void set(ArrayList<Task> myList){
		this.myList = myList;
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
			if(task.getDescription().contains(text)){
				searchedList.add(task);
			}
		}
		return searchedList;	
	}
		
	//setter
	public boolean add(Task input){
		return myList.add(input);

	}
		
	public boolean remove(Task task){		
		return myList.remove(task);
	}
	
	public void update(Task currTask, Boolean isCompleted){
		currTask.setIsCompleted(isCompleted);
	}
	
	public void update(Task currTask, Task updateElement){
		currTask.update(updateElement);
	}
			
}
