//@author A0111867A

public class Display {

	private static Display display = null;
	private View view;
	private String message;
	private int viewIndex;
	private COMMAND_TYPE command;
	private int paging;
	
	private Display(){
		this.view = new HomeView();	
		this.message = "";
		this.viewIndex = -1;
		this.command = COMMAND_TYPE.HOME;
		this.paging = 1;
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
	 * Returns the reference of the current command type
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
	 * @param view current view shown to user
	 * @param command current command executed
	 */
	public void set(String message, int viewIndex, COMMAND_TYPE command){
		this.message = message;
		this.viewIndex = viewIndex;
		this.command = command;
		this.view.update();
	}

}
