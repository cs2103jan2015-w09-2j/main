import java.time.LocalDateTime;


public class EditCmd extends ModifiableCmd{
	
	private Task task;
	private Task editContent; 	//depreciated
	private int index;
	private int viewIndex;
	
	private String description = null;
	private LocalDateTime start = null;
	private LocalDateTime end = null;
	private boolean isSomeday = false;
	
	//depreciated
	public EditCmd(int index, Task editContent){
		this.index = index;
		this.editContent = editContent;
	}

	private EditCmd(int index){
		this.index = index;
	}
	
	public EditCmd(int index, boolean isSomeday){
		this(index);
		assert(isSomeday);
		this.isSomeday = isSomeday;
		this.viewIndex = 1;
	}
	
	public EditCmd(int index, String description){
		this(index);
		this.description = description;
		this.viewIndex = 2;
	}
	
	public EditCmd(int index, String description, boolean isSomeday){
		this(index, description);
		assert(isSomeday);
		this.isSomeday = isSomeday;
		this.viewIndex = 3;
	}
	
	//indicate: 1= start, 2= end, 3 = by
	public EditCmd(int index, LocalDateTime time, int indicate){
		this(index);
		if(indicate == 1){
			this.start = time;
			this.end = null;
		}
		else if(indicate == 2){
			this.start = null;
			this.end = time;
		}
		else{
			assert(indicate == 3);
			this.start = null;
			this.end = time;
		}
		this.viewIndex = 4;
	}
	
	public EditCmd(int index, LocalDateTime start, LocalDateTime end){
		this.start = start;
		this.end = end;
		this.viewIndex = 5;
	}
	
	//indicate: 1= start, 2= end
	public EditCmd(int index, String description, LocalDateTime time, int indicate){
		this(index, time, indicate);
		this.description = description;
		this.viewIndex = 6;
	}
	
	public EditCmd(int index, String description, LocalDateTime start, LocalDateTime end){
		this(index, description);
		this.start = start;
		this.end = end;
		this.viewIndex = 7;
	}
	
	public boolean execute() throws IndexOutOfBoundsException{
		
		task = getViewTask(index);
		
		//store current task in temp before edit
		String tempDescription = task.getDescription();
		LocalDateTime tempStart = task.getStart();
		LocalDateTime tempEnd = task.getEnd();
		
		if(isSomeday){
			data.update(task, description);
		}
		else{
			data.update(task, description, start, end);
		}
		writeToFile();
		
		//keep temp for undo
		description = tempDescription;
		start = tempStart;
		end = tempEnd;
		
		if(viewIndex == 2){
			display.setMessage(String.format(MESSAGE_EDIT_DES, description, task.getDescription()));
		}
		else if(viewIndex == 4){
			display.setMessage(String.format(MESSAGE_EDIT_DES, end.toLocalDate() + " " + end.toLocalTime(), 
					task.getEnd().toLocalDate() + " " + task.getEnd().toLocalTime()));
		}
		else{
			display.setMessage("");
		}
		
		return true;
	}
	
	public void undo(){
		data.update(task, description, start, end);
		writeToFile();
		
		display.setMessage("");
	}
		
	//depreciated
	private Task clone(Task task){
		Task clonedTask = new Task();
		clonedTask.setDescription(task.getDescription());
		clonedTask.setStart(task.getStart());
		clonedTask.setEnd(task.getEnd());
		clonedTask.setIsCompleted(task.getIsCompleted());
		
		return clonedTask;
	}
}
