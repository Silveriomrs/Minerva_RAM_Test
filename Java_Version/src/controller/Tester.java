/**
 * 
 */
package controller;

import model.TypeRamExpansion;

/**
 * 
 */
public class Tester {
	
	final double BASE = 0xA0000;							//Starting point for RAM after ROM area.
	double TOP, MIDDLE;
	double write,read,address;
	boolean example;
	TypeRamExpansion typeramexp;
	int results;
	int ix;

	public Tester() {
		this.results = 0;
		this.ix = 0;
		//setting default values (example)
		setExample();
		//after setting example environment, set Top RAM according to it.
		setTop();
		//set the middle point.
		this.MIDDLE = getMiddle();
	}
	
	private void setExample() {
		this.example = true;
		this.write = 0x548C4878;
		this.read = 0x5CCD5CCD;
		this.address = 0x00032000;
		this.typeramexp = getTypeRAM();

	}
	
	private TypeRamExpansion getTypeRAM() {
		return TypeRamExpansion.QL128;
	}
	
	private void setTop() {
		switch(typeramexp) {
		case QL512: TOP = 0xA0000; break;
		case QL640: TOP = 0xC0000; break;
		default:
			TOP = 0x40000;
		}

	}
	
	private double getMiddle() {
		return (TOP - BASE)/2 + BASE;
	}
	
	private boolean isOutInnerRAM() {
		return this.address > TOP;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tester tester = new Tester();
	}

}
