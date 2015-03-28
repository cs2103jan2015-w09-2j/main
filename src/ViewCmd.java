
public class ViewCmd extends Cmd{

	String view;
	
	public ViewCmd(String view) {
		this.view = view.toLowerCase();
	}

	public boolean execute(){
		Display display = Display.getInstance();
		
		switch(view){
			case "home" :
				display.setView(new DateView());
				break;
			case "today" :
				display.setView(new TodayView());
				break;
			case "upcoming" :
				display.setView(new UpcomingView());
				break;
			case "someday" :
				display.setView(new SomedayView());
				break;
			case "done" :
				display.setView(new CompletedView());
				break;
			case "help" :
				break;
			default :
				display.setView(new DateView());
				break;
		}
		
	     return true;
	}

}
