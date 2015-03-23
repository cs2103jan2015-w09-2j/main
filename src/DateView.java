import java.util.ArrayList;


public class DateView extends View{
	
	private static DateView dateView = null;
	private ArrayList<Task> today;
	private ArrayList<Task> upcoming;
	private ArrayList<Task> someday;
	
	private DateView(){
		this.today = new ArrayList<Task>();
		this.upcoming = new ArrayList<Task>();
		this.someday = new ArrayList<Task>();
	}
	
	private DateView(ArrayList<Task> today, ArrayList<Task> upcoming, ArrayList<Task> someday) {
		this.today = today;
		this.upcoming = upcoming;
		this.someday = someday;
	}
	
	public static DateView getInstance(){
		if(dateView == null){
			dateView = new DateView();
		}
		return dateView;
	}
	
	public static void setInstance(ArrayList<Task> today, ArrayList<Task> upcoming, ArrayList<Task> someday){
		dateView = new DateView(today, upcoming, someday);
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
