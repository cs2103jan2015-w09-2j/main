import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

public class Data extends Observable{

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
	
	//setter
	public boolean add(Task input){
		boolean isAdded = myList.add(input);
		sort();
		
	    setChanged();
	    notifyObservers();
	      
		return isAdded;
	}
		
	public boolean remove(Task task){		
		boolean isRemoved = myList.remove(task);
		
	    setChanged();
	    notifyObservers();
	    
		return isRemoved;
	}
	
	public void update(Task currTask, Boolean isCompleted){
		currTask.setIsCompleted(isCompleted);
		sort();
		
	    setChanged();
	    notifyObservers();
	}
	
	public void update(Task currTask, Task updateElement){
		currTask.update(updateElement);
		sort();
		
	    setChanged();
	    notifyObservers();
	}
		
	//others
	public void sort(){
		//Collections.sort(myList, new DataComparator());
	}
		
	public boolean isEmpty(){
		return myList.size() <= 0;
	}
	
	/*public View toDateView(){
		Collections.sort(data, new DataComparator());
		
		ArrayList<Task> today = new ArrayList<Task>();
		ArrayList<Task> upcoming = new ArrayList<Task>();
		ArrayList<Task> someday = new ArrayList<Task>();
		
		for(Task element : data){
			if(element.isNormalTask()){
				today.add(element);
			}
			else{
				upcoming.add(element);
			}
		}
		
		view = new DateView(today, upcoming, someday);
		return view;
	}*/
		
}
