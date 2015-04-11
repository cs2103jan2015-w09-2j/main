//@author A0111867A

public class Display {

	final static int DEFAULT_PAGE = 1;
	final static int INVALID_INDEX = -1;
	
	private static Display display = null;
	
	private View view; 	           //current view shown to user
	private String message;        //current message to be displayed to user
	private int viewIndex;         //index of task modified 
	private COMMAND_TYPE command;  //current command executed
	private int paging;            //current page the user is viewing   

	//Constructor
	private Display(){
		this.view = new HomeView();	
		this.message = "";
		this.viewIndex = INVALID_INDEX;
		this.command = COMMAND_TYPE.HOME;
		this.paging = DEFAULT_PAGE;
	}
	
	/**
	 * Returns an instance of this class
	 */
	public static Display getInstance() {
		if (display == null) {
			display = new Display();
		}
		return display;
	}
	
	/**
	 * Returns the reference of the current view
	 */
	public View getView() {
		return view;
	}

	/**
	 * Returns the reference of the current message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Returns the reference of the current view index
	 */
	public int getViewIndex() {
		return viewIndex;
	}

	/**
	 * Returns the reference of the current command type
	 */
	public COMMAND_TYPE getCommand(){
		return command;
	}
	
	/**
	 * Returns the reference of the current paging
	 */
	public int getPaging(){
		return paging;
	}
	
	/**
	 * Replaces the target field with the specified element.
	 * 
	 * @param view current view shown to user
	 */
	public void set(View view) {
		set(view, 1);
	}
	
	/**
	 * Replaces the target field with the specified element.
	 * 
	 * @param view current view shown to user
	 * @param paging current page the user is viewing
	 */
	public void set(View view, int paging) {
		view.update();
		this.view = view;
		this.paging = paging;
	}
	
	/**
	 * Replaces the target field with the specified element.
	 * 
	 * @param message current message to be displayed to user
	 */
	public void set(String message) {
		this.message = message;
		this.view.update();
	}
	
	/**
	 * Replaces the target field with the specified element.
	 * 
	 * @param message current message to be displayed to user
	 * @param command current command executed
	 */
	public void set(String message, COMMAND_TYPE command){
		set(message, -1, command);
	}
	
	/**
	 * Replaces the target field with the specified element.
	 * 
	 * @param message current message to be displayed to user
	 * @param viewIndex index of task modified 
	 * @param command current command executed
	 */
	public void set(String message, int viewIndex, COMMAND_TYPE command){
		this.message = message;
		this.viewIndex = viewIndex;
		this.command = command;
		this.view.update();
	}

}
