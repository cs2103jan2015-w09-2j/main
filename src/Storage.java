//@author A0111217

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
	private static final String CHARACTER_BACKSLASH = "\\";
	private static final String CHARACTER_REVERSE_BACKSLASH = "/";
	private static final String MESSAGE_ERROR_FILE_NOT_FOUND = "%1$s is not found!\r\n";
	private static final String CHARACTER_EMPTY_STRING = "";
	private static Logger logger = Logger.getLogger("Storage");
	private static final String DIRECTORY_LOGGER = "StorageLog";

	private String fileName = "oneTag.json"; // default name is oneTag.json
	private ArrayList<Task> allTasks;
	private String currentRelativePath = System.getProperty(USER_DIRECTORY);
	private String filePath = currentRelativePath + CHARACTER_BACKSLASH + fileName;

	public Storage() {
		allTasks = new ArrayList<Task>();
		checkFileExist(this.filePath);
		initializeLogger();
	}

	public Storage(ArrayList<Task> task) {
		allTasks = task;
		checkFileExist(this.filePath);
		initializeLogger();
	}
	
	// Used for testing
	public Storage(String directory, ArrayList<Task> task) {
		filePath = directory;
		allTasks = task;
		checkFileExist(this.filePath);
		initializeLogger();
	}

	private void initializeLogger() {
		Handler fh;
		try {
			fh = new FileHandler(DIRECTORY_LOGGER, true);
		} catch (SecurityException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			System.out.println(String.format(MESSAGE_ERROR_FILE_NOT_FOUND,
					DIRECTORY_LOGGER));
			return;
		}
		SimpleFormatter formatter = new SimpleFormatter();
		fh.setFormatter(formatter);
		logger.addHandler(fh);
		logger.setLevel(Level.OFF);
	}
	
	/**
	 * Gets the file path
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
		copyFile(userSpecifiedDirectory);

		File configFile = new File(NAME_CONFIG_FILE);
		configFile.createNewFile();
	
		filePath = userSpecifiedDirectory + CHARACTER_BACKSLASH + fileName;
		writeStringToFile(filePath, NAME_CONFIG_FILE);

		closeFileHandler();

	}

	private void copyFile(String userSpecifiedDirectory) {
		String oldFilePath = filePath;
		File oldLocationFile = new File(oldFilePath);
		
		if (userSpecifiedDirectory.endsWith(CHARACTER_BACKSLASH) | userSpecifiedDirectory.endsWith(CHARACTER_REVERSE_BACKSLASH)){
			filePath = userSpecifiedDirectory + fileName;
		}
		else{
		filePath = userSpecifiedDirectory + CHARACTER_BACKSLASH + fileName;
		}
		File updatedLocationFile = new File(filePath);

		try {
			Files.copy(oldLocationFile.toPath(), updatedLocationFile.toPath());
		} catch (IOException e1) {
			logger.log(Level.WARNING,
					String.format(MESSAGE_ERROR_FILE_NOT_FOUND, fileName));
		}

		oldLocationFile.delete();
	}

	private void closeFileHandler() {
		for (Handler h : logger.getHandlers()) {
			h.close();
		}
	}

	/**
	 * Writes the ArrayList<Task> to file
	 * 
	 * @param tasks
	 */
	public void writeToFile(ArrayList<Task> tasks) {

		String json = convertTaskToString(tasks);
		writeStringToFile(json, filePath);
		closeFileHandler();

	}

	private String convertTaskToString(ArrayList<Task> tasks) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting().registerTypeAdapter(Task.class,
				new TaskSerializer());
		Gson gson = gsonBuilder.create();
		String json = gson.toJson(tasks);
		return json;
	}

	private void writeStringToFile(String json, String filePath) {
		FileWriter fw;
		try {
			fw = new FileWriter(filePath);
			fw.write(json);
			;
			fw.flush();
			fw.close();
		} catch (Exception e) {
			logger.log(Level.WARNING, e.getMessage());
		}
	}

	/**
	 * Gets the list of tasks from file, puts them into an ArrayList
	 * 
	 * @return ArrayList<Task>
	 */
	public ArrayList<Task> getData() {
		String jsonString = new String(CHARACTER_EMPTY_STRING);
		jsonString = readStringFromFile(jsonString);

		convertStringToTask(jsonString);

		if (allTasks == null) {
			allTasks = new ArrayList<Task>();
		}

		closeFileHandler();

		return allTasks;

	}

	private void convertStringToTask(String jsonString) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Task.class,
				new TaskDeserializer()).create();
		Type listType = new TypeToken<ArrayList<Task>>() {
		}.getType();
		allTasks = gson.fromJson(jsonString, listType);
	}

	private String readStringFromFile(String jsonString) {

		BufferedReader br = null;

		try {
			String line;
			br = new BufferedReader(new FileReader(filePath));
			while ((line = br.readLine()) != null) {
				jsonString += line;
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, e.getMessage());
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				logger.log(Level.WARNING, e.getMessage());
			}
		}
		return jsonString;
	}

	/**
	 * 
	 * @param filePath
	 * @return boolean
	 */
	private boolean checkFileExist(String filePath) {

		File file = new File(filePath);
		File configFile = new File(NAME_CONFIG_FILE);

		if (configFile.exists()) {
			try {
				this.filePath = new Scanner(new File(NAME_CONFIG_FILE))
						.useDelimiter("\\Z").next();
			} catch (FileNotFoundException e) {
				logger.log(Level.WARNING,
						String.format(MESSAGE_ERROR_FILE_NOT_FOUND, fileName));
			}
		} else {
			if (file.exists()) {
				return true;
			} else {
				try {
					file.createNewFile();
				} catch (IOException e) {
					logger.log(Level.WARNING, String.format(
							MESSAGE_ERROR_FILE_NOT_FOUND, fileName));
					return false;
				}
			}

		}

		return false;
	}

}
