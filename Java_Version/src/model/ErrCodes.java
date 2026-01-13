/**
 * Model part of the application in charge of store the values from Faulty Ram Tester.
 * @author Silverio MRS. (aka Popopo,Lindyhop).
 * @version 1.0
 */
package model;

/**
 * The class contains the possible Error Codes produced.
 */
public enum ErrCodes {
	/** This error is produced when the arguments are not enough */
	NOT_ENOUGH_PARAMS,
	/** Produced when the number of arguments exceed the expected */
	TOOMANYPARAM,
	/** Produced when the expected hexadecimal Write value doesn't complains the format */
	WRONG_WRITE_VALUE,
	/** Produced when the expected hexadecimal Read value doesn't complains the format */
	WRONG_READ_VALUE,
	/** Produced when the expected hexadecimal Address value doesn't complains the format */
	WRONG_ADDRESS_VALUE,
	/** Produced when the expected QLRAM value doesn't complains the format @see model.TypeRamExpansion */
	WRONG_QLRAM_VALUE,
	/** Produced when the introduced parameter is not contained in the APP */
	UNKNOWN_PARAM;
	
	/**
	 * The method tells when the value is not contained into the list of valid Error Codes.
	 * @param value passed as String to check if it's contained.
	 * @return True when the value is valid. False otherwise.
	 */
    public static boolean contains(String value) {
        for (ErrCodes e : values()) {
            if (e.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
