import java.util.EmptyStackException;


public class UndoCmd extends Cmd {

	public UndoCmd(){
	}
	
	@Override
	public void execute() {
		History history = History.getInstance();
		
		try{
			Cmd cmd = history.remove();
			cmd.undo();
		}
		catch(EmptyStackException ex){
			display.set(UNDO_EMPTY_MESSAGE);
		}
	}
	
	@Override
	public boolean equals(Object o){
		return (o instanceof UndoCmd);
	}

}
