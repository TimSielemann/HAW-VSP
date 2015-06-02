package mware_lib;
/**
 * Kapselt eine Entfernte Objectreferenz 
 * @author Tim
 *
 */
public class RawObject {
	
	private Kommunikationsmodul kommModul;
	private ObjectReference objectReference;
	private boolean debug;
	
	public RawObject(ObjectReference ret, Kommunikationsmodul kommModul, boolean debug) {
		this.objectReference = (ObjectReference)ret;
		this.kommModul = kommModul;
		this.setDebug(debug);
	}

		public Kommunikationsmodul getKommModul() {
		return kommModul;
	}

	public void setKommModul(Kommunikationsmodul kommModul) {
		this.kommModul = kommModul;
	}

	public ObjectReference getObjectReference() {
		return objectReference;
	}

	public void setObjectReference(ObjectReference objectReference) {
		this.objectReference = objectReference;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

}
