package accessor_one;

public class SomeException110 extends Exception {

	// ist serialisierbar (Diagramm: Entfernter Methodenaufruf) und erbt von
	// Exception(unter Punkt Exception)
	private static final long serialVersionUID = 1505977596929721707L;

	public SomeException110(String message) {
		super(message);
	}
}
