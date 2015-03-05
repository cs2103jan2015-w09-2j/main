import java.util.Calendar;
import java.util.GregorianCalendar;

public class Task{
	
	private String description;
	private transient Calendar start;
	private transient Calendar end;
	
	public Task(int startYear, int startMonth, int startDay, int startHour, int startMin, int endYear, int endMonth, int endDate, int endHour, int endMin, String userDescription){
		description = userDescription;
		start = Calendar.getInstance();
		end = Calendar.getInstance();
		start.set(Calendar.YEAR, startYear);
		start.set(Calendar.MONTH, startMonth);
		start.set(Calendar.DAY_OF_MONTH, startDay);
		start.set(Calendar.HOUR, startHour);
		start.set(Calendar.MINUTE, startMin);
		end.set(Calendar.YEAR, startYear);
		end.set(Calendar.MONTH, startMonth);
		end.set(Calendar.DAY_OF_MONTH, startDay);
		end.set(Calendar.HOUR, startHour);
		end.set(Calendar.MINUTE, startMin);
	}
	
	public Task(){
		start = new GregorianCalendar();
		end = new GregorianCalendar();
	}
	
	public String getDescription(){
		return description;
	}
	
	public Calendar getStart(){
		return start;
	}
	
	public Calendar getEnd(){
		return end;
	}
	
	public void setStartYear(int year){
		
	}
	
	public void setStartDate(int date){
		
	}
	
	public void setStartTime(int startHour, int startMin){
		
	}
	
	public void setEndYear(int year){
		
	}
	
	public void setEndDate(int date){
		
	}
	
	public void setEndTime(int endHour, int endMin){
		
	}
	
	//Start of Chun How
	public void setDescription(String description){
		if(description != null){
			this.description = description;
		}
	}
	
	public void setStart(Calendar start){
		if(start != null){
			this.start = start;
		}
	}
	
	public void setEnd(Calendar end){
		if(end != null){
			this.end = end;
		}
	}
	
	public void update(Task task){
		String newDescription = task.getDescription();
		Calendar newStart = task.getStart();
		Calendar newEnd = task.getEnd();
		
		setDescription(newDescription);
		setStart(newStart);
		setEnd(newEnd);
	}
	//end of Chun How
	
	public static void main(String[] args) {
	}

	public String toString(){
		start = Calendar.getInstance();
		
		end = Calendar.getInstance();
		
		if (start.getTime() == null){
			return "[" + end.get(Calendar.HOUR_OF_DAY) + (end.get(Calendar.MINUTE)) + "] " +  description;
		}
		else{
			return "[" + start.get(Calendar.HOUR_OF_DAY) + (start.get(Calendar.MINUTE)) + "-" + end.get(Calendar.HOUR_OF_DAY) + end.get(Calendar.MINUTE) + "] " + description;
			
		}
		
		
	}
}

