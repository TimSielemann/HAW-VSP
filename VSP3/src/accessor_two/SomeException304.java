package accessor_two;

public class SomeException304 extends Exception {
	// ist serialisierbar (Diagramm: Entfernter Methodenaufruf) und erbt von
	// Exception(unter Punkt Exception)
	private static final long serialVersionUID = -628756181903679311L;

	public SomeException304(String message) {
		super(message);
	}
}
