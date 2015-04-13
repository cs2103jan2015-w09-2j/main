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
			display.set(MESSAGE_FILE_ACCESS_NOT_ALLOWED);
		}
		
	}

	/**
	 * Undo the command previously executed by this class
	 */
	@Override
	public void undo(){

		try{
			storage.setPath(previousStorageLocation);
		}
		catch(IOException ioEx){
			logger.log(Level.WARNING, NAME_CLASS_STORAGE, MESSAGE_FILE_ACCESS_NOT_ALLOWED);
			display.set(MESSAGE_FILE_ACCESS_NOT_ALLOWED);
			return;
		}
		
		try{
			data.set(storage.getData());
		}
		catch(IOException ioEx){
				logger.log(Level.WARNING, NAME_CLASS_STORAGE, MESSAGE_FILE_ACCESS_NOT_ALLOWED);
				display.set(MESSAGE_FILE_ACCESS_NOT_ALLOWED);
				return;
			}
		
		display.set(MESSAGE_UNDO_SAVE);
	}

	/**
	 * Execute the command specified in this class
	 */
	public void execute() {
		
		try{
			previousStorageLocation = storage.getFilePath();
			storage.setPath(storageLocation);
			}catch(IOException ioEx){
				logger.log(Level.WARNING, NAME_CLASS_STORAGE, MESSAGE_FILE_ACCESS_NOT_ALLOWED);
				display.set(MESSAGE_FILE_ACCESS_NOT_ALLOWED);
				return;
			}
		
		
		try{
			data.set(storage.getData());
		}catch(IOException ioEx){
			logger.log(Level.WARNING, NAME_CLASS_STORAGE, MESSAGE_FILE_ACCESS_NOT_ALLOWED);
			display.set(MESSAGE_FILE_ACCESS_NOT_ALLOWED);
			return;
		}
		
		display.set(String.format(MESSAGE_SAVE_NEW_USER_DIRECTORY , storageLocation));
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 * 
	 * @param o the reference object with which to compare.
	 */
	@Override
	public boolean equals(Object o) {
		if (this.getClass() != o.getClass()){
			return false;
		}
		
		SaveCmd save = (SaveCmd) o;
		
		if (!this.storageLocation.equals(save.storageLocation)){
			return false;
		}
		else if (!this.previousStorageLocation.equals(save.previousStorageLocation)){
			return false;
		}
		else if (!save.storage.equals(save.storage)){
			return false;
		}
		
		return true;
	}

}