import java.io.IOException;


public class SaveCmd extends ModifiableCmd {
	String storageLocation;
	Storage storage;
	
	public SaveCmd(String storageLocation) {
		this.storageLocation = storageLocation;
		storage = new Storage();
	}

	@Override
	public boolean execute(){
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
