import java.util.Observable;
import java.util.Observer;

import javax.swing.text.BadLocationException;

public class TodayView implements View{

	Data data = null;
	
	public TodayView(Data data){
		this.data = data;
	}

	@Override
	public Task getTask(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() throws BadLocationException {
		// TODO Auto-generated method stub
		
	}

}
