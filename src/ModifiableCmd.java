import java.util.ArrayList;


public abstract class ModifiableCmd extends Cmd{
	
	public abstract void undo();
	
	protected void writeToFile(){
		Storage file = new Storage();
		
		ArrayList<Task> tasks = data.getData();
		file.writeToFile(tasks);
	}
}