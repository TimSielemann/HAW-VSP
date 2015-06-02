package accessor_two;

import accessor_two.SomeException112;
import mware_lib.RawObject;

public class ClassOneImplBaseStub extends ClassOneImplBase {

	private RawObject rawObject;

	public ClassOneImplBaseStub(Object rawObjectRef) {
		this.rawObject = (RawObject) rawObjectRef;
	}

	@Override
	public double methodOne(String param1, double param2)
			throws SomeException112 {
		if(param1==null){
			param1="defaultNull";
		}
		Object[] params = { param1, param2 };
		Object result = rawObject.getKommModul().send(
				this.rawObject.getObjectReference(), "methodOne", params);
		if (result instanceof SomeException112) {
			throw ((SomeException112) result);
		} else {
			return ((double) result);
		}
	}

	@Override
	public double methodTwo(String param1, double param2)
			throws SomeException112, SomeException304 {
		if(param1==null){
			param1="defaultNull";
		}
		Object[] params = { param1, param2 };
		Object result = rawObject.getKommModul().send(
				this.rawObject.getObjectReference(), "methodTwo", params);
		if (result instanceof SomeException112) {
			throw ((SomeException112) result);
		} else if (result instanceof SomeException304) {
			throw ((SomeException304) result);
		} else {
			return ((double) result);
		}
	}

}