package de.ants.vsp.sender;

import java.io.IOException;

public class SendBuffer extends Thread implements ISendBuffer {

	private byte[] toSend;

	public SendBuffer() {
		this.toSend = new byte[23];
	}

	@Override
	public synchronized byte[] getData() {
		return this.toSend;
	}

	public byte[] getDataFromSource() {
		byte[] retAry = new byte[24];
		int i = 0;
		try {
			while (i <= 23) {
				// i = System.in.read(retAry);
				i += System.in.read(retAry, i, 24 - i);
			}

		} catch (IOException e) {
			// System.out.println("Keine, alte oder falsche Daten eingetroffen");
			e.printStackTrace();
		}
		return retAry;

	}

	public synchronized byte[] getToSend() {
		return toSend;
	}

	public synchronized void setToSend(byte[] toSend) {
		this.toSend = toSend;
	}

	@Override
	public void run() {
		super.run();
		while (!this.isInterrupted()) {
			this.setToSend(getDataFromSource());
		}
	}
}
