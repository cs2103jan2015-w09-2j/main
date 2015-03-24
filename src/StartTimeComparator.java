import java.time.LocalDateTime;
import java.util.Comparator;


public class StartTimeComparator implements Comparator<Task>{

	@Override
	public int compare(Task task1, Task task2) {
		LocalDateTime start1 = task1.getStart();
		LocalDateTime start2 = task2.getStart();
		
		return start1.compareTo(start2);
	}
	
}
