package de.ants.vsp.sender;

import java.io.IOException;

public class SendBuffer extends Thread implements ISendBuffer {

	private byte[] toSend;
	private boolean abgesendet;

	public SendBuffer() {
		this.toSend = new byte[23];
	}

	@Override
	public byte[] getData() {
		this.abgesendet = true;
		return this.toSend;
	}

	public byte[] getDataFromSource() {
		byte[] retAry = new byte[23];
		boolean flag = true;
		while (flag) {

			try {
				while (System.in.read() != -1) {
					System.in.read(retAry);
					if (System.in.read() == -1)
						flag = false;
				}
			} catch (IOException e) {
				// System.out.println("Keine, alte oder falsche Daten eingetroffen");
				e.printStackTrace();
			}
		}
		this.abgesendet = false;
		return retAry;

	}

	public byte[] getToSend() {
		return toSend;
	}

	public void setToSend(byte[] toSend) {
		this.toSend = toSend;
	}

	@Override
	public void run() {
		super.run();
		while (true) {
			while (this.abgesendet) {
				this.toSend = getDataFromSource();
			}
		}
	}
}
