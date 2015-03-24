//@author A0111867A

public class Display {

	private static Display display = null;
	private View view;
	private String message;

	private Display(){
		view = null;
		message = "";
	}
	
	public static Display getInstance() {
		if (display == null) {
			display = new Display();
		}
		return display;
	}
	
	public View getView() {
		return view;
	}

	public String getMessage() {
		return message;
	}

	public void set(View view) {
		this.view = view;
		this.message = "";
	}
	
	public void set(View view, String message) {
		this.view = view;
		this.message = message;
	}

}
