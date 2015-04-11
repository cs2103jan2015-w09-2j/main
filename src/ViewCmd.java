
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
			display.set(new HomeView());
			break;
		case "today" :
			display.set(new TodayView());
			break;
		case "upcoming" :
			display.set(new UpcomingView());
			break;
		case "someday" :
			display.set(new SomedayView());
			break;
		case "done" :
			display.set(new CompletedView());
			break;
		case "help" :
			display.set(new HelpView());
			break;
		default :
			display.set(new HomeView());
			break;
	}	
	    return true;
	}
	
	public void undo(){
		display.set(preView);
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
