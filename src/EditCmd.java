import java.time.LocalDateTime;


public class EditCmd extends ModifiableCmd{
	
	private Task task;
	private int index;
	private int viewIndex = 0;
	
	private String description = null;
	private LocalDateTime start = null;
	private LocalDateTime end = null;
	private boolean isSomeday = false;
	
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
	
	//indicate: 1= start, 2= end
	public EditCmd(int index, LocalDateTime time, int indicate){
		this(index);
		if(indicate == 1){
			this.start = time;
			this.end = null;
			this.viewIndex = 4;
		}
		else{
			assert(indicate == 2);
			this.start = null;
			this.end = time;
			this.viewIndex = 5;
		}
	}
	
	public EditCmd(int index, LocalDateTime start, LocalDateTime end){
		this.start = start;
		this.end = end;
		this.viewIndex = 6;
	}
	
	//indicate: 1= start, 2= end
	public EditCmd(int index, String description, LocalDateTime time, int indicate){
		this(index, time, indicate);
		this.description = description;
		this.viewIndex += 3; //start = 7, end = 8
	}
	
	public EditCmd(int index, String description, LocalDateTime start, LocalDateTime end){
		this(index, description);
		this.start = start;
		this.end = end;
		this.viewIndex = 9;
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
		
		String editMessage;
		if(viewIndex == 2){
			display.setMessage(String.format(EDIT_DES_MESSAGE, description, task.getDescription()));
		}
		else if(viewIndex == 4){
			display.setMessage(String.format(EDIT_END_DATE_MESSAGE, end.toLocalDate() + " " + end.toLocalTime(), 
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
	
	private String getMessage(){
		String message = "";
		
		switch(viewIndex){
			case 1 :
				message = String.format(EDIT_TASK_TO_SOMEDAY_MESSAGE,description,getTaskType(task));
				break;
			case 2 :
				message = String.format(EDIT_DES_MESSAGE,task.getDescription(),description);
				break;
			case 3 :
				message = String.format(EDIT_TASK_DEC_AND_TO_SOMEDAY_MESSAGE,description,task.getDescription(),getTaskType(task));
				break;
			case 4 :
				message = String.format(EDIT_START_MESSAGE,task.getDescription(),task.getStart(),start);
				break;
			case 5 :
				message = String.format(EDIT_END_MESSAGE,task.getDescription(),task.getEnd());
				break;
			case 6 :
				message = String.format(EDIT_TIME_MESSAGE,task.getDescription(),task.getStart(),task.getEnd());
				break;
			case 7 :
				message = String.format(EDIT_DES_START_MESSAGE,description,task.getDescription(),task.getStart());
				break;				
			case 8 :
				message = String.format(EDIT_DES_END_MESSAGE,description,task.getDescription(),task.getEnd());
				break;
			case 9 :
				message = String.format(EDIT_DES_AND_DATETIME_MESSAGE,description,task.getDescription(),task.getStart(),task.getEnd());
				break;
		}
		
		return message;
	}
}
