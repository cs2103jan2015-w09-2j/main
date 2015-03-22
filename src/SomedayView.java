import java.util.Observable;
import java.util.Observer;

public class SomedayView extends View implements Observer{

	Data data = null;
	
	public SomedayView(Data data){
		this.data = data;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Task getTask(int index) {
		// TODO Auto-generated method stub
		return null;
	}

}
