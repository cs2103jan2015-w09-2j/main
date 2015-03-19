//@author A0112715R
import java.util.ArrayList;

public class Display {

	private static DateView viewsOfDays;
	private static String message;
	private int i;
	private static Display display = new Display();

	public static Display getInstanceOfDisplay() {
		if (display == null) {
			display = new Display();
		}
		return display;

	}

	protected Display(View dateView, String msg) {
		viewsOfDays = (DateView) dateView;
		message = msg;

	}

	public Display() {
		// TODO Auto-generated constructor stub
	}

	protected String getToday() {
		String tasksForToday = getTask(viewsOfDays.getToday());
		return tasksForToday;
	}

	protected String getUpcoming() {
		String upcomingTasks = getTask(viewsOfDays.getUpcoming());
		return upcomingTasks;
	}

	protected String getSomeday() {
		String tasksForSomeday = getTask(viewsOfDays.getSomeday());
		return tasksForSomeday;

	}

	protected String getTask(ArrayList<Task> taskArray) {
		String tasks = "";
		for (Task task : taskArray) {
			i++;
			tasks += i + "." + task.toString() + "\n";
		}
		return tasks;
	}

	protected String getMessage() {
		return message;
	}

}
