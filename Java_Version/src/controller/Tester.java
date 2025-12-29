/**
 * 
 */
package controller;

import model.ErrCodes;
import model.TypeRamExpansion;
import model.Value;
import view.ShellMSG;

/**
 * This engine is configured to works with INTs whose size is at least 32bits.
 */
public class Tester {
	
	private ShellMSG shell = new ShellMSG();
	
	final static int BASE = 0x20000;							//Starting point for RAM after ROM area.
	private static int TOP;

	private static int MIDDLE;
	private Value write,read,address;
	private TypeRamExpansion typeramexp;
	private int[] binary = new int[8];

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
		shell.showInputs(write, read, address, "" + this.typeramexp);
		shell.showFaultyICs(address.getValue(), BASE, MIDDLE, TOP, binary);
	}
	
	public int[] getBinArray() {
		return this.binary;
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
			//this.address = Integer.parseInt(v, 16);
			this.address = new Value(v);;
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
		Tester.MIDDLE = getMiddle();
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
	
	private void setExample() {
		this.write = new Value("548C4878");
		this.read = new Value("5CCD5CCD");
		this.address = new Value("00032000");
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
	
	private boolean isInnerRAM() {
		return this.address.getValue() > TOP;
	}
	
	public boolean processArgs(String[] args) {
		int a = args.length;
		boolean color = true;
		boolean ok = true;
		
		if(a > 1 && args[a-1].equals("-nc")) {
			shell.setSwitch(args[a-1]);
			color = false;
		}
		
		System.out.println();
		
		switch(a) {
		case 0:
			shell.setSwitch("-m");
			break;
		case 1: //For optional switch.
			shell.setSwitch(args[0]);
			if(args[0].equals("-re")) showResults();
			break;
		case 2: //not enough parameters
			if(!color) shell.setSwitch(args[0]);
			else shell.showErr(ErrCodes.NOT_ENOUGH_PARAMS,"");
			break;
		case 3: //Default type of QLRAM is 128K from constructor: W R A
		case 4:	//Possible cases for 4 args: W R A M  or  W R A NC  
		case 5:	//the only case for NC switch and 5 args: W R A M NC	
			ok = setWrite(args[0].toUpperCase()) && ok;
			ok = setRead(args[1].toUpperCase()) && ok;
			ok = setAddress(args[2].toUpperCase()) && ok;
			//Set QL Ram amount based on the args size options.
			if((!color && a == 5)) {
				ok = setTypeRAM(args[3].toUpperCase()) && ok;
			} else if (color && a == 4) {
				ok = setTypeRAM(args[3].toUpperCase()) && ok;
			}
			
			if(ok) {
				calc();
				showResults();
			}
			
			break;
			default:
				shell.showErr(ErrCodes.TOOMANYPARAM,"");
		}

		return ok;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Create a object with default values example.
		Tester tester = new Tester();
		boolean ok = tester.processArgs(args);
		//TODO: Remove after testing
//		tester.typeramexp = TypeRamExpansion.QL512;
//		tester.calc();
//		//tester.shell.showAddressGraph(address.getValue(), BASE, MIDDLE, TOP);
//		tester.shell.setSwitch("-nc");
//		tester.shell.setSwitch("-e");
//		tester.showResults();
		
	}

}
