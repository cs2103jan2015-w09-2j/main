import java.util.ArrayList;

import javax.swing.text.BadLocationException;


public class SearchView implements View{

	ArrayList<Task> searchedList;
	
	public SearchView(String searchedText){
		Data data = Data.getInstance();
		
		searchedList = data.getSearched(searchedText);
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() throws BadLocationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Task getTask(int index) {
		return searchedList.get(index);
	}

}
