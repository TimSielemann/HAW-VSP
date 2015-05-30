package nameservicetest;
import mware_lib.NameService;
import mware_lib.ObjectBroker;


public class NameServiceTest {

	/**
	 * @pre NameService muss laufen...
	 * @param args
	 */
	public static void main(String[] args) {
		
		ObjectBroker broker = new ObjectBroker("localhost", 25645, true);
		NameService ns = broker.getNameService();
		System.out.println("Rebind Object Tim");
		ns.rebind(new Object(), "Tim");
		Object resolved = ns.resolve("Tim");
		System.out.println("Resolved Object: " + resolved);
		
	}

}
