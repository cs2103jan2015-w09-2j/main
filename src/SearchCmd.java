

public class SearchCmd extends Cmd{
	
	private String searchedText;
	private View preView;
	
	public SearchCmd(String searchedText){
		this.searchedText = searchedText;
		this.preView = display.getView();
	}
	
	public boolean execute(){
		if(data.getSearched(searchedText).isEmpty()){
			display.setMessage(MESSAGE_EMPTY_SEARCH);
		}
		
		display.setView(new SearchView(searchedText));
		
		return true;
	}
	
	public void undo(){
		display.setView(preView);
	}

}
