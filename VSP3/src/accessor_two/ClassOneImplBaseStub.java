package accessor_two;

import mware_lib.EntferntesReferenzmodul;
import mware_lib.ObjectReference;
import mware_lib.Kommunikationsmodul;

public class ClassOneImplBaseStub extends ClassOneImplBase {

	private ObjectReference objectRef;
	private int port;
	private EntferntesReferenzmodul refmodul;
	private boolean debug;

	public ClassOneImplBaseStub(Object rawObjectRef, int port,
			EntferntesReferenzmodul refmodul, boolean debug) {
		this.objectRef = (ObjectReference) rawObjectRef;
		this.port = port;
		this.refmodul = refmodul;
		this.debug = debug;
	}

	@Override
	public double methodOne(String param1, double param2)
			throws SomeException112 {
		Object[] params = { param1, param2 };
		Kommunikationsmodul komModul = new Kommunikationsmodul(this.port,
				this.refmodul, this.debug);
		Object result = komModul.send(this.objectRef, "methodTwo", params);
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
		Kommunikationsmodul komModul = new Kommunikationsmodul(this.port,
				this.refmodul, this.debug);
		Object result = komModul.send(this.objectRef, "methodTwo", params);
		if (result instanceof SomeException112) {
			throw ((SomeException112) result);
		} else if (result instanceof SomeException304) {
			throw ((SomeException304) result);
		} else {
			return ((double) result);
		}
	}

}
