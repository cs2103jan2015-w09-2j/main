
public class Controller {
			
	private Storage file;
	
	public Controller(){
		file = new Storage();
		Data.setInstance(file.getData());
	}
		
	public Display executeCommand(String input) {
		Cmd cmd = Parser.toCmd(input);
		return cmd.execute();
	}

}