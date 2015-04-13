//@author A0111217Y
public class ExitCmd extends ModifiableCmd{

	private static final int EXIT_CODE = 0;

	/**
	 * Execute the command specified in this class
	 */
	public void execute() {
		logger.close();
		System.exit(EXIT_CODE);
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 * 
	 * @param o the reference object with which to compare.
	 */
	@Override
	public boolean equals(Object o) {
		if (this.getClass() == o.getClass()){
			return true;
		}
		else{
			return false;
		}
	}		
}
