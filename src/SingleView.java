import java.util.ArrayList;

public abstract class SingleView{

	protected Data data = Data.getInstance();
	
	private ArrayList<Task> list;
	
	public SingleView(){
		update();
	}
	
	protected ArrayList<Task> getList(){
		return this.list;
	}
	
	protected void setList(ArrayList<Task> list){
		this.list = list;
	}
	
	public Task getTask(int index) {
		index--;
		int size = list.size();
		
		if(index > -1 && index < size){
			return list.get(index);
		}
		else{
			return null;
		}
	}

	abstract void update();
}