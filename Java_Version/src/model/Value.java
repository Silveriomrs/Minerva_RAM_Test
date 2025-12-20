/**
 * 
 */
package model;

/**
 * 
 */
public class Value {
	
	private byte[] hexList;
	private int[] decList;
	private boolean valid = false;
	
	public Value(String value) {
		this.hexList = new byte[4];
		this.decList = new int[4];
		this.valid = setValue(value);
	}
	
	public byte getHex(int index) {
		return this.hexList[index];
	}
	
	public int getDec(int index) {
		return this.decList[index];
	}
	
	public boolean isValid() {
		return this.valid;
	}
	
	private boolean setValue(String value) {		
		//Check no null
		boolean done = value != null;
		//Remove spaces
		String aux = value.trim();
		//Check length == 4 bytes => 8 chars
		done = aux.length() == 8;
		//When it goes wrong stop and return false operation.
		if(!done) return done;
		
		//Convert each byte into Hex and dec.
		for (int i = 0; i<aux.length() ; i+=2) {
			//Take the the byte aimed by the index
			//Convert to hex and store it
			int high = Character.digit(aux.charAt(i), 16);
	        int low  = Character.digit(aux.charAt(i + 1), 16);

	        if (high < 0 || low < 0) {
	            return false;
	        }

			hexList[i/2] = (byte) ((high << 4) + low);
			//convert to dec and store it
			decList[i/2] = hexList[i/2] & 0xFF;
		}
		
		return done;
	}
	
	public String toString() {
		//TODO: Clean commented old sentences.
		String state ="value: ";
		//String decstr = "Decimal value: ";
		StringBuilder hex = new StringBuilder();
		
		for(int i = 0; i < 4 ; i++) {
			hex.append(String.format("%02X ", hexList[i] & 0xFF));
			//decstr += decList[i] + " ";			
		}
		
		state += hex.toString() + "\n";
		state += "Valid? : " + (isValid()? "yes":"no") + "\n";
		
		return state;
	}
}
