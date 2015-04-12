import java.util.EmptyStackException;
import java.util.Stack;

//@author A0111867A
public class History {

	private static History history = null;
	
	private Stack<Cmd> list;
	
	private History(){
		list = new Stack<Cmd>();
	}
	
	public static History getInstance(){
		if(history == null){
			history = new History();
		}
		return history;
	}
	
	public void add(Cmd cmd){
		list.push(cmd);
	}
	
	public Cmd remove() throws EmptyStackException{
		return list.pop();
	}
}
