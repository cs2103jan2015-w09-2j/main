
public class ViewCmd extends Cmd{

	String currView;
	View preView;
	
	public ViewCmd(String view) {
		this.currView = view.toLowerCase();
		this.preView = display.getView();
	}

	public boolean execute(){
		switch(currView){
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
	
	public void undo(){
		display.setView(preView);
	}

}
