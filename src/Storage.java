/*
 * @author: Ng Zhi Hua
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


public class Storage {
	
	private static final String USER_DIRECTORY = "user.dir";
	private static final String CHARACTER_BACKSLASH = "//";
	private static final String MESSAGE_NEW_USER_DIRECTORY = "Directory has been set to %1$s";
	private static final String MESSAGE_NEW_FILE_NAME = "File name has been set to %1$s";
	
	private static final String MESSAGE_ERROR_FILE_NOT_FOUND = "%1$s is not found!\r\n";
	private static final String CHARACTER_EMPTY_STRING = "";
	
	private String fileName = "oneTag.json";  //default name is oneTag.json
	private ArrayList<Task> allTasks;
	private String currentRelativePath = System.getProperty(USER_DIRECTORY);
	private String filePath = currentRelativePath + CHARACTER_BACKSLASH + fileName;
	
	
	public Storage(){
		allTasks = new ArrayList<Task>();
		checkFileExist(this.filePath);
	}
	
	public Storage(ArrayList<Task> task){
		allTasks = task;
		checkFileExist(this.filePath);
	}
	
	public Storage(String directory, ArrayList<Task> task){
		filePath = directory;
		allTasks = task;
		checkFileExist(this.filePath);
	}
	
	public Storage(String directory, String fileName, ArrayList<Task> task ){
		this.fileName = fileName;
		filePath = directory + CHARACTER_BACKSLASH + fileName;
		allTasks = task;
		checkFileExist(this.filePath);
	}
	
	/**
	 * Changes the directory to specified directory
	 */
	public String setPath(String newDirectory){
		filePath = newDirectory + CHARACTER_BACKSLASH + fileName;
		
		return String.format(MESSAGE_NEW_USER_DIRECTORY, newDirectory);
	}
	
	
	/**
	 * Changes the file name to name
	 * @param name
	 * @return String
	 */
	public String setFileName(String name){
		fileName = name;
		
		return String.format(MESSAGE_NEW_FILE_NAME, name);
	}
	
	
	/**
	 * Writes the ArrayList<Task> to file
	 * @param tasks
	 */
	public void writeToFile(ArrayList<Task> tasks){
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting().registerTypeAdapter(Task.class, new TaskSerializer());
		Gson gson = gsonBuilder.create();
		String json = gson.toJson(tasks);
		
		FileWriter fw;
		try{
			fw = new FileWriter(filePath);
			fw.write(json);;
			fw.flush();
			fw.close();
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
		
		
	}
	
	/**
	 * Gets the list of tasks from file, puts them into an ArrayList
	 * @return ArrayList<Task>
	 */
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
		
		Gson gson = new GsonBuilder().registerTypeAdapter(Task.class, new TaskDeserializer()).create();
		Type listType = new TypeToken<ArrayList<Task>>() {}.getType();
		allTasks = gson.fromJson(jsonString, listType);
		
		if (allTasks == null){
			allTasks = new ArrayList<Task>();
		}
		
		return allTasks;
		
	}
	
	/**
	 * 
	 * @param filePath
	 * @return String
	 */
	private String checkFileExist(String filePath){

		File file = new File(filePath);

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

}


