
public class ExitCmd extends ModifiableCmd{

	private static final int EXIT_CODE = 0;

	@Override
	public boolean execute() {
		logger.close();
		System.exit(EXIT_CODE);
		
		return true;
	}

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
