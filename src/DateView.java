import java.util.ArrayList;


public class DateView extends View{

	ArrayList<Task> today;
	ArrayList<Task> upcoming;
	ArrayList<Task> someday;
	
	public DateView(){
		this.today = null;
		this.upcoming = null;
		this.someday = null;
	}
	
	public DateView(ArrayList<Task> today, ArrayList<Task> upcoming, ArrayList<Task> someday) {
		this.today = today;
		this.upcoming = upcoming;
		this.someday = someday;
	}

	public ArrayList<Task> getToday() {
		return today;
	}

	public ArrayList<Task> getUpcoming() {
		return upcoming;
	}

	public ArrayList<Task> getSomeday() {
		return someday;
	}
	
	public Task getTask(int numbering){
		int index = numbering - 1;
		
		int todaySize = today.size();
		int dateSize = todaySize + upcoming.size();
		int allSize = dateSize + someday.size();
		
		if(index > -1 && index < todaySize){
			return today.get(index);
		}
		else if(index < dateSize){
			return upcoming.get(index);
		}
		else if(index < allSize){
			return someday.get(index);
		}
		else{
			return new Task();
		}
	}
	
}
