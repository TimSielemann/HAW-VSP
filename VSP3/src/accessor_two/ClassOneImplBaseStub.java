package accessor_two;

import mware_lib.ObjectReference;
import mware_lib.Kommunikationsmodul;

public class ClassOneImplBaseStub extends ClassOneImplBase {
	
	private ObjectReference objectRef;

	public ClassOneImplBaseStub(Object rawObjectRef){
		this.objectRef=(ObjectReference) rawObjectRef;
	}
	@Override
	public double methodOne(String param1, double param2)
			throws SomeException112 {
		Object[] params = {param1,param2};
		return Kommunikationsmodul.send(this.objectRef,"methodOne",params);
	}

	@Override
	public double methodTwo(String param1, double param2)
			throws SomeException112, SomeException304 {
		Object[] params = {param1,param2};
		return Kommunikationsmodul.send(this.objectRef,"methodTwo",params);
	}

}
