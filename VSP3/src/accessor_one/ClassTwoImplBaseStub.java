package accessor_one;

import accessor_one.SomeException112;
import mware_lib.RawObject;

public class ClassTwoImplBaseStub extends ClassTwoImplBase {

	private RawObject rawObject;

	public ClassTwoImplBaseStub(Object rawObjectRef) {
		this.rawObject = (RawObject) rawObjectRef;
	}

	@Override
	public int methodOne(double param1) throws SomeException110 {
		Object[] params = { param1 };
		Object result = rawObject.getKommModul().send(
				this.rawObject.getObjectReference(), "methodOne", params);
		if (result instanceof SomeException110) {
			throw ((SomeException110) result);
		} else {
			return ((int) result);
		}
	}

	@Override
	public double methodTwo() throws SomeException112 {
		Object[] params = {};
		Object result = rawObject.getKommModul().send(
				this.rawObject.getObjectReference(), "methodTwo", params);
		if (result instanceof SomeException112) {
			throw ((SomeException112) result);
		} else {
			return ((double) result);
		}
	}

}