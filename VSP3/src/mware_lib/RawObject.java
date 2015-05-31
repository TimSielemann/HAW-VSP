package mware_lib;

public class RawObject {
	
	private Kommunikationsmodul kommModul;
	private ObjectReference objectReference;
	
	public RawObject(ObjectReference ret, Kommunikationsmodul kommModul) {
		this.objectReference = (ObjectReference)ret;
		this.kommModul = kommModul;
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

}
