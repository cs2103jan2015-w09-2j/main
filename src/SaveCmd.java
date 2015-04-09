//author A0111217
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;


public class SaveCmd extends ModifiableCmd {
	private static final String CHARACTER_END_OF_FILE = "\\Z";
	private static final String NAME_CONFIG = "config.json";
	
	String storageLocation;
	String previousStorageLocation;
	Storage storage;
	
	
	public SaveCmd(String storageLocation) {
		this.storageLocation = storageLocation;
		
		try{
		storage = new Storage();
		}catch(IOException ex){
			logger.log(Level.WARNING, NAME_CLASS_STORAGE, MESSAGE_FILE_ACCESS_NOT_ALLOWED);
			display.setMessage(MESSAGE_FILE_ACCESS_NOT_ALLOWED);
		}
		
	}

	@Override
	public void undo(){
		/*
		File config = new File(NAME_CONFIG);
		if (config.exists()){
			try {
				previousStorageLocation = new Scanner(config).useDelimiter(CHARACTER_END_OF_FILE).next();
			} catch (FileNotFoundException e) {
				logger.log(Level.WARNING, NAME_CLASS_STORAGE, MESSAGE_SAVE_CONFIG_NOT_FOUND);
				display.setMessage(MESSAGE_SAVE_CONFIG_NOT_FOUND);
				return;
			}
		}
		else{
			previousStorageLocation = storage.getPath();
		}
		
		*/
		System.out.println(previousStorageLocation);
		try{
			storage.setPath(previousStorageLocation);
		}catch(IOException ioEx){
			System.out.println("1");
			logger.log(Level.WARNING, NAME_CLASS_STORAGE, MESSAGE_FILE_ACCESS_NOT_ALLOWED);
			display.setMessage(MESSAGE_FILE_ACCESS_NOT_ALLOWED);
			return;
		}
		
		try{
			data.set(storage.getData());
			}catch(IOException ioEx){
				System.out.println("2");
				logger.log(Level.WARNING, NAME_CLASS_STORAGE, MESSAGE_FILE_ACCESS_NOT_ALLOWED);
				display.setMessage(MESSAGE_FILE_ACCESS_NOT_ALLOWED);
				return;
			}
		
		display.setMessage(MESSAGE_UNDO_SAVE);
	}

	@Override
	public boolean execute() {
		
		try{
			previousStorageLocation = storage.getFilePath();
			storage.setPath(storageLocation);
			}catch(IOException ioEx){
				logger.log(Level.WARNING, NAME_CLASS_STORAGE, MESSAGE_FILE_ACCESS_NOT_ALLOWED);
				display.setMessage(MESSAGE_FILE_ACCESS_NOT_ALLOWED);
				return false;
			}
		
		
		try{
			data.set(storage.getData());
		}catch(IOException ioEx){
			logger.log(Level.WARNING, NAME_CLASS_STORAGE, MESSAGE_FILE_ACCESS_NOT_ALLOWED);
			display.setMessage(MESSAGE_FILE_ACCESS_NOT_ALLOWED);
			return false;
		}
		
		display.setMessage(String.format(MESSAGE_SAVE_NEW_USER_DIRECTORY , storageLocation));
		
		return true;
	}

}