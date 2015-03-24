import java.time.LocalDateTime;
import java.util.Comparator;


public class EndTimeComparator implements Comparator<Task>{

	@Override
	public int compare(Task task1, Task task2) {
		LocalDateTime end1 = task1.getEnd();
		LocalDateTime end2 = task2.getEnd();
		
		return end1.compareTo(end2);
	}
	
}
