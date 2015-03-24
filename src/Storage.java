/*
 * @author: Ng Zhi Hua
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


public class Storage {
	
	private static final String NAME_CONFIG_FILE = "config.json";
	private static final String USER_DIRECTORY = "user.dir";
	private static final String CHARACTER_BACKSLASH = "/";
	private static final String MESSAGE_NEW_USER_DIRECTORY = "Directory has been set to %1$s";
	private static final String MESSAGE_NEW_FILE_NAME = "File name has been set to %1$s";
	private static final String MESSAGE_ERROR_FILE_NOT_FOUND = "%1$s is not found!\r\n";
	private static final String CHARACTER_EMPTY_STRING = "";
	private static Logger logger = Logger.getLogger("Storage");
	private static final String DIRECTORY_LOGGER = "storage.log";
	
	private String fileName = "oneTag.json";  //default name is oneTag.json
	private ArrayList<Task> allTasks;
	private String currentRelativePath = System.getProperty(USER_DIRECTORY);
	private String filePath = currentRelativePath + CHARACTER_BACKSLASH + fileName;
	
	
	public Storage(){
		allTasks = new ArrayList<Task>();
		checkFileExist(this.filePath);
		initializeLogger();
	}
	
	public Storage(ArrayList<Task> task){
		allTasks = task;
		checkFileExist(this.filePath);
		initializeLogger();
	}
	
	public Storage(String directory, ArrayList<Task> task){
		filePath = directory;
		allTasks = task;
		checkFileExist(this.filePath);
		initializeLogger();
	}
	
	public Storage(String directory, String fileName, ArrayList<Task> task ){
		this.fileName = fileName;
		filePath = directory + CHARACTER_BACKSLASH + fileName;
		allTasks = task;
		checkFileExist(this.filePath);
		initializeLogger();
	}
	
	private void initializeLogger(){
		Handler fh;
		try {
			fh = new FileHandler(DIRECTORY_LOGGER);
		} catch (SecurityException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			System.out.println(String.format(MESSAGE_ERROR_FILE_NOT_FOUND, DIRECTORY_LOGGER));
			return;
		}
		SimpleFormatter formatter = new SimpleFormatter();
		fh.setFormatter(formatter);
		logger.addHandler(fh);
		logger.setLevel(Level.OFF);
	}
	
	/**
	 * Changes the directory to specified directory
	 */
	public String setPath(String userSpecifiedDirectory){
		copyFile(userSpecifiedDirectory);
		
		logger.log(Level.FINEST, String.format(MESSAGE_NEW_USER_DIRECTORY, userSpecifiedDirectory) );
		
		File configFile = new File(NAME_CONFIG_FILE);
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				logger.log(Level.WARNING, String.format(MESSAGE_ERROR_FILE_NOT_FOUND, NAME_CONFIG_FILE),e.getMessage());
			}
		filePath = userSpecifiedDirectory + CHARACTER_BACKSLASH + fileName;
		writeStringToFile(filePath,NAME_CONFIG_FILE);
		
		
		
		return String.format(MESSAGE_NEW_USER_DIRECTORY, userSpecifiedDirectory);
	}

	private void copyFile(String userSpecifiedDirectory){
		String oldFilePath = filePath;
		File oldLocationFile = new File(oldFilePath);
		filePath = userSpecifiedDirectory + CHARACTER_BACKSLASH + fileName;
		File updatedLocationFile = new File(filePath);
		try {
			Files.copy(oldLocationFile.toPath(), updatedLocationFile.toPath());
		} catch (IOException e1) {
			logger.log(Level.WARNING, String.format(MESSAGE_ERROR_FILE_NOT_FOUND, fileName));
		}
		oldLocationFile.delete();
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
		
		writeStringToFile(json, filePath);
		
		
	}

	private void writeStringToFile(String json, String filePath) {
		FileWriter fw;
		try{
			fw = new FileWriter(filePath);
			fw.write(json);;
			fw.flush();
			fw.close();
		}catch(Exception e){
			logger.log(Level.WARNING, e.getMessage());
		}
	}
	

	/**
	 * Gets the list of tasks from file, puts them into an ArrayList
	 * @return ArrayList<Task>
	 */
	public ArrayList<Task> getData(){
		String jsonString = new String("");		
		BufferedReader br = null;
		try{
			String line;
			br = new BufferedReader(new FileReader(filePath));
			while((line = br.readLine()) != null){
				jsonString += line;
			}
		} catch(Exception e){
			logger.log(Level.WARNING, e.getMessage());
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				logger.log(Level.WARNING, e.getMessage());
			}
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
	private boolean checkFileExist(String filePath){

		File file = new File(filePath);
		File configFile = new File(NAME_CONFIG_FILE);
		if (configFile.exists()){
			try {
				this.filePath = new Scanner(new File(NAME_CONFIG_FILE)).useDelimiter("\\Z").next();
			} catch (FileNotFoundException e) {
				logger.log(Level.WARNING, String.format(MESSAGE_ERROR_FILE_NOT_FOUND, fileName));
			}
		}
		else{
			if (file.exists()){
				return true;
			}
			else{
				try {
					file.createNewFile();
				} catch (IOException e) {
					logger.log(Level.WARNING, String.format(MESSAGE_ERROR_FILE_NOT_FOUND,fileName));
					return false;
				}
			}
				
		}
		
		return false;
		
		/*
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				return String.format(MESSAGE_ERROR_FILE_NOT_FOUND,
						fileName);
			}
		}
		*/
	}
	
	public static void main(String args[]){
		Storage storage = new Storage();
	}
	
}


