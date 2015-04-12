import java.util.EmptyStackException;
import java.util.Stack;

//@author A0111867A
public class History {

	private static History history = null;
	
	private Stack<Cmd> list;
	
	private History(){
		list = new Stack<Cmd>();
	}
	
	/**
	 * Returns an instance of this class
	 */
	public static History getInstance(){
		if(history == null){
			history = new History();
		}
		return history;
	}
	
	/**
	 * Looks at the object at the top of history without removing it from the history.
	 */
	public Cmd peep() throws EmptyStackException{
		return list.peek();
	}
	
	/**
	 * Appends the specified cmd to the start of this list.
	 * 
	 * @param cmd cmd to be appended to this list
	 */
	public void add(Cmd cmd){
		list.push(cmd);
	}
	
	/**
	 * Removes the cmd at the start of this list.
	 */
	public Cmd remove() throws EmptyStackException{
		return list.pop();
	}
}
