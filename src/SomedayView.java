import java.util.ArrayList;

import javax.swing.text.BadLocationException;


public class SomedayView implements View{

	ArrayList<Task> someday;
	
	public SomedayView(){
		update();
	}
	
	@Override
	public Task getTask(int index) {
		return someday.get(index);
	}

	@Override
	public void update() {
		Data data = Data.getInstance();
		
		someday = data.getSomeday();
	}

	@Override
	public void show() throws BadLocationException {
		// TODO Auto-generated method stub
		
	}

}
