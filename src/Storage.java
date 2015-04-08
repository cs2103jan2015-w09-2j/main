//@author A0111217

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Storage {

	private static final String CHARACTER_END_OF_FILE = "\\Z";
	private static final String NAME_CONFIG_FILE = "config.json";
	private static final String USER_DIRECTORY = "user.dir";
	private static final String CHARACTER_BACKSLASH = "\\";
	private static final String CHARACTER_REVERSE_BACKSLASH = "/";
	private static final String CHARACTER_EMPTY_STRING = "";
	private static OneTagLogger logger = OneTagLogger.getInstance();

	private String fileName = "oneTag.json"; // default name is oneTag.json
	private ArrayList<Task> allTasks;
	private String currentRelativePath = System.getProperty(USER_DIRECTORY);
	private String filePath = currentRelativePath + CHARACTER_BACKSLASH + fileName;

	public Storage() throws IOException{
		allTasks = new ArrayList<Task>();
		checkFileExist(this.filePath);
	}
	
	public Storage(String userSpecifiedDirectory) throws IOException{
		allTasks = new ArrayList<Task>();
		checkFileExist(userSpecifiedDirectory);
	}
	
	// Used for testing
	public Storage(String directory, ArrayList<Task> task) throws IOException{
		filePath = directory;
		allTasks = task;
		checkFileExist(this.filePath);
	}
	
	/**
	 * Gets the entire file path
	 * @return String
	 */
	public String getPath(){
		return filePath;
	}
	
	/**
	 * Gets the file directory without the file name
	 * @return String
	 */
	public String getFilePath(){
		String pathToReturn = new String(filePath);
		
		pathToReturn = pathToReturn.replaceFirst(fileName, CHARACTER_EMPTY_STRING);
		return pathToReturn;
	}
	
	/**
	 * Changes the directory to specified directory
	 */
	public void setPath(String userSpecifiedDirectory) throws IOException{
		assert (userSpecifiedDirectory == null);
		
		String oldFilePath = filePath;
		
		if (userSpecifiedDirectory.endsWith(CHARACTER_BACKSLASH) | userSpecifiedDirectory.endsWith(CHARACTER_REVERSE_BACKSLASH)){
			filePath = userSpecifiedDirectory + fileName;
		}
		else{
		filePath = userSpecifiedDirectory + CHARACTER_BACKSLASH + fileName;
		}
		
		File file = new File(filePath);
		if (!file.exists()){
		copyFile(oldFilePath, filePath);
		}
		writeStringToFile(filePath, NAME_CONFIG_FILE);
		
		File oldFile = new File(oldFilePath);
		oldFile.delete();

	}

	/**
	 * Writes the ArrayList<Task> to file
	 * 
	 * @param tasks
	 */
	public void writeToFile(ArrayList<Task> tasks) throws IOException{
		
		String json = convertTaskToString(tasks);
		writeStringToFile(json, filePath);

	}

	/**
	 * Gets the list of tasks from file, puts them into an ArrayList
	 * 
	 * @return ArrayList<Task>
	 */
	public ArrayList<Task> getData() throws IOException{
		String jsonString = new String(CHARACTER_EMPTY_STRING);
		jsonString = readStringFromFile(jsonString);

		convertStringToTask(jsonString);

		if (allTasks == null) {
			allTasks = new ArrayList<Task>();
		}


		return allTasks;

	}
	
	/*
	 * Contains mechanism to correct missing config.json files and oneTag.json file
	 */
	private boolean checkFileExist(String filePath) throws IOException {

		File file;
		File configFile = new File(NAME_CONFIG_FILE);
		
		if (configFile.exists()) {
			Scanner scanConfig = new Scanner(configFile);
			this.filePath = scanConfig.useDelimiter(CHARACTER_END_OF_FILE).next();
			scanConfig.close();
			file = new File(this.filePath);
			if (file.exists()){
				return true;
			}
			else{
				file.createNewFile();
				return false;
			}
		} else {
			configFile.createNewFile();
			file = new File(filePath);
			file.createNewFile();
			writeStringToFile(filePath, NAME_CONFIG_FILE);
			return false;
		}

	}
	
	private void copyFile(String oldFilePath, String userSpecifiedPath) throws IOException {
		
		File oldLocationFile = new File(oldFilePath);
		filePath = userSpecifiedPath;
		
		File updatedLocationFile = new File(filePath);
		
		
		Files.copy(oldLocationFile.toPath(), updatedLocationFile.toPath());
		
	}
	
	private void convertStringToTask(String jsonString) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Task.class,
				new TaskDeserializer()).create();
		Type listType = new TypeToken<ArrayList<Task>>() {
		}.getType();
		allTasks = gson.fromJson(jsonString, listType);
	}
	
	private String convertTaskToString(ArrayList<Task> tasks) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting().registerTypeAdapter(Task.class, new TaskSerializer());
		Gson gson = gsonBuilder.create();
		String json = gson.toJson(tasks);
		
		return json;
	}

	private String readStringFromFile(String fileString) throws IOException {

		BufferedReader br = null;

		String line;
		br = new BufferedReader(new FileReader(filePath));
		while ((line = br.readLine()) != null) {
			fileString += line;
		}
		
		br.close();
		
		return fileString;
	}
	
	private void writeStringToFile(String stringToWrite, String filePath) throws IOException{
		FileWriter fw;
		fw = new FileWriter(filePath);
		fw.write(stringToWrite);
		fw.flush();
		fw.close();
	}
}
