/**
 * 
 */
package controller;

import model.TypeRamExpansion;
import model.Value;

/**
 * This engine is configured to works with INTs whose size is at least 32bits.
 */
public class Tester {
	public static final String RESET  = "\u001B[0m";
    public static final String RED    = "\u001B[31m";
    public static final String GREEN  = "\u001B[32m";
    
	final int BASE = 0x20000;							//Starting point for RAM after ROM area.
	private int TOP, MIDDLE;
	private Value write,read;
	private int address;
	private TypeRamExpansion typeramexp;
	private int[] binary = new int[8];;

	public Tester() {
		//setting default values (example)
		setExample();
	}
	
	/**
	 * This constructor get the main datas by its interface.
	 * @param w Write code.
	 * @param r Read code.
	 * @param a involved address.
	 * @param t TypeRamExpansion defined in one of the option (128,512,640)
	 */
	public Tester(String w, String r, int a, TypeRamExpansion t) {
		setWrite(w);
		setRead(r);
		setAddress(a);
		setTypeRAM(t);
		calc();
	}
	
	public void setWrite(String v) {
		this.write = new Value(v);
	}
	
	public void setRead(String v) {
		this.read = new Value(v);
	}
	
	public void setAddress(int a) {
		this.address = a;
	}
	
	public void setTypeRAM(TypeRamExpansion t) {
		this.typeramexp = t;
	}
	
	public void calc() {
		//after setting example environment, set Top RAM according to it.
		setTop();
		//set the middle point.
		this.MIDDLE = getMiddle();
		calcBIN();
	}
	
	private int calcBIN() {
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
	
	public String getBinaryStr() {
		String b = "";
		for(int a : binary) { b += a; }
		return b;
	}
	
	public void showResults() {
		System.out.println("Write " + write.toString());
		System.out.println("Read " + read.toString());
		
		
		System.out.println("System: " + typeramexp);
		System.out.println("Binary result: " + getBinaryStr());
		
		if(isOutInnerRAM()) System.out.println(GREEN + "\nFaulty ram is in expansion memory\n" + RESET);
		else showFaultyICs();
		
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
	
	
	private void setExample() {
		this.write = new Value("548C4878");
		this.read = new Value("5CCD5CCD");
		this.address = 0x00032000;
		this.typeramexp = TypeRamExpansion.QL128;
		calc();
	}
	
	private TypeRamExpansion getTypeRAM() {
		return typeramexp;
	}
	
	private void setTop() {
		switch(typeramexp) {
		case QL128: TOP = 0x40000; break;
		case QL512: TOP = 0xA0000; break;
		case QL640: TOP = 0xC0000; break;
		default:
			TOP = 0x40000;
		}
	}
	
	private int getMiddle() {
		return (TOP - BASE)/2 + BASE;
	}
	
	private boolean isOutInnerRAM() {
		return this.address > TOP;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Tester tester = new Tester();

		//Test For 512K Fault IC : IC8
		tester.setRead("FFFFFFFF");
		tester.setWrite("FF7FFF7F");
		tester.setAddress(0x00050808);
		tester.setTypeRAM(TypeRamExpansion.QL512);
		//
		tester.calc();
		tester.showResults();
	}

}
