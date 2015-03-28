
public class EditCmd extends ModifiableCmd{
	
	private Task task;
	private Task editContent;
	private int index;
	
	public EditCmd(int index, Task editContent){
		View view = display.getView();
		
		this.task = view.getTask(index);
		this.editContent = editContent;
	}
	
	public boolean execute() throws IndexOutOfBoundsException{
		task = getViewTask(index);
		
		data.update(task, editContent);
		writeToFile();
		
		display.setMessage(MESSAGE_EDIT);
		
		return true;
	}
	
	public void undo(){
		System.out.println(task);
		System.out.println(editContent);
	}
}
