
public class SaveCmd extends ModifiableCmd {
	String storageLocation;
	public SaveCmd(String storageLocation) {
		this.storageLocation = storageLocation;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute() {
		Storage storage = new Storage();
		storage.setPath(storageLocation);
		return true;
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

}
