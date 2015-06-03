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
}
