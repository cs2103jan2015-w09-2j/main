
public class UndoCmd extends Cmd {

	public UndoCmd(){
	}
	
	@Override
	public boolean execute() {
		History history = History.getInstance();
		
		ModifiableCmd cmd = history.remove();
		cmd.undo();
		return true;
	}

}
