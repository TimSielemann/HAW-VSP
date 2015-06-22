package de.ants.vsp.sender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import de.ants.vsp.receiver.Datensenke;
import de.ants.vsp.receiver.IReceiver;
import de.ants.vsp.receiver.Receiver;

public class Sender extends Thread implements ISender {

	private int slot;
	private int port;
	private InetAddress ip;
	private byte[] message = new byte[] {};
	private SendBuffer sendBuffer;
	private IReceiver empfaenger;
	private Datensenke datensenke;
	private char type;

	public Sender(String ip, int port, IReceiver rec, Datensenke datensenke,
			char type) throws UnknownHostException {
		this.slot = 0;
		this.ip = InetAddress.getByName(ip);
		this.port = port;
		this.sendBuffer = new SendBuffer();
		this.empfaenger = rec;
		this.datensenke = datensenke;
		sendBuffer.start();
		this.type = type;
	}

	// public Sender(SendBuffer sendbuf, Receiver rec, Datensenke datasenke)
	// throws UnknownHostException {
	// this.slot = 0;
	// this.ip = InetAddress.getByName("225.10.1.2");
	// this.port = 15007;
	// this.sendBuffer = sendbuf;
	// this.datensenke = datasenke;
	// this.empfaenger = rec;
	// }

	@Override
	public boolean isRightSlot() {
		if (this.slot < 1 || this.slot >= 25) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isCollusionFromReceiver() {
		return this.empfaenger.isCollision();
	}

	public void getDataFromBuffer() {
		this.message = this.sendBuffer.getData();
	}

	public long getTimeFromReceiver() {
		return this.empfaenger.getTime();
	}

	public int getSlotforNextFrameFromReceiver() {
		return this.empfaenger.getFreeSlotNextFrame();
	}

	@Override
	public void logCollusion() {
		this.datensenke.logCollision(true, this.slot);

	}

	@Override
	public void sendData() throws SocketException {
		try {
			Thread.sleep(Receiver.SPOTTIME / 4);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (isRightSlot()) {
			if (!isCollusionFromReceiver()) {
				// if (rightTimeToSend()) {
				try {
					byte[] toSend = this.prepareMessage();

					DatagramSocket socket = new DatagramSocket();
					socket.send(new DatagramPacket(toSend, this.message.length,
							this.ip, port));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// }
			}
		}
	}

	public byte[] prepareMessage() {
		byte[] toSend = new byte[34];
		byte[] message = this.sendBuffer.getData();
		int nextSlot = this.getSlotforNextFrameFromReceiver();
		Long time = this.getTimeFromReceiver();
		ByteBuffer bb = ByteBuffer.allocate(Long.BYTES);
		byte[] timeBytes = bb.putLong(time).array();
		toSend[0] = (byte) this.type;
		for (int i = 0; i < message.length; i++) {
			toSend[i + 1] = message[i];
		}
		toSend[25] = (byte) nextSlot;
		for (int i = 0; i < timeBytes.length; i++) {
			toSend[i + 26] = timeBytes[i];
		}
		return toSend;
	}

	@Override
	public synchronized void notifySender() {
		this.notify();
	}

	@Override
	public synchronized void run() {
		// TODO Auto-generated method stub
		super.run();
		while (true) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				this.sendData();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// private boolean rightTimeToSend() {
	// boolean flag=false;
	// long warteDauerSlot=this.slot*40l;
	// long time=this.getTimeFromReceiver();
	// if(time>time+warteDauerSlot)
	// return flag;
	//
	// }

}
