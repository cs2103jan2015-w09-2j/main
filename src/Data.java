import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

public class Data extends Observable{

	private static Data theOne;
	private ArrayList<Task> data;
	
	//Constructor
	private Data(){
		data = new ArrayList<Task>();
	}
	
	private Data(ArrayList<Task> data){
		this.data = data;
	}
	
	public static Data getInstance(){
		if(theOne == null){
			theOne = new Data();
		}
		return theOne;
	}
	
	public static void setInstance(ArrayList<Task> data){
		theOne = new Data(data);
	}
	
	//getter
	public ArrayList<Task> getData(){
		return data;
	}
		
	public ArrayList<Task> getToday(){
		ArrayList<Task> todayList = new ArrayList<Task>();
		
		for(Task task : data){
			if(task.isTodayTask()){
				todayList.add(task);
			}
		}
		return todayList;
	}
	
	public ArrayList<Task> getUpcoming(){
		ArrayList<Task> upcomingList = new ArrayList<Task>();
		
		for(Task task : data){
			if(task.isTodayTask()){
				upcomingList.add(task);
			}
		}
		return upcomingList;
	}
	
	public ArrayList<Task> getSomeday(){
		ArrayList<Task> somedayList = new ArrayList<Task>();
		
		for(Task task : data){
			if(task.isTodayTask()){
				somedayList.add(task);
			}
		}
		return somedayList;
	}
	
	//setter
	public boolean add(Task input){
		boolean isAdded = data.add(input);
		sort();
		
	    setChanged();
	    notifyObservers();
	      
		return isAdded;
	}
		
	public boolean remove(Task task){		
		boolean isRemoved = data.remove(task);
		
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
		Collections.sort(data, new DataComparator());
	}
	
	public void clear(){
		data.clear();
		//view.clear();
	}
	
	public boolean isEmpty(){
		return data.size() <= 0;
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
