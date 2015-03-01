import com.google.gson.Gson;

public class Storage {
	
	private static final String USER_DIRECTORY = "user.dir";
	private static final String CHARACTER_BACKSLASH = "//";
	private static final String MESSAGE_NEW_USER_DIRECTORY = "Directory has been set to %1$s";
	private static final String MESSAGE_NEW_FILE_NAME = "File name has been set to %1$s";
	private String fileName = "oneTag.json";  //default name is oneTag.json
	private String filePath;
	
	
	public Storage(){
		String currentRelativePath = System.getProperty(USER_DIRECTORY);
		filePath = currentRelativePath + CHARACTER_BACKSLASH + fileName;
	}
	
	public Storage(String directory){
		filePath = directory;
	}
	
	public Storage(String directory, String name){
		fileName = name;
		filePath = directory + CHARACTER_BACKSLASH + fileName;
	}
	
	public String setPath(String newDirectory){
		filePath = newDirectory + CHARACTER_BACKSLASH + fileName;
		
		return String.format(MESSAGE_NEW_USER_DIRECTORY, newDirectory);
	}
	
	public String setFileName(String name){
		fileName = name;
		
		return String.format(MESSAGE_NEW_FILE_NAME, name);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
