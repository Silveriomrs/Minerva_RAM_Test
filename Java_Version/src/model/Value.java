/**
 * Model part of the application in charge of store the values from Faulty Ram Tester.
 * @author Silverio MRS. (aka Popopo,Lindyhop).
 * @version 1.0
 */
package model;

/**
 * This class store one hexadecimal value and gives it in different formats as needs.
 */
public class Value {
	
	/** Hexadecimal number stored by pairs of hex digits (4 pairs) */
	private byte[] hexList;
	/** Hexadecimal number stored by pairs of decimal number equivalent (4 pairs) */
	private int[] decList;
	/** Hexadecimal number contained */
	private int value;
	/** Flag to indicate if the stored number is valid or not */
	private boolean valid = false;
	
	
	/**
	 * Constructor for the Value Class with a string parameter that represent an unsigned hexadecimal number.
	 * @param value hexadecimal in String format representing an unsigned number.
	 */
	public Value(String value) {
		this.hexList = new byte[4];
		this.decList = new int[4];
		this.valid = setValue(value);
	}	
	
	/**
	 * The function gives the hexadecimal number stored in a position of the array.
	 * @param index of the array to read its value.
	 * @return hexadecimal number into the referenced position and size of a byte. 
	 */
	public byte getHex(int index) {
		return this.hexList[index];
	}
	
	/**
	 * The function gives the decimal number stored in a position of the array.
	 * @param index of the position to read.
	 * @return decimal number accessed whose size is an int.
	 */
	public int getDec(int index) {
		return this.decList[index];
	}
	
	/**
	 * The function returns if the stored value is a valid hexadecimal number one or not.
	 * @return True when it's valid. False otherwise.
	 */
	public boolean isValid() {
		return this.valid;
	}
	
	/**
	 * This function gives back the stored value in Hex format.
	 * @return the hexadecimal number.
	 */
	public int getValue() {
		return this.value;
	}
		
	/**
	 * The function acquires the unsigned hexadecimal number passed into a String, check if it is a valid number,
	 *  and then set the corresponding attributes (fields) arrays properly.
	 *  It returns a boolean value when the number is valid and the process is done.
	 * @param v hexadecimal unsigned number of 8 digits length (4 bytes).
	 * @return True if the process was successful. Otherwise false.
	 */
	private boolean setValue(String v) {		
		//Check no null
		boolean done = v != null;
		//Remove spaces
		String aux = v.trim();
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
		
		this.value = Integer.parseUnsignedInt(v, 16);
		
		return done;
	}
	
	/**
	 * This method overwrite the toString function and conforms the number in an easy visual format.
	 *  The generated strings is returned with a Carry Return code.
	 */
	public String toString() {
		String state ="";
		StringBuilder hex = new StringBuilder();
		
		for(int i = 0; i < 4 ; i++) {
			hex.append(String.format("%02X ", hexList[i] & 0xFF));
		}
		
		state += hex.toString() + "\n";
		
		return state;
	}
}
