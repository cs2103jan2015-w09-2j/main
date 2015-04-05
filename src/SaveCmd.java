
public class SaveCmd extends ModifiableCmd {
	String storageLocation;
	Storage storage;
	
	public SaveCmd(String storageLocation) {
		this.storageLocation = storageLocation;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute() {
		storage = new Storage();
		storage.setPath(storageLocation);
		return true;
	}

	@Override
	public void undo() {
		storage.setPath(storageLocation);
	}

}
