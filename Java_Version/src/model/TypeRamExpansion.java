package model;

public enum TypeRamExpansion {
	QL128,
	QL512,
	QL640;
	
	 public static boolean contains(String value) {
	        for (TypeRamExpansion e : values()) {
	            if (e.name().equals(value)) {
	                return true;
	            }
	        }
	        return false;
	 }
}
