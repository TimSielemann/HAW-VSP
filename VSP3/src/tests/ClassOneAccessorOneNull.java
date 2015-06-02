package tests;

import accessor_one.ClassOneImplBase;
import accessor_one.SomeException112;

public class ClassOneAccessorOneNull extends ClassOneImplBase {

	@Override
	public String methodOne(String param1, int param2) throws SomeException112 {
		Werkzeug.printResult(this.getClass().getName(), this.toString(), "methodOne", param1, param2, null);
		return null;
	}

}
