package accessor_one;

import accessor_one.SomeException112;
import mware_lib.RawObject;

public class ClassOneImplBaseStub extends ClassOneImplBase {

	private RawObject rawObject;

	public ClassOneImplBaseStub(Object rawObjectRef) {
		this.rawObject = (RawObject) rawObjectRef;
	}

	@Override
	public String methodOne(String param1, int param2) throws SomeException112 {
		Object[] params = { param1, param2 };
		Object result = rawObject.getKommModul().send(
				this.rawObject.getObjectReference(), "methodOne", params);
		if (result instanceof SomeException112) {
			if(this.rawObject.isDebug()){
				printError("accessor_one.ClassOneImplBaseStub", this.rawObject.getObjectReference().getName(), "methodOne", param1,
					param2,"accessor_one.SomeException112",((SomeException112) result).getMessage());
				}
			throw ((SomeException112) result);
		} else {
			if(this.rawObject.isDebug()){
				printResult("accessor_one.ClassOneImplBaseStub",
						this.rawObject.getObjectReference().getName(), "methodOne",param1, param2,
						(String) result);
			}
			return ((String) result);
		}
	}

	private static void printError(String packageAndClassName,
			String remotetObjName, String methodName, String param1,
			int param2, String exceptionPackageAndClassName, String message) {
		System.out
				.println("--------------------------------------------------------------");
		System.out.println(packageAndClassName + " ('" + remotetObjName + "')");
		System.out.println(methodName);

		if (param1 == null) {
			System.out.println("param1 = " + param1);
		} else {
			System.out.println("param1 = \"" + param1 + "\"");
		}
		System.out.println("param2 = " + param2);

		System.out.println(exceptionPackageAndClassName + " with message '"
				+ message + "'");
		System.out
				.println("--------------------------------------------------------------");
	}
	private static void printResult(String packageAndClassName,
			String remotetObjName, String methodName, String param1, int param2,
			String result) {
		System.out.println("--------------------------------------------------------------");
		System.out.println(packageAndClassName + " ('"+ remotetObjName + "')");
		System.out.println(methodName);
		
		if (param1 == null) {
			System.out.println("param1 = " + param1);
		} else {
			System.out.println("param1 = \"" + param1 + "\"");
		}
		System.out.println("param2 = " + param2);

		System.out.println("Return value = '" + result+"'");
		System.out.println("--------------------------------------------------------------");
	}
}
