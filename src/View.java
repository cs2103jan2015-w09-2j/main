
public abstract class View {

	private static View theOne;
	
	public static View getInstance(){
		if(theOne == null){
			theOne = new DateView();
		}
		return theOne;
	}
	
	public static void setInstance(View view){
		theOne = view;
	}
	
	abstract Task getTask(int index);

}
