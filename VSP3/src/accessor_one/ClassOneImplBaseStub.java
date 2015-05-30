package accessor_one;

import nameservice.ObjectReference;
import mware_lib.Kommunikationsmodul;

public class ClassOneImplBaseStub extends ClassOneImplBase {

	private ObjectReference objectRef;
	
	public ClassOneImplBaseStub(Object rawObjectRef){
		this.objectRef= (ObjectReference)rawObjectRef;
	}
	@Override
	public String methodOne(String param1, int param2) throws SomeException112 {
		Object[] params = {param1,param2};
		return Kommunikationsmodul.send(this.objectRef,"methodOne",params);
	}

}
