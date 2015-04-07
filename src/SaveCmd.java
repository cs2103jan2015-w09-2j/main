import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class SaveCmd extends ModifiableCmd {
	String storageLocation;
	String previousStorageLocation;
	Storage storage;
	private static final String NAME_CONFIG = "config.json";
	
	public SaveCmd(String storageLocation) {
		this.storageLocation = storageLocation;
		storage = new Storage();
	}

	@Override
	public boolean execute(){
		File config = new File(NAME_CONFIG);
		if (config.exists()){
			try {
				previousStorageLocation = new Scanner(config).useDelimiter("\\Z").next();
			} catch (FileNotFoundException e) {
				display.setMessage(MESSAGE_SAVE_CONFIG_NOT_FOUND);
			}
		}
		else{
			previousStorageLocation = storage.getPath();
		}
		
		try{
		storage.setPath(storageLocation);
		}catch(IOException ioEx){
			display.setMessage(String.format(MESSAGE_ERROR_FILE_NOT_FOUND, storageLocation));
			return false;
		}
		display.setMessage(String.format(MESSAGE_SAVE_NEW_USER_DIRECTORY , storageLocation));
		
		return true;
	}

	@Override
	public void undo() {
		
		try{
			storage.setPath(storageLocation);
			}catch(IOException ioEx){
				display.setMessage(String.format(MESSAGE_ERROR_FILE_NOT_FOUND, storageLocation));
				return;
			}
		
		display.setMessage(MESSAGE_UNDO_SAVE);
	}

}