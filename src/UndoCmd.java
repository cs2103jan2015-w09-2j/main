import java.util.EmptyStackException;

//@author A0111867A
public class UndoCmd extends Cmd {

	public UndoCmd(){
	}
	
	/**
	 * Execute the command specified in this class
	 */
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
	
	/**
	 * Indicates whether some other object is "equal to" this one.
	 * 
	 * @param o the reference object with which to compare.
	 */
	@Override
	public boolean equals(Object o){
		return (o instanceof UndoCmd);
	}

}
