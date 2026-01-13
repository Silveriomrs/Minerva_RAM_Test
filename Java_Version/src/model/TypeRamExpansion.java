/**
 * Model part of the application in charge of store the valid amount of inner RAM defined as valid.
 * @author Silverio MRS. (aka Popopo,Lindyhop).
 * @version 1.0
 */
package model;

public enum TypeRamExpansion {
	/** Default value in the APP. This is the standard for a stock Sinclair QL with 128KB of installed RAM */
	QL128,
	/** When the internal amount of RAM is 512KB */
	QL512,
	/** When the internal amount of RAM is 640KB */
	QL640;
	
	
	/**
	 * The method tells when the value is not contained into the list of valid types of installed inner RAM.
	 * @param value passed as String to check if it is contained.
	 * @return True when the value is valid. False otherwise.
	 */
	 public static boolean contains(String value) {
	        for (TypeRamExpansion e : values()) {
	            if (e.name().equals(value)) {
	                return true;
	            }
	        }
	        return false;
	 }
}
