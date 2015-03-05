
public class Controller {
			
	private Data myList;
	private Storage file;
	
	public Controller(){
		file = new Storage();
		myList = new Data(file.getData());
	}
		
	public Display executeCommand(String input) {
		Cmd cmd = Parser.toCmd(input);
		return cmd.execute(myList);
	}

}