package de.ants.vsp.receiver;

public class ReceiveWrapObject {
	
	private byte[] msg;
	private long timeReceived;

	public ReceiveWrapObject(byte[] msg, long timeReceived) {
		super();
		this.msg = msg;
		this.timeReceived = timeReceived;
	}
	
	public byte[] getMsg() {
		return msg;
	}
	public void setMsg(byte[] msg) {
		this.msg = msg;
	}
	public long getTimeReceived() {
		return timeReceived;
	}
	public void setTimeReceived(long timeReceived) {
		this.timeReceived = timeReceived;
	}
}
