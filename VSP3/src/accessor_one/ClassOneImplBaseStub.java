package accessor_one;


import accessor_one.SomeException112;
import mware_lib.EntferntesReferenzmodul;
import mware_lib.Kommunikationsmodul;
import mware_lib.ObjectReference;
import mware_lib.RawObject;

public class ClassOneImplBaseStub extends ClassOneImplBase {

	private RawObject rawobject;
	
	public ClassOneImplBaseStub(Object rawObjectRef){
		this.rawobject= (RawObject)rawObjectRef;
	}
	@Override
	public String methodOne(String param1, int param2) throws SomeException112 {
		Object[] params = {param1,param2};
		Object result = rawobject.getKommModul().send(this.rawobject.getObjectReference(), "methodOne", params);
		if (result instanceof SomeException112) {
			throw ((SomeException112) result);
		} else {
			return ((String) result);
		}
	}

}
