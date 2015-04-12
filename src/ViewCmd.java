
public class ViewCmd extends Cmd{

	final static int PAGE_ONE = 1;
	COMMAND_TYPE command;
	View preView;
	int paging;
	
	public ViewCmd(COMMAND_TYPE command) {
		this.command = command;
		this.preView = display.getView();
		this.paging = PAGE_ONE;
	}
	
	public ViewCmd(COMMAND_TYPE command, int paging) {
		this.command = command;
		this.preView = display.getView();
		this.paging = paging;
	}

	/**
	 * Execute the command specified in this class
	 */
	public void execute(){
		this.preView = display.getView();

		switch(command){
			case HOME :
				display.set(new HomeView(), paging); 
				break;
			case TODAY :
				display.set(new TodayView(), paging);
				break;
			case UPCOMING :
				display.set(new UpcomingView(), paging);
				break;
			case SOMEDAY :
				display.set(new SomedayView(), paging);
				break;
			case DONE :
				display.set(new CompletedView(), paging);
				break;
			case HELP :
				display.set(new HelpView());
				break;
			default :
				display.set(new HomeView());
				break;
		}	
	}
	
	/**
	 * Undo the command previously executed by this class
	 */
	@Override
	public void undo(){
		display.set(preView, paging);
	}
	
	/**
	 * Indicates whether some other object is "equal to" this one.
	 * 
	 * @param o the reference object with which to compare.
	 */
	@Override
	public boolean equals(Object o){
		if(o instanceof ViewCmd){
			ViewCmd otherViewCmd = (ViewCmd)o;
			return this.command.equals(otherViewCmd.command) && this.preView.equals(otherViewCmd.preView)
					&& this.paging == otherViewCmd.paging;
		}
		else{
			return false;
		}
	}

}
