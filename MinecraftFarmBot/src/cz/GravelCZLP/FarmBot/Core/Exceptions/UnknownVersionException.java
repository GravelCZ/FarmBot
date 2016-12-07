package cz.GravelCZLP.FarmBot.Core.Exceptions;

public class UnknownVersionException extends Exception {
	private static final long serialVersionUID = 3846494776315389641L;

	public UnknownVersionException(String cause) {
		super(cause);
	}
	
}
