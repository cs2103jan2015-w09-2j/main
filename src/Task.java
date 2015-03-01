import java.util.Calendar;


public class Task implements Comparable<Object> {
	
	String description;
	Calendar start;
	Calendar end;
	
	public Task(int startYear, int startMonth, int startDate, int startHour, int startMin, int endYear, int endMonth, int endDate, int endHour, int endMin, String userDescription){
		description = userDescription;
		start.set(startYear, startMonth, startDate, startHour, startMin);
		end.set(endYear, endMonth, endDate, endHour, endMin);
	}
	
	public Task(int startDate, int startHour, int startMin, int endDate, int endHour, int endMin, String userDescription){
		description = userDescription;
		start.set(startDate, startHour, startMin);
		end.set(endDate,endHour, endMin);
	}
	
	public Task(int startHour, int startMin, int endHour, int endMin, String userDescription){
		description = userDescription;
		start.set(startHour, startMin);
		end.set(endHour, endMin);
	}
	
	public Task(int endHour, int endMin, String userDescription){
		description = userDescription;
		end.set(endHour, endMin);
	}
	
	public Task(){
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	//@override
	public boolean equal(Task otherTask){
		boolean isEqualDescription = this.description.equals(otherTask.getDescription());
		boolean isEqualStart = this.start.equals(otherTask.getStart());
		boolean isEqualEnd = this.end.equals(otherTask.getEnd());
		
		if (isEqualDescription && isEqualStart && isEqualEnd){
			return true;
		}
		else{
			return false;
		}
	}
	
	@Override
	public int compareTo(Object o) {
		int equalDescription = this.description.compareTo(((Task) o).getDescription());
		int equalStart = this.start.compareTo(((Task) o).getStart());
		int equalEnd = this.end.compareTo(((Task) o).getEnd());
		
		int compareValue = equalDescription + equalStart + equalEnd;
		
	return compareValue;
	}
}
