package applications;

import accessor_two.ClassOneImplBase;
import accessor_two.SomeException112;
import accessor_two.SomeException304;
import mware_lib.*;

public class App1 {
	
	private static final String OBJ_Name1 = "obj1";
	private static final String OBJ_Name2 = "obj2";

	public static void main(String[] argv) throws SomeException112, SomeException304 {

		Obj1 o1 = new Obj1();
		Obj3 o3 = new Obj3();
		ObjectBroker ob = ObjectBroker.init(argv[0], Integer.valueOf(argv[1]), Boolean.valueOf(argv[2]));
		NameService ns = ob.getNameService();
		ns.rebind(o1, "obj1");
		ns.rebind(o3, "obj3");
		
//		Object raw = ns.resolve("obj3");
//		ClassOneImplBase remoteObj = ClassOneImplBase.narrowCast(raw);
//		System.out.println(remoteObj.methodOne("test", new Double(null)));
//		System.out.println(remoteObj.methodTwo("test", 5.0));
	}

}
