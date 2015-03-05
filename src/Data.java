import java.util.ArrayList;

public class Data {

	ArrayList<Task> data;
	ArrayList<Task> view;
	
	public Data(){
		data = new ArrayList<Task>();
		view = new ArrayList<Task>();
	}
	
	public Data(ArrayList<Task> data){
		this.data = data;
		view = new ArrayList<Task>();
	}
	
	public ArrayList<Task> getData(){
		return data;
	}
	
	public ArrayList<Task> getView(){
		return view;
	}
		
	public boolean add(Task input){
		boolean isAdded = data.add(input);
		
		view = data;
		
		return isAdded;
	}
		
	public boolean remove(Task task){		
		boolean isRemoved = data.remove(task);
		
		view = data;
		
		return isRemoved;
	}
	
	public Task getViewTask(int index){
		return view.get(index);
	}
	
	public void clear(){
		data.clear();
		view.clear();
	}
	
	public boolean isEmpty(){
		return data.size() <= 0;
	}

}
