package accessor_one;

import accessor_one.SomeException112;
import mware_lib.RawObject;

public class ClassTwoImplBaseStub extends ClassTwoImplBase {

	private RawObject rawObject;

	public ClassTwoImplBaseStub(Object rawObjectRef) {
		this.rawObject = (RawObject) rawObjectRef;
	}

	@Override
	public int methodOne(double param1) throws SomeException110 {
		Object[] params = { param1 };
		Object result = rawObject.getKommModul().send(
				this.rawObject.getObjectReference(), "methodOne", params);
		if (result instanceof SomeException110) {
			if (this.rawObject.isDebug()) {
				printErrorOne("accessor_one.ClassTwoImplBaseStub",
						this.rawObject.getObjectReference().getName(),
						"methodOne", param1, "accessor_one.SomeException110",
						((SomeException110) result).getMessage());
			}
			throw ((SomeException110) result);
		} else {
			if (this.rawObject.isDebug()) {
				printResult("accessor_one.ClassTwoImplBaseStub", this.rawObject
						.getObjectReference().getName(), "methodOne", param1,
						(int) result);
			}
			return ((int) result);
		}
	}

	@Override
	public double methodTwo() throws SomeException112 {
		Object[] params = {};
		Object result = rawObject.getKommModul().send(
				this.rawObject.getObjectReference(), "methodTwo", params);
		if (result instanceof SomeException112) {
			if (this.rawObject.isDebug()) {
				printErrorTwo("accessor_one.ClassTwoImplBaseStub", this.rawObject
						.getObjectReference().getName(), "methodTwo",
						"accessor_one.SomeException112",
						((SomeException112) result).getMessage());
			}
			throw ((SomeException112) result);
		} else {
			if (this.rawObject.isDebug()) {
				printResult("accessor_one.ClassTwoImplBaseStub", this.rawObject
						.getObjectReference().getName(), "methodTwo", 
						(double) result);
			}
			return ((double) result);
		}
	}

	private static void printErrorOne(String packageAndClassName,
			String remotetObjName, String methodName, double param1,
			String exceptionPackageAndClassName, String message) {
		System.out
				.println("--------------------------------------------------------------");
		System.out.println(packageAndClassName + " ('" + remotetObjName + "')");
		System.out.println(methodName);
		System.out.println("param1 = " + param1);
		System.out.println(exceptionPackageAndClassName + " with message '"
				+ message + "'");
		System.out
				.println("--------------------------------------------------------------");
	}

	private static void printErrorTwo(String packageAndClassName,
			String remotetObjName, String methodName,
			String exceptionPackageAndClassName, String message) {
		System.out
				.println("--------------------------------------------------------------");
		System.out.println(packageAndClassName + " ('" + remotetObjName + "')");
		System.out.println(methodName);
		System.out.println(exceptionPackageAndClassName + " with message '"
				+ message + "'");
		System.out
				.println("--------------------------------------------------------------");
	}

	private static void printResult(String packageAndClassName,
			String remotetObjName, String methodName, double param1, int result) {
		System.out
				.println("--------------------------------------------------------------");
		System.out.println(packageAndClassName + " ('" + remotetObjName + "')");
		System.out.println(methodName);
		System.out.println("param1 = " + param1);
		System.out.println("Return value = " + result);
		System.out
				.println("--------------------------------------------------------------");
	}

	private static void printResult(String packageAndClassName,
			String remotetObjName, String methodName, double result) {
		System.out
				.println("--------------------------------------------------------------");
		System.out.println(packageAndClassName + " ('" + remotetObjName + "')");
		System.out.println(methodName);
		System.out.println("Return value = " + result);
		System.out
				.println("--------------------------------------------------------------");
	}
}
