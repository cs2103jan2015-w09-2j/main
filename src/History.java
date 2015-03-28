import java.util.EmptyStackException;
import java.util.Stack;


public class History {

	private static History history = null;
	
	private Stack<ModifiableCmd> list;
	
	private History(){
		list = new Stack<ModifiableCmd>();
	}
	
	public static History getInstance(){
		if(history == null){
			history = new History();
		}
		return history;
	}
	
	public void add(ModifiableCmd cmd){
		list.push(cmd);
	}
	
	public ModifiableCmd remove() throws EmptyStackException{
		return list.pop();
	}
}
