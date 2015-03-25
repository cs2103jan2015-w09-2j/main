

public class SearchCmd extends Cmd{
	
	String searchedText;
	
	public SearchCmd(String searchedText){
		this.searchedText = searchedText;
	}
	
	public boolean execute(){
		Display display = Display.getInstance();
		
		display.setView(new SearchView(searchedText));
		
		return true;
	}
}
