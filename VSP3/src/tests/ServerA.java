package tests;

import mware_lib.NameService;
import mware_lib.ObjectBroker;

public class ServerA extends Thread {
	
	
	private ObjectBroker broker;

	public ServerA(String host, int port, boolean b){
		ClassOneAccessorOne one = new ClassOneAccessorOne();
		broker = new ObjectBroker(host, port, b);
		NameService ns = broker.getNameService();
		ns.rebind(one, "OneAccessorOne");
		ClassOneAccessorTwo oneTwo = new ClassOneAccessorTwo();
		ns.rebind(oneTwo, "OneAccessorTwo");
		ClassTwoAccessorOneAsync async = new ClassTwoAccessorOneAsync();
		ns.rebind(async, "Async");
		ClassOneAccessorOneNull nullObject = new ClassOneAccessorOneNull();
		ns.rebind(nullObject, "Null");
		
	}
	
	public void shutdown(){
		broker.shutDown();
	}
	
	public static void main(String[] args){
		if (args.length < 3){
			args = new String[]{"localhost", "25645", "false"};
		}
		
		ServerA serverA = new ServerA(args[0], Integer.parseInt(args[1]), Boolean.parseBoolean(args[2]));
		serverA.start();
	}
}
