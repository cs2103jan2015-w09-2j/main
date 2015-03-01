import java.util.ArrayList;

public class Data {

	ArrayList<Task> data;
	
	public Data(){
		data = new ArrayList<Task>();
	}
	
	public Data(ArrayList<Task> data){
		this.data = data;
	}
	
	public ArrayList<Task> getData(){
		return data;
	}
	
	public void add(Task input){
		data.add(input);
	}
	
	public Task remove(int deleteNum){
		int deleteIndex = deleteNum - 1;
		
		return data.remove(deleteIndex);
	}
	
	public void clear(){
		data.clear();
	}
	
	public boolean isEmpty(){
		return data.size() <= 0;
	}
		
	public String toString() {
		String output = "";
		
		int numbering = 0;
		
		for(int i = 0; i < data.size(); i++){
			numbering = i + 1;
			output += numbering + ". " + data.get(i);
			
			int lastIndex = data.size() - 1;
			if(i != lastIndex){
				output += "\n";
			}
		}
		
		return output;
	}
}
