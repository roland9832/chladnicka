package sk.upjs.ics.chladnicka.storage;

public class EntityUndeletableException extends RuntimeException{


	private static final long serialVersionUID = 6310395448839444909L;

	public EntityUndeletableException(String info) {
		super(info);
	}
	
	
}
