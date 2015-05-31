package accessor_one;

import accessor_one.SomeException112;
import mware_lib.EntferntesReferenzmodul;
import mware_lib.ObjectReference;
import mware_lib.Kommunikationsmodul;

public class ClassTwoImplBaseStub extends ClassTwoImplBase {

	private ObjectReference objectRef;
	private EntferntesReferenzmodul refmodul;
	private int port;
	private boolean debug;

	public ClassTwoImplBaseStub(Object rawObjectRef, int port,
			EntferntesReferenzmodul refmodul, boolean debug) {
		this.objectRef = (ObjectReference) rawObjectRef;
		this.port = port;
		this.refmodul = refmodul;
		this.debug = debug;
	}

	@Override
	public double methodTwo() throws SomeException112 {
		Object[] params = {};
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
	public int methodOne(double param1) throws SomeException110 {
		Object[] params = { param1 };
		Kommunikationsmodul komModul = new Kommunikationsmodul(this.port,
				this.refmodul, this.debug);
		Object result = komModul.send(this.objectRef, "methodTwo", params);
		if (result instanceof SomeException110) {
			throw ((SomeException110) result);
		} else {
			return ((int) result);
		}
	}

}
