//author A0111217
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class OneTagLogger {
	
	private static final String NAME_ONE_TAG_LOG = "OneTagLog";
	private static final String CHARACTER_COLON_WITH_SPACE = " : ";
	private static final String DIRECTORY_LOGGER = NAME_ONE_TAG_LOG;
	private static final String MESSAGE_ERROR_FILE_NOT_FOUND = "%1$s is not found!\r\n";
	private static final Level logLevel = Level.OFF;
	
	private static OneTagLogger oneTagLogger = null;
	private static Logger logger;
	
	public static OneTagLogger getInstance(){
		if (oneTagLogger == null){
			oneTagLogger = new OneTagLogger();
			
		}
			return oneTagLogger;
	}
	
	private OneTagLogger(){
		initializeLogger();
	}
	
	private void initializeLogger() {
		Handler fh;
		
		logger = Logger.getLogger(NAME_ONE_TAG_LOG); 
		
		try {
			fh = new FileHandler(DIRECTORY_LOGGER, true);
		} catch (SecurityException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			System.out.println(String.format(MESSAGE_ERROR_FILE_NOT_FOUND, DIRECTORY_LOGGER));
			return;
		}
		SimpleFormatter formatter = new SimpleFormatter();
		fh.setFormatter(formatter);
		
		logger.addHandler(fh);
		logger.setLevel(logLevel);
	}
	
	
	public void log(Level level, String className, String message){
		String stringToLog = className + CHARACTER_COLON_WITH_SPACE + message;
		logger.log(level, stringToLog);
	}
	
	public void close() {
		for (Handler h : logger.getHandlers()) {
			h.close();
		}
	}
}
