/**
 * 
 */
package controller;

import model.TypeRamExpansion;
import model.Value;
import view.ShellMSG;
import view.ErrCodes;

/**
 * This engine is configured to works with INTs whose size is at least 32bits.
 */
public class Tester {
	
	private ShellMSG shell = new ShellMSG();
    private static final String VERSION = "0.9";
	
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
	public Tester(String w, String r, String a, String t) {
		setWrite(w);
		setRead(r);
		setAddress(a);
		setTypeRAM(t);
		calc();
	}
	
	public void showResults() {
		shell.showInputs(write, read, "" + this.typeramexp, binaryToStr());
		shell.showFaultyICs(address, MIDDLE, binary, isOutInnerRAM());
	}
	
	private boolean isValidHEX(String h) {
		//Create a Value object to accomplish requirements.
		Value v = new Value(h); 
		//Lets pass the value and return the result.
		return v.isValid();
	}
	
	public boolean setWrite(String v) {
		boolean OK = isValidHEX(v);
		if(OK) {
			this.write = new Value(v);
		} else {
			shell.showErr(ErrCodes.WRONG_WRITE_VALUE,v);
		}
	
		return OK;
	}
	
	public boolean setRead(String v) {
		boolean OK = isValidHEX(v);
		if(OK) {
			this.read = new Value(v);
		} else {
			shell.showErr(ErrCodes.WRONG_READ_VALUE,v);
		}
		
		return OK;
	}
	
	public boolean setAddress(String v) {
		boolean OK = isValidHEX(v);
		
		if(OK) {
			this.address = Integer.parseInt(v, 16);
		} else {
			shell.showErr(ErrCodes.WRONG_ADDRESS_VALUE,v);
		}
		
		return OK;
	}
	
	public boolean setTypeRAM(String v) {
		boolean OK = TypeRamExpansion.contains(v);
	    
		if(OK) {
			this.typeramexp = TypeRamExpansion.valueOf(v);
		} else {
			shell.showErr(ErrCodes.WRONG_QLRAM_VALUE,v);
		}
		
		return OK;
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
	
	public String binaryToStr() {
		String b = "";
		for(int a : binary) { b += a; }
		return b;
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
		int a = args.length;
		boolean ok = true;
		
		switch(a) {
		case 1: //For optional switch.
			tester.shell.showHelp(args[0]);
			break;
		case 2: //not enough parameters
			tester.shell.showErr(ErrCodes.NOT_ENOUGH_PARAMS,"");
			break;
		case 3:
			ok = tester.setTypeRAM("QL128") && ok;
		case 4:
			ok = tester.setRead(args[0]) && ok;
			ok = tester.setWrite(args[1]) && ok;
			ok = tester.setAddress(args[2]) && ok;
			if(a == 4) ok = tester.setTypeRAM(args[4]) && ok;
			if(ok) {
				tester.calc();
				tester.showResults();
			}
			break;
			default:
				tester.shell.showAbout();
				tester.shell.showSyntax();
				tester.shell.showCredits();
		}
		
	}

}
