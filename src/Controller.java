
public class Controller {
			
	private Storage file;
	
	public Controller(){
		file = new Storage();
		Data.setInstance(file.getData());
		
		Data myList = Data.getInstance();
		
		DateView.setInstance(myList.getToday(), myList.getUpcoming(), myList.getSomeday());
	}
		
	public Display executeCommand(String input) {
		Cmd cmd = OneTagParser.toCmd(input);
		return cmd.execute();
	}

}