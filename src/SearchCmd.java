

public class SearchCmd extends Cmd{
	
	private String searchedText;
	
	public SearchCmd(String searchedText){
		this.searchedText = searchedText;
	}
	
	public boolean execute(){

		display.setView(new SearchView(searchedText));
		
		return true;
	}
}
