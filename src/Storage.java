import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import com.google.gson.Gson;


public class Storage {
	
	private static final String USER_DIRECTORY = "user.dir";
	private static final String CHARACTER_BACKSLASH = "//";
	private static final String MESSAGE_NEW_USER_DIRECTORY = "Directory has been set to %1$s";
	private static final String MESSAGE_NEW_FILE_NAME = "File name has been set to %1$s";
	
	private static final String MESSAGE_ERROR_FILE_NOT_FOUND = "%1$s is not found!\r\n";
	private static final String CHARACTER_EMPTY_STRING = "";
	private String fileName = "oneTag.json";  //default name is oneTag.json
	private String filePath;
	
	private ArrayList<Task> allTasks;
	
	
	public Storage(){
		String currentRelativePath = System.getProperty(USER_DIRECTORY);
		filePath = currentRelativePath + CHARACTER_BACKSLASH + fileName;
		allTasks = new ArrayList<Task>();
		checkFileExist();
	}
	
	public Storage(String directory, ArrayList<Task> task){
		filePath = directory;
		allTasks = task;
		checkFileExist();
	}
	
	public Storage(String directory, String name, ArrayList<Task> task ){
		fileName = name;
		filePath = directory + CHARACTER_BACKSLASH + fileName;
		allTasks = task;
		checkFileExist();
	}
	
	public String setPath(String newDirectory){
		filePath = newDirectory + CHARACTER_BACKSLASH + fileName;
		
		return String.format(MESSAGE_NEW_USER_DIRECTORY, newDirectory);
	}
	
	public String setFileName(String name){
		fileName = name;
		
		return String.format(MESSAGE_NEW_FILE_NAME, name);
	}
	
	public void writeToFile(ArrayList<Task> tasks)throws Exception{
		
		Gson gson = new Gson();
		String json = gson.toJson(tasks);
		PrintWriter pw;
		try{
			pw = new PrintWriter(filePath);
			pw.write(json);;	
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
		pw.close();
	}
	
	@SuppressWarnings("resource")
	public ArrayList<Task> getData(){
		
		String jsonString = new String("");		
		BufferedReader br;
		
		try{
			String line;
			br = new BufferedReader(new FileReader(filePath));
			
			while((line = br.readLine()) != null){
				jsonString += line;
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return allTasks;
		
	}
	
	
	public String checkFileExist() {

		File file = new File(this.filePath);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				return String.format(MESSAGE_ERROR_FILE_NOT_FOUND,
						fileName);
			}
		}
		return CHARACTER_EMPTY_STRING;
	}

	
	
	public static void main(String[] args) throws Exception{
		Storage storage = new Storage();
		storage.checkFileExist();
		
		Task taskOne = new Task(2015,2,0, 15, 21, 2015, 2, 0, 15,21, "Do something");
		Task taskTwo = new Task(2015,2,0, 15, 21, 2015, 2, 0, 15,21, "Do Other Thing");
		ArrayList<Task> testing = new ArrayList<Task>();
		testing.add(taskOne);
		testing.add(taskTwo);
		storage.writeToFile(testing);
		storage.getData();

	}

}


