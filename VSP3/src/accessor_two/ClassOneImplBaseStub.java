package accessor_two;

import mware_lib.EntferntesReferenzmodul;
import mware_lib.ObjectReference;
import mware_lib.Kommunikationsmodul;
import mware_lib.RawObject;

public class ClassOneImplBaseStub extends ClassOneImplBase {

	private RawObject rawObject;

	public ClassOneImplBaseStub(Object rawObjectRef) {
		this.rawObject = (RawObject) rawObjectRef;
	}

	@Override
	public double methodOne(String param1, double param2)
			throws SomeException112 {
		Object[] params = { param1, param2 };
		Object result = rawObject.getKommModul().send(this.rawObject.getObjectReference(), "methodOne", params);
		if (result instanceof SomeException112) {
			throw ((SomeException112) result);
		} else {
			return ((double) result);
		}
	}

	@Override
	public double methodTwo(String param1, double param2)
			throws SomeException112, SomeException304 {
		Object[] params = { param1, param2 };
		Object result = rawObject.getKommModul().send(this.rawObject.getObjectReference(), "methodTwo", params);
		if (result instanceof SomeException112) {
			throw ((SomeException112) result);
		} else if (result instanceof SomeException304) {
			throw ((SomeException304) result);
		} else {
			return ((double) result);
		}
	}

}
