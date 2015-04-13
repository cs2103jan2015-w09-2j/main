
//@author A0111867A
public class SearchCmd extends Cmd{
	
	private String searchedText = "";
	private View preView;
	
	public SearchCmd(String searchedText){
		this.searchedText = searchedText;
		this.preView = display.getView();
	}
	
	/**
	 * Execute the command specified in this class
	 */
	public void execute(){	
		if(data.getSearched(searchedText).isEmpty()){
			display.set(SEARCH_KEYWORD_IS_EMPTY);
		}
		preView = display.getView();
		display.set(new SearchView(searchedText));
	}
	
	/**
	 * Undo the command previously executed by this class
	 */
	@Override
	public void undo(){
		display.set(preView);
	}
	
	/**
	 * Indicates whether some other object is "equal to" this one.
	 * 
	 * @param o the reference object with which to compare.
	 */
	@Override
	public boolean equals(Object o){
		if(o instanceof SearchCmd){
			SearchCmd otherSearchCmd = (SearchCmd)o;
			return this.searchedText.equals(otherSearchCmd.searchedText) && this.preView.equals(otherSearchCmd.preView);
		}
		else{
			return false;
		}
	}

}
