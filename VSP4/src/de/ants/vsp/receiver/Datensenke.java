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
	private FileHandler fh;
	private String name;
	
	public Datensenke(String name) throws SecurityException, IOException{
		logger = Logger.getLogger("VSP");
		logger.setLevel(Level.SEVERE);
		logger.setUseParentHandlers(false);
//		 This block configure the logger with handler and formatter  
        fh = new FileHandler("./" +  name +  ".log");  
		logger.addHandler(fh);
		SimpleFormatter formatter = new SimpleFormatter();  
		fh.setFormatter(formatter); 
		this.name = name + ": ";
	}
	
	public void dumpData(byte[] bytes, int spotNo){
		logger.info(this.name + "Spot: " + spotNo + " Message Erhalten: " + this.getMessageForBytes(bytes));
		//System.out.println("Spot: " + spotNo + " Message Erhalten: " + this.getMessageForBytes(bytes));
	}

	private String getMessageForBytes(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		if (bytes.length == 34){
			ByteBuffer bb = ByteBuffer.wrap(Arrays.copyOfRange(bytes, 26, 34));
			builder.append(System.getProperty("line.separator"));
			builder.append("Stationstyp: ");
			builder.append(new String(Arrays.copyOfRange(bytes, 0, 1)));
			builder.append(System.getProperty("line.separator"));
			builder.append("Message: ");
			builder.append(new String(Arrays.copyOfRange(bytes, 1, 24)));
			builder.append(System.getProperty("line.separator"));
			builder.append("Next Reserved Slot: ");
			builder.append(bytes[25] + "");
			builder.append(System.getProperty("line.separator"));
			builder.append("Timestamp: ");
			builder.append(bb.getLong() + "");
			builder.append(System.getProperty("line.separator"));			
		}
		return builder.toString();
	}
	
	public void logCollision(boolean own, int spotNo){
		if (own){
			logger.severe(this.name + "Kollision erkannt (selbst beteiligt) im Spot: " + spotNo);
		}
		else {
			logger.severe(this.name + "Kollision erkannt (unbeteiligt) im Spot: " + spotNo);
		}
	}
	
	public void logNewTimeSet(long oldTime, long newTime){
		if (oldTime != newTime)
			logger.severe(this.name + "Time changed: " + oldTime + " -> " + newTime);
	}
	
	public void logMessage(String message){
		logger.info(this.name + message);
	}
	
	public void logMessageSevere(String message){
		logger.severe(this.name + message);
	}
	
	public void close(){
		this.fh.close();
	}
}
