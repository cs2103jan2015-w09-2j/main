import java.util.ArrayList;

public class Data {

	ArrayList<Task> data;
	View view;
	
	//Constructor
	public Data(){
		data = new ArrayList<Task>();
		view = toDateView();
	}
	
	public Data(ArrayList<Task> data){
		this.data = data;
		view = toDateView();
	}
	
	//getter
	public ArrayList<Task> getData(){
		return data;
	}
	
	public View getView(){
		return view;
	}
	
	public Task getViewTask(int index){
		return view.getTask(index);
	}
	
	//setter
	public boolean add(Task input){
		boolean isAdded = data.add(input);
		
		toDateView();
		
		return isAdded;
	}
		
	public boolean remove(Task task){		
		boolean isRemoved = data.remove(task);
		
		toDateView();
		
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
	
	public DateView toDateView(){
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
		
		return new DateView(today, upcoming, someday);
	}
}
