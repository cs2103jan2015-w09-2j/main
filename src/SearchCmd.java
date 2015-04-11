

public class SearchCmd extends Cmd{
	
	private String searchedText = "";
	private View preView;
	
	public SearchCmd(String searchedText){
		this.searchedText = searchedText;
		this.preView = display.getView();
	}
	
	public boolean execute(){	
		if(data.getSearched(searchedText).isEmpty()){
			display.set(SEARCH_KEYWORD_IS_EMPTY);
		}
		
		display.set(new SearchView(searchedText));
		
		return true;
	}
	
	public void undo(){
		display.set(preView);
	}
	
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
