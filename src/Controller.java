
public class Controller {
			
	private static Controller controller = null;
	private Storage file;
	
	public Controller(){
		file = new Storage();
		Data data = Data.getInstance();
		DateView dateView = DateView.getInstance();
		
		data.set(file.getData());	
				
		System.out.println(data.getSomeday());
		dateView.set(data.getToday(), data.getUpcoming(), data.getSomeday());
	}
		
	public static Controller getInstance(){
		if(controller == null){
			controller = new Controller();
		}
		return controller;
	}
	
	public Display executeCommand(String input) {
		Cmd cmd = OneTagParser.toCmd(input);
		return cmd.execute();
	}

}