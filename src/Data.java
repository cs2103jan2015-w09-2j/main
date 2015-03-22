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
			
	//setter
	public boolean add(Task input){
		boolean isAdded = data.add(input);
		
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
		
	//others
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
