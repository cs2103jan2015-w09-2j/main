//@author A0111867A

public class Display {

	private static Display display = null;
	private static View view;
	private static String message;

	public Display(){
		view = null;
		message = "";
	}
	
	public static Display getInstance() {
		if (display == null) {
			display = new Display();
		}
		return display;
	}
	
	public static View getView() {
		return view;
	}

	public static void setView(View view) {
		Display.view = view;
	}

	public static String getMessage() {
		return message;
	}

	public static void setMessage(String message) {
		Display.message = message;
	}

}
