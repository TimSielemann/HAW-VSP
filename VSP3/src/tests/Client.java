package tests;

import accessor_one.ClassOneImplBase;
import accessor_one.ClassTwoImplBase;
import accessor_one.SomeException110;
import accessor_one.SomeException112;
import accessor_two.SomeException304;
import mware_lib.ObjectBroker;

public class Client {

	private ObjectBroker broker;

	public Client(String host, int port, boolean b) {
		broker = new ObjectBroker(host, port, b);
	}

	public String classOneAccessorOneMethodOne() throws SomeException112 {
		Object raw = broker.getNameService().resolve("OneAccessorOne");
		ClassOneImplBase base = ClassOneImplBase.narrowCast(raw);
		return base.methodOne("Hello", 21);
	}

	public String classOneAccessorOneMethodOneError() throws SomeException112 {
		Object raw = broker.getNameService().resolve("OneAccessorOne");
		ClassOneImplBase base = ClassOneImplBase.narrowCast(raw);
		return base.methodOne("Hello", -1);
	}

	public double classTwoAccessorOneMethodOne() throws SomeException110 {
		Object raw = broker.getNameService().resolve("TwoAccessorOne");
		ClassTwoImplBase base = ClassTwoImplBase.narrowCast(raw);
		return base.methodOne(1);
	}

	public double classTwoAccessorOneMethodOneError() throws SomeException110 {
		Object raw = broker.getNameService().resolve("TwoAccessorOne");
		ClassTwoImplBase base = ClassTwoImplBase.narrowCast(raw);
		return base.methodOne(-1);
	}

	public double classTwoAccessorOneMethodTwo() throws SomeException112,
			SomeException110 {
		Object raw = broker.getNameService().resolve("TwoAccessorOne");
		ClassTwoImplBase base = ClassTwoImplBase.narrowCast(raw);
		base.methodOne(2);
		return base.methodTwo();
	}

	public double classTwoAccessorOneMethodTwoError() throws SomeException112,
			SomeException110 {
		Object raw = broker.getNameService().resolve("TwoAccessorOne");
		ClassTwoImplBase base = ClassTwoImplBase.narrowCast(raw);
		base.methodOne(0);
		return base.methodTwo();
	}

	public double classOneAccessorTwoMethodOne()
			throws accessor_two.SomeException112 {
		Object raw = broker.getNameService().resolve("OneAccessorTwo");
		accessor_two.ClassOneImplBase base = accessor_two.ClassOneImplBase
				.narrowCast(raw);
		return base.methodOne("bla", 1);
	}

	public double classOneAccessorTwoMethodOneError()
			throws accessor_two.SomeException112 {
		Object raw = broker.getNameService().resolve("OneAccessorTwo");
		accessor_two.ClassOneImplBase base = accessor_two.ClassOneImplBase
				.narrowCast(raw);
		return base.methodOne("", 1);
	}

	public double classOneAccessorTwoMethodTwo()
			throws accessor_two.SomeException112, SomeException304 {
		Object raw = broker.getNameService().resolve("OneAccessorTwo");
		accessor_two.ClassOneImplBase base = accessor_two.ClassOneImplBase
				.narrowCast(raw);
		return base.methodTwo("bla", 1);
	}

	public double classOneAccessorTwoMethodTwoError112()
			throws accessor_two.SomeException112, SomeException304 {
		Object raw = broker.getNameService().resolve("OneAccessorTwo");
		accessor_two.ClassOneImplBase base = accessor_two.ClassOneImplBase
				.narrowCast(raw);
		return base.methodTwo("", 1);
	}

	public double classOneAccessorTwoMethodTwoError304()
			throws accessor_two.SomeException112, SomeException304 {
		Object raw = broker.getNameService().resolve("OneAccessorTwo");
		accessor_two.ClassOneImplBase base = accessor_two.ClassOneImplBase
				.narrowCast(raw);
		return base.methodTwo("error", 1);
	}

	public boolean testAsync() throws SomeException112 {
		Object raw = broker.getNameService().resolve("Async");
		ClassTwoImplBase base = ClassTwoImplBase.narrowCast(raw);
		Object raw1 = broker.getNameService().resolve("Async");
		ClassTwoImplBase base2 = ClassTwoImplBase.narrowCast(raw1);
		Thread a = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					base.methodOne(1);
				} catch (SomeException110 e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		a.start();
		base2.methodTwo();
		boolean retValue = a.isAlive();

		try {
			a.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retValue;
	}

	public void shutdown() {
		this.broker.shutDown();
	}

	public Object testNull() throws SomeException112 {
		Object raw = broker.getNameService().resolve("Null");
		ClassOneImplBase base = ClassOneImplBase.narrowCast(raw);
		String a = null;
		return base.methodOne(a, 21);
	}
}
