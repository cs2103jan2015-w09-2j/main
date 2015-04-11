//@author A0111217Y
import java.io.IOException;
import java.util.logging.Level;


public class SaveCmd extends ModifiableCmd {
	
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

		try{
			storage.setPath(previousStorageLocation);
		}catch(IOException ioEx){
			logger.log(Level.WARNING, NAME_CLASS_STORAGE, MESSAGE_FILE_ACCESS_NOT_ALLOWED);
			display.setMessage(MESSAGE_FILE_ACCESS_NOT_ALLOWED);
			return;
		}
		
		try{
			data.set(storage.getData());
			}catch(IOException ioEx){
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
	
	public boolean equals(SaveCmd cmd){
		if (this.getClass() != cmd.getClass()){
			return false;
		}
		else if (!this.storageLocation.equals(cmd.storageLocation)){
			return false;
		}
		else if (!this.previousStorageLocation.equals(cmd.previousStorageLocation)){
			return false;
		}
		else if (!this.storage.equals(cmd.storage)){
			return false;
		}
		
		return true;
	}

}