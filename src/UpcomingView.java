import java.util.Observable;
import java.util.Observer;

public class UpcomingView implements View, Observer{

	Data data = null;
	
	public UpcomingView(Data data){
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
