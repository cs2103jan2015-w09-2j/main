//@author A0111867A

public class AddCmd extends ModifiableCmd{

	private Task task;
	
	public AddCmd(Task task){
		this.task = task;
	}
	
	/**
	 * Execute the command specified in this class
	 */
	public void execute(){	
		data.add(task);
		writeToFile();
	     
		String addMessage = String.format(ADD_TASK_MESSAGE, task.getDescription(), getTaskType(task));
	    display.set(addMessage, COMMAND_TYPE.ADD);
	}
	
	/**
	 * Undo the command previously executed by this class
	 */
	@Override
	public void undo(){
		data.remove(task);
		writeToFile();
		
		String undoMessage = String.format(UNDO_ADD_MESSAGE, task.getDescription());
	    display.set(undoMessage, COMMAND_TYPE.DELETE);
	}
	
	/**
	 * Indicates whether some other object is "equal to" this one.
	 * 
	 * @param o the reference object with which to compare.
	 */
	@Override
	public boolean equals(Object o){
		if(o instanceof AddCmd){
			AddCmd otherAddCmd = (AddCmd)o;
			return this.task.equals(otherAddCmd.task);
		}
		else{
			return false;
		}
	}
}
