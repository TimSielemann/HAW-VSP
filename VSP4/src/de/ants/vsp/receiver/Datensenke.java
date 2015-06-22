package de.ants.vsp.receiver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Datensenke {
	private Logger logger;
	
	public Datensenke(String name) throws SecurityException, IOException{
		logger = Logger.getLogger("VSP");
		logger.setLevel(Level.ALL);
		FileHandler fh;  
		// This block configure the logger with handler and formatter  
        fh = new FileHandler("./" +  name +  ".log");  
		logger.addHandler(fh);
		SimpleFormatter formatter = new SimpleFormatter();  
		fh.setFormatter(formatter);  
	}
	
	public void dumpData(byte[] bytes, int spotNo){
		logger.info("Spot: " + spotNo + " Message Erhalten: " + this.getMessageForBytes(bytes));
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
			logger.info("Kollision erkannt (selbst beteiligt) im Spot: " + spotNo);
		}
		else {
			logger.info("Kollision erkannt (unbeteiligt) im Spot: " + spotNo);
		}
	}
	
	public void logNewTimeSet(long oldTime, long newTime){
		if (oldTime != newTime)
			logger.info("Time changed: " + oldTime + " -> " + newTime);
	}
	
	public void logMessage(String message){
		logger.info(message);
	}
}
