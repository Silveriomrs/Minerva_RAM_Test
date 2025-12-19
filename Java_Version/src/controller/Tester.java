/**
 * 
 */
package controller;

import model.TypeRamExpansion;
import model.Value;

/**
 * 
 */
public class Tester {
	public static final String RESET  = "\u001B[0m";
    public static final String RED    = "\u001B[31m";
    public static final String GREEN  = "\u001B[32m";
    
	final double BASE = 0x20000;							//Starting point for RAM after ROM area.
	private double TOP, MIDDLE;
	private Value write,read;
	private double address;
	private boolean example;
	private TypeRamExpansion typeramexp;
	private int[] binary;

	public Tester() {
		this.binary = new int[8];
		//setting default values (example)
		setExample();
	}
	
	public boolean setWrite(String v) {
		return write.setValue(v);
	}
	
	public boolean setRead(String v) {
		return read.setValue(v);
	}
	
	public boolean setAddress(double a) {
		address = a;
		return true;
	}
	
	private void calcTOPandMIDDLE() {
		//after setting example environment, set Top RAM according to it.
		setTop();
		//set the middle point.
		this.MIDDLE = getMiddle();
	}
	
	public int calcBIN() {
		calcTOPandMIDDLE();
		//Auxiliar array to store xor values between write and read.
		int cx[] = new int[4];
		//Decimal results from OR operation on the elements of cx array.
		int orv = 0;
		
		//Firstly for each byte of the lists do XOR: Ai XOR Bi
		for(int i = 0; i < 4 ; i++) {
			cx[i] = write.getDec(i) ^ read.getDec(i);
			//do OR with each par of values into CX
			orv = orv | cx[i];
		}
		
		//Let's start by the MSB
		for(int i = 7; i >= 0; i--) {
			this.binary[7-i] = (orv >> i) & 1;
		}
		
		return orv;
	}
	
	private void showFaultyICs() {
		String ic = "IC";
		// 8 ICs in high part of RAM or 8 ICs low part of RAM.
		int n = (address >= MIDDLE)? 16:8;
		//1 => BAD, 0 => GOOD
		for(int i = 0; i < 8; i++) {
			if(binary[i] == 1) System.out.println(ic + (n-i) + RED + " BAD" + RESET);
			else System.out.println(ic + (n-i) + GREEN + " GOOD" + RESET);
		}
		
	}
	
	public void showResults() {
		String b = "";
		System.out.println("Write " + write.toString());
		System.out.println("Read " + read.toString());
		
		for(int a : binary) {
			b += a;
		}
		
		System.out.println("System: " + typeramexp);
		System.out.println("Binary result: " + b);
		
		if(isOutInnerRAM()) System.out.println("Faulty ram is in expansion memory\n");
		else showFaultyICs();
		
	}
	
	public int[] getBinary() {
		return binary;
	}
	
	private void setExample() {
		this.example = true;
		this.write = new Value("548C4878");
		this.read = new Value("5CCD5CCD");
		this.address = 0x00032000;
		this.typeramexp = TypeRamExpansion.QL128;
	}
	
	public void setTypeRAM(TypeRamExpansion t) {
		this.typeramexp = t;
	}
	
	private TypeRamExpansion getTypeRAM() {
		return typeramexp;
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
		// Test1
//		tester.setRead("70014E7B");
//		tester.setWrite("70014F7B");
//		tester.setAddress(0x0000200C);
		//Test2
//		tester.setRead("2001E548");
//		tester.setWrite("2001E549");
//		tester.setAddress(0x00020000);
		//Test3 For 512K Fault IC : IC6
//		tester.setRead("FFFFFFFF");
//		tester.setWrite("FFFFFFDF");
//		tester.setAddress(0x00030060);
//		tester.setTypeRAM(TypeRamExpansion.QL512);
		//
		//Test For 512K Fault IC : IC8
		tester.setRead("FFFFFFFF");
		tester.setWrite("FF7FFF7F");
		tester.setAddress(0x00050808);
		tester.setTypeRAM(TypeRamExpansion.QL512);
		//
		tester.calcBIN();
		tester.showResults();
	}

}
