import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Task implements Comparable<Task>{

	private static final String EMPTY_STRING = "";
	private static final String DEADLINE_TASK = "[%1$s] " + "%2$s";
	private static final String NORMAL_TASK = "[%1$s - %2$s] %3$s";
	private static final int BEFORE = -1;
	private static final int EQUAL = 0;
	private static final int AFTER = 1;
	private static final int UNKNOWN = 2;
	
	private String description;
	private LocalDateTime start = null;
	private LocalDateTime end = null;
	
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
	
	public String getDescription() {
		return description;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public LocalDateTime getEnd() {
		return end;
	}
	
	private LocalTime getStartTime(){
		return start.toLocalTime();
	}
	
	private LocalTime getEndTime(){
		return end.toLocalTime();
	}

	public void setDescription(String newDescription) {
		description = newDescription;
	}

	public void setStart(LocalDateTime newStart) {
		start = newStart;
	}

	public void setEnd(LocalDateTime newEnd) {
		end = newEnd;
	}

	public void update(Task newTask) {
		String newDescription = newTask.getDescription();
		LocalDateTime newStart = newTask.getStart();
		LocalDateTime newEnd = newTask.getEnd();

		setDescription(newDescription);
		setStart(newStart);
		setEnd(newEnd);
	}

	public boolean isFloatingTask() {

		if (start == null && end == null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isDeadlineTask() {
		if (start == null && end != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isNormalTask() {
		if (start != null && end != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isTodayTask(){
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
	
	public boolean isUpcomingTask(){
		if (!this.isSomedayTask() && !this.isTodayTask()){
			return true;
		}
		else{
			return false;
		}
	}

	public boolean isSomedayTask(){
		if (this.isFloatingTask()){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	
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
	
	public boolean equals(Object obj) {
	    
		if (obj == null) {
	        return false;
	    }
	    
	    if (getClass() != obj.getClass()) {
	        return false;
	    }
	    
	    final Task other = (Task) obj;
	    
	    if (this.isFloatingTask() && other.isFloatingTask()){
	    	return this.getDescription().equals(other.getDescription());
	    }
	    else if (this.isDeadlineTask() && other.isDeadlineTask()){
	    	return (this.getDescription().equals(other.getDescription()) && this.getEnd().equals(other.getEnd()));
	    }
	    else if (this.isNormalTask() && other.isNormalTask()){
	    	return (this.getDescription().equals(other.getDescription()) && this.getStart().equals(other.getStart()) && this.getEnd().equals(other.getEnd()));
	    }
	    
	    return false;
	}
	
	public int compareTo(Task object){
		
		if (this.equals(object)){
			return EQUAL;
		}
		
		if (this.isFloatingTask() && object.isFloatingTask()){
			return this.getDescription().compareTo(object.getDescription());
		}
		else if (this.isDeadlineTask() && object.isDeadlineTask()){
			if (this.getEnd().equals(object.getEnd())){
				return this.getDescription().compareTo(object.getDescription());
			}
			else{
				return this.getEnd().compareTo(object.getEnd());
				}
			}
		else if (this.isNormalTask() && object.isNormalTask()){
			if (this.getStart().equals(object.getStart())){
				if (this.getEnd().equals(object.getEnd())){
					return this.getDescription().compareTo(object.getDescription());
				}
				else{
					return this.getEnd().compareTo(object.getEnd());
				}
			}
			else{
				return this.getStart().compareTo(object.getStart());
			}
		}
		
		if (this.isFloatingTask() && object.isDeadlineTask()){
			return BEFORE;
		}
		else if (this.isFloatingTask() && object.isNormalTask()){
			return BEFORE;
		}
		else if (this.isDeadlineTask() && object.isNormalTask()){
			return BEFORE;
		}
		else if (this.isDeadlineTask() && object.isFloatingTask()){
			return AFTER;
		}
		else if (this.isNormalTask() && object.isFloatingTask()){
			return AFTER;
		}
		else if (this.isNormalTask() && object.isDeadlineTask()){
			return AFTER;
		}
		
		return UNKNOWN;

	}

}
