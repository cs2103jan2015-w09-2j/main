import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

//@author A0111867A
public abstract class ModifiableCmd extends Cmd{
	
	protected static final String NAME_CLASS_MODIFIABLECMD = "ModifiableCmd";
	
	//@author A0111217Y
	protected void writeToFile(){
		try{
		Storage file = new Storage();
		
		ArrayList<Task> tasks = data.getData();
		file.writeToFile(tasks);
		}
		catch(FileNotFoundException ex){
			logger.log(Level.WARNING, NAME_CLASS_MODIFIABLECMD, MESSAGE_STORAGE_FILE_NOT_FOUND);
			display.set(MESSAGE_STORAGE_FILE_NOT_FOUND);
		}
		catch(IOException ex){
			logger.log(Level.WARNING, NAME_CLASS_MODIFIABLECMD, MESSAGE_FILE_ACCESS_NOT_ALLOWED);
			display.set(MESSAGE_FILE_ACCESS_NOT_ALLOWED);
		}
	}
}
