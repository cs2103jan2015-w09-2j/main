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
	
	/**
	 * Execute the command specified in this class
	 */
	public void execute() throws IndexOutOfBoundsException{
		
		task = getViewTask(index);
		
		//store current task in temp before edit
		String tempDescription = task.getDescription();
		LocalDateTime tempStart = task.getStart();
		LocalDateTime tempEnd = task.getEnd();
		
		if(isSomeday){
			if(description != null){
				task.setDescription(description);
			}
			task.setStart(null);
			task.setEnd(null);
		}
		else{
			 update();
		}
		writeToFile();
		
		//keep temp for undo
		description = tempDescription;
		start = tempStart;
		end = tempEnd;
		
		display.set(getMessage());
	}
	
	/**
	 * Undo the command previously executed by this class
	 */
	@Override
	public void undo(){
		update();
		writeToFile();
		
		display.set(getMessage());
	}
	
	//update the field of task if it is not null
	private void update(){
		if(description != null){
			task.setDescription(description);
		}
		if(start != null){
			task.setStart(start);
		}
		if(end != null){
			task.setEnd(end);
		}
	}
	
	/**
	 * Indicates whether some other object is "equal to" this one.
	 * 
	 * @param o the reference object with which to compare.
	 */
	@Override
	public boolean equals(Object o){
		if(o instanceof EditCmd){
			EditCmd otherEditCmd = (EditCmd)o;
			
			Boolean isSameTask = this.task.equals(otherEditCmd.task);
			Boolean isSameIndex = this.index == otherEditCmd.index;
			Boolean isSameViewIndex = this.viewIndex == otherEditCmd.viewIndex;
			Boolean isSameDescription = this.description.equals(otherEditCmd.description);
			Boolean isSameStart = this.start.equals(otherEditCmd.start);
			Boolean isSameEnd = this.end.equals(otherEditCmd.end);
			Boolean isSameIsSomeday = this.isSomeday == otherEditCmd.isSomeday;
			
			return isSameTask && isSameIndex && isSameViewIndex && isSameDescription 
					&& isSameStart && isSameEnd && isSameIsSomeday;
		}
		else{
			return false;
		}
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
