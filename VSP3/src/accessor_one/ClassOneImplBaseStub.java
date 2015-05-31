package accessor_one;


import accessor_one.SomeException112;
import mware_lib.EntferntesReferenzmodul;
import mware_lib.Kommunikationsmodul;
import mware_lib.ObjectReference;

public class ClassOneImplBaseStub extends ClassOneImplBase {

	private ObjectReference objectRef;
	private int port;
	private EntferntesReferenzmodul refmodul;
	private boolean debug;
	
	public ClassOneImplBaseStub(Object rawObjectRef, int port,
			EntferntesReferenzmodul refmodul, boolean debug){
		this.objectRef= (ObjectReference)rawObjectRef;
		this.port=port;
		this.refmodul=refmodul;
		this.debug=debug;
	}
	@Override
	public String methodOne(String param1, int param2) throws SomeException112 {
		Object[] params = {param1,param2};
		Kommunikationsmodul komModul = new Kommunikationsmodul(this.port,
				this.refmodul, this.debug);
		Object result = komModul.send(this.objectRef, "methodTwo", params);
		if (result instanceof SomeException112) {
			throw ((SomeException112) result);
		} else {
			return ((String) result);
		}
	}

}
