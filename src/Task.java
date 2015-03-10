import java.time.LocalDateTime;
import java.time.LocalTime;

public class Task implements Comparable<Task>{

	private static final String EMPTY_STRING = "";
	private static final String DEADLINE_TASK = "[%1$s] " + "%2$s";
	private static final String NORMAL_TASK = "[%1$s - %2$s] %3$s";
	private static final int EQUAL = 0;
	private String description;
	private LocalDateTime start;
	private LocalDateTime end;

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

	public Task() {
		description = EMPTY_STRING;
		start = LocalDateTime.now();
		end = LocalDateTime.now();
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

	public LocalTime getStartTime() {
		return start.toLocalTime();
	}

	public LocalTime getEndTime() {
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
	    
	    if (!this.getDescription().equals(other.getDescription())){
	    	return false;
	    }
	    if (!this.getStart().equals(other.getStart())){
	    	return false;
	    }
	    if (!this.getEnd().equals(other.getEnd())){
	    	return false;
	    }
	    
	    return true;
	}
	
	public int compareTo(Task object){
		int compareDescription = description.compareTo(object.getDescription());
		int compareStart = start.compareTo(object.getStart());
		int compareEnd = end.compareTo(object.getEnd());
		
		if (compareStart == EQUAL){
			if (compareEnd == EQUAL){
				return compareDescription;
			}
			else{
				return compareEnd;
			}
		}
		else{
			return compareStart;
		}
	}
	
	public static void main(String[] args) {

	}

}
