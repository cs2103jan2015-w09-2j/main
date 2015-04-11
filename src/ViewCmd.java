
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
			display.setView(new HomeView());
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
			display.setView(new HelpView());
			break;
		default :
			display.setView(new HomeView());
			break;
	}	
	    return true;
	}
	
	public void undo(){
		display.setView(preView);
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof ViewCmd){
			ViewCmd otherViewCmd = (ViewCmd)o;
			return this.currView.equals(otherViewCmd.currView) && this.preView.equals(otherViewCmd.preView);
		}
		else{
			return false;
		}
	}

}
