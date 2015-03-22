import java.util.Comparator;


class DataComparator implements Comparator<Task>{
	
	@Override
	public int compare(Task task1, Task task2){
		return task1.compareTo(task2);
	}

}
