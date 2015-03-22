import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Task implements Comparable<Task>{

	private static final String EMPTY_STRING = "";
	private static final String DEADLINE_TASK = "[%1$s] " + "%2$s";
	private static final String NORMAL_TASK = "[%1$s - %2$s] %3$s";
	
	private String description;
	private LocalDateTime start = null;
	private LocalDateTime end = null;
	private boolean isCompleted = false;
	
	// Timed Task
	public Task(int startYear, int startMonth, int startDay, int startHour,
			int startMin, int endYear, int endMonth, int endDay, int endHour,
			int endMin, String taskDescription) {
		description = taskDescription;
		start = LocalDateTime.of(startYear, startMonth, startDay, startHour,
				startMin);
		end = LocalDateTime.of(endYear, endMonth, endDay, endHour, endMin);
	}

	// Floating task
	public Task(String taskDescription) {
		description = taskDescription;
		start = null;
		end = null;
	}

	// Deadline task
	public Task(int endYear, int endMonth, int endDay, int endHour, int endMin,
			String taskDescription) {
		description = taskDescription;
		end = LocalDateTime.of(endYear, endMonth, endDay, endHour, endMin);
	}
	
	// Used internally by TaskSerializer
	public Task(){
		
	}
	
	/**
	 * Returns the description of Task object
	 * @return String
	 */
	public String getDescription() {
		return description;
	}	
	
	/**
	 * Returns the start date and time of Task object
	 * @return LocalDateTime
	 */
	public LocalDateTime getStart() {
		return start;
	}

	/**
	 * Returns the end date and time of Task object
	 * @return LocalDateTime
	 */
	public LocalDateTime getEnd() {
		return end;
	}
	
	/**
	 * Returns the start time of Task object
	 * @return LocalTime
	 */
	private LocalTime getStartTime(){
		return start.toLocalTime();
	}
	
	/**
	 * Returns the end time of Task object
	 * @return LocalTime
	 */
	private LocalTime getEndTime(){
		return end.toLocalTime();
	}
	
	/**
	 * Returns whether the task is completed
	 * @return boolean
	 */
	public boolean getIsCompleted(){
		return isCompleted;
	}
	
	/**
	 * Sets the description of Task object
	 * @param newDescription Sets the description of Task object
	 */
	public void setDescription(String newDescription) {
		description = newDescription;
	}
	
	/**
	 * Sets the start date and time of Task object
	 * @param newStart sets the start date and time of Task object
	 */
	public void setStart(LocalDateTime newStart) {
		start = newStart;
	}
	
	/**
	 * Sets the end date and time of Task object
	 * @param newEnd sets the end date and time of Task object
	 */
	public void setEnd(LocalDateTime newEnd) {
		end = newEnd;
	}
	
	/**
	 * Sets the task as completed
	 */
	public void setIsCompleted(boolean isCompleted){
		this.isCompleted = isCompleted;
	}
	
	/**
	 * Updates the fields of the current object with the fields of the newTask
	 * @param newTask updates the fields of the current object with the fields of the newTask
	 */
	public void update(Task otherTask) {
		String otherDescription = otherTask.getDescription();
		LocalDateTime otherStart = otherTask.getStart();
		LocalDateTime otherEnd = otherTask.getEnd();
		boolean isCompletedOther = otherTask.getIsCompleted();

		setDescription(otherDescription);
		setStart(otherStart);
		setEnd(otherEnd);
		setIsCompleted(isCompletedOther);
	}
	
	/**
	 * Returns true if task is a floating task, false otherwise
	 * @return true if task is a floating task, false otherwise
	 */
	public boolean isFloatingTask() {
		
		if (start == null && end == null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns true if task is a deadline task, false otherwise
	 * @return true if Task is a deadline task, false otherwise
	 */
	public boolean isDeadlineTask() {

		if (start == null && end != null) {
			return true;
		} else {
			return false;
		}
	}
	
	/** 
	 * Returns true if task is a timed task, false otherwise
	 * @return true if Task is a timed task, false otherwise
	 */
	public boolean isNormalTask() {
	
		if (start != null && end != null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns true if deadline task's end field is today or if timed task's start field is today, false otherwise
	 * @return true if deadline task's end field is today or if timed tasks's start field is today, false otherwise
	 */
	public boolean isTodayTask(){
		if (this.isCompleted){
			return false;
		}
		
		LocalDate now = LocalDate.now();
		
		if (this.isFloatingTask()){
			return false;
		}else if (this.isDeadlineTask()){
			if (this.end.toLocalDate().equals(now)){
				return true;
			}
			else{
				return false;
			}
		}
		else if (this.isNormalTask()){
			if (this.start.toLocalDate().equals(now)){
				return true;
			}
			else{
				return false;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns true if task is not some day's task and not today's task, false otherwise
	 * @return true if task is not some day's task and not today's task, false otherwise
	 */
	public boolean isUpcomingTask(){	
		if (this.isCompleted){
			return false;
		}
		if (!this.isSomedayTask() && !this.isTodayTask()){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Returns true if task is a floating task
	 * @return true if task is a floating task
	 */
	public boolean isSomedayTask(){
		if (this.isCompleted){
			return false;
		}
		if (this.isFloatingTask()){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Returns a String representation of this object
	 * @returns each task with a specified format - floating task - "Description", Deadline Task - [end time] "Description" Timed task - [start time - end time] "Description
	 *
	 */
	public String toString() {
		String toPrint = EMPTY_STRING;
		if (isFloatingTask()) {
			toPrint = description;
		} else if (isDeadlineTask()) {
			toPrint = String.format(DEADLINE_TASK, getEndTime().toString(),
					description);
		} else if (isNormalTask()) {
			toPrint = String.format(NORMAL_TASK, getStartTime().toString(),
					getEndTime().toString(), description);
		}

		return toPrint;
	}
	
	/**
	 * Returns true if some other object is "equal to" this one, false otherwise
	 * @return true if some other object is "equal to" this one, false otherwise
	 */
	public boolean equals(Object obj) {
	    boolean isNullObj = (obj == null);
		
		if (isNullObj) {
	        return false;
	    }
		
		assert !isNullObj;
		
	    if (getClass() != obj.getClass()) {
	        return false;
	    }
	    
	    assert this.getClass() == obj.getClass();
	    
	    
	    final Task other = (Task) obj;
	    
	    if (this.isCompleted == other.isCompleted){
	    
	    if (this.isFloatingTask() && other.isFloatingTask()){
	    	return this.getDescription().equals(other.getDescription());
	    }
	    else if (this.isDeadlineTask() && other.isDeadlineTask()){
	    	return (this.getDescription().equals(other.getDescription()) && this.getEnd().equals(other.getEnd()));
	    }
	    else if (this.isNormalTask() && other.isNormalTask()){
	    	return (this.getDescription().equals(other.getDescription()) && this.getStart().equals(other.getStart()) && this.getEnd().equals(other.getEnd()));
	    }
	    
	    }
	    else{
	    	return false;
	    }
	    return false;
	}
	
	//@author A0111867A
	@Override
	public int compareTo(Task task) {
		
		return compareCompleted(task) + compareFloating(task) + compareTimed(task);
		
	}

	private int compareTimed(Task task) {
		if(!this.start.equals(task.start) && !this.end.equals(task.end)){
			return this.start.compareTo(task.start);
		}
		
		else if(this.start.equals(task.start) && !this.end.equals(task.end)){
			return this.end.compareTo(task.end);
		}
		else{
			return 0;
		}
	}

	private int compareFloating(Task task) {
		if(this.isFloatingTask() && !task.isFloatingTask()){
			return -1;
		}
		else if(!this.isFloatingTask() && task.isFloatingTask()){
			return 1;
		}
		else{
			return 0;
		}
	}

	private int compareCompleted(Task task) {
		if(this.isCompleted && !task.isCompleted){
			return -1;
		}
		else if(!this.isCompleted && task.isCompleted){
			return 1;
		}
		else{
			return 0;
		}
	}

}
