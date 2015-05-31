package accessor_one;

import mware_lib.EntferntesReferenzmodul;

public abstract class ClassTwoImplBase {
	public abstract int methodOne(double param1) throws SomeException110;

	public abstract double methodTwo() throws SomeException112;

	public static ClassTwoImplBase narrowCast(Object rawObjectRef, int port,
			EntferntesReferenzmodul refmodul, boolean debug) {
		return new ClassTwoImplBaseStub(rawObjectRef,port,refmodul,debug);

	}

}
