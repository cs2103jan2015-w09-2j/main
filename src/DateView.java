import java.util.ArrayList;


public class DateView implements View{

	ArrayList<Task> today;
	ArrayList<Task> upcoming;
	ArrayList<Task> someday;
	
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
	
	public Task getTask(int index){
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
