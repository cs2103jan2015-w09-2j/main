import java.util.ArrayList;

public abstract class SingleView implements View{

	protected Data data = Data.getInstance();
	private ArrayList<Task> list;
	
	public ArrayList<Task> getList() {
		return list;
	}

	protected void setList(ArrayList<Task> list) {
		this.list = list;
	}

	public Task getTask(int index) throws IndexOutOfBoundsException{
		index--;
		
		return list.get(index);
	}
		
	public abstract void update();
}