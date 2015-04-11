import java.util.EmptyStackException;


public class UndoCmd extends Cmd {

	public UndoCmd(){
	}
	
	@Override
	public boolean execute() {
		History history = History.getInstance();
		
		try{
			Cmd cmd = history.remove();
			cmd.undo();
		}
		catch(EmptyStackException ex){
			display.setMessage(UNDO_EMPTY_MESSAGE);
		}
		return true;
	}
	
	@Override
	public boolean equals(Object o){
		return (o instanceof UndoCmd);
	}

}
