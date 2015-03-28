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
	
	public Task getTask(int index) throws IndexOutOfBoundsException{
		index--;
		
		return list.get(index);
	}

	abstract void update();
}