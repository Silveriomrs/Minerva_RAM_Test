package model;

public enum ErrCodes {
	NOT_ENOUGH_PARAMS,
	TOOMANYPARAM,
	WRONG_WRITE_VALUE,
	WRONG_READ_VALUE,
	WRONG_ADDRESS_VALUE,
	WRONG_QLRAM_VALUE,
	UNKNOWN_PARAM;
	

    public static boolean contains(String value) {
        for (ErrCodes e : values()) {
            if (e.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
