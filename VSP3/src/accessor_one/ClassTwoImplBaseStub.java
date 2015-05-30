package accessor_one;

import nameservice.ObjectReference;
import mware_lib.Kommunikationsmodul;

public class ClassTwoImplBaseStub extends ClassTwoImplBase {

	private ObjectReference objectRef;

	public ClassTwoImplBaseStub(Object rawObjectRef) {
		this.objectRef = (ObjectReference) rawObjectRef;
	}

	@Override
	public double methodTwo() throws SomeException112 {
		Object[] params = {};
		return Kommunikationsmodul.send(this.objectRef, "methodTwo", params);
	}

	@Override
	public int methodOne(double param1) throws SomeException110 {
		Object[] params = { param1 };
		return Kommunikationsmodul.send(this.objectRef, "methodOne", params);
	}

}
