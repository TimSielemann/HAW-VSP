package accessor_one;

import mware_lib.EntferntesReferenzmodul;

public abstract class ClassOneImplBase {

	public abstract String methodOne(String param1, int param2)
			throws SomeException112;

	public static ClassOneImplBase narrowCast(Object rawObjectRef, int port,
			EntferntesReferenzmodul refmodul, boolean debug) {
		return new ClassOneImplBaseStub(rawObjectRef, port, refmodul, debug);
	}

}
