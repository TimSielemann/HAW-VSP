package tests;

import mware_lib.NameService;
import mware_lib.ObjectBroker;

public class ServerB extends Thread {

	private ObjectBroker broker;

	public ServerB(String host, int port, boolean b) {
		ClassTwoAccessorOne two = new ClassTwoAccessorOne();
		broker = new ObjectBroker(host, port, b);
		NameService ns = broker.getNameService();
		ns.rebind(two, "TwoAccessorOne");
	}
	
	public void shutdown(){
		broker.shutDown();
	}
	
	public static void main(String[] args){
		if (args.length < 3){
			args = new String[]{"localhost", "25645", "false"};
		}
		
		ServerB serverB = new ServerB(args[0], Integer.parseInt(args[1]), Boolean.parseBoolean(args[2]));
		serverB.start();
	}

}
