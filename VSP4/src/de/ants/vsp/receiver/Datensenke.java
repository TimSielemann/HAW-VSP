package de.ants.vsp.receiver;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Datensenke {
	
	
	public void dumpData(byte[] bytes, int spotNo){
		System.out.println("Spot: " + spotNo + " Message Erhalten: " + this.getMessageForBytes(bytes));
	}

	private String getMessageForBytes(byte[] bytes) {
		ByteBuffer bb = ByteBuffer.wrap(Arrays.copyOfRange(bytes, 26, 33));
		StringBuilder builder = new StringBuilder();
		builder.append("Stationstyp: ");
		builder.append(bytes[0]);
		builder.append("\\n");
		builder.append("Message: ");
		builder.append(Arrays.copyOfRange(bytes, 1, 24));
		builder.append("\\n");
		builder.append("Next Reserved Slot: ");
		builder.append(bytes[25]);
		builder.append("\\n");
		builder.append("Timestamp: ");
		builder.append(bb.getLong());
		builder.append("\\n");
		return builder.toString();
	}
	
	public void logCollision(boolean own, int spotNo){
		if (own){
			System.out.println("Kollision erkannt (selbst beteiligt) im Spot: " + spotNo);
		}
		else {
			System.out.println("Kollision erkannt (unbeteiligt) im Spot: " + spotNo);
		}
	}
	
	public void logNewTimeSet(long oldTime, long newTime){
		if (oldTime != newTime)
			System.out.println("Time changed: " + oldTime + " -> " + newTime);
	}
}
