
public class SaveCmd extends Cmd {
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

}
