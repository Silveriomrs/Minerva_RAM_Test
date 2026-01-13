/**
 * Controller part of the application in charge of decoding the 3 values from Faulty Ram Tester.
 * This Class implement the algorithm written by Dominic Brown (1990) for the Sinclair QL.
 * @author Silverio MRS. (aka Popopo,Lindyhop).
 * @version 1.0
 */
package controller;

import model.ErrCodes;
import model.TypeRamExpansion;
import model.Value;
import view.ShellMSG;

/**
 * This engine is configured to works with INTs whose size is at least 32bits.
 */
public class Decoder {
	
	/** Instance of the view part for console. */
	private ShellMSG shell = new ShellMSG();
	/** Starting point for RAM after ROM area. */
	final static int BASE = 0x20000;
	/** Last Address of the inner RAM zone */
	private static int TOP;
	/** Middle point of the inner RAM area address */
	private static int MIDDLE;
	/** Hexadecimal code from test to decode Write data, Read data and Address of the issue */
	private Value write,read,address;
	/** Amount of inner RAM installed on the PC {128,512,640} KB */
	private TypeRamExpansion typeramexp;
	/** Binary array with marking the faulty and right ICs */
	private int[] binary = new int[8];
	/** Flag to indicate if all ICs are right or not */
	private boolean allICfine = true;							// Flag to mark case when all ICs are OK.
	
	/**
	 * Constructor to set an example for this instance.
	 */
	public Decoder() {
		//setting default values (example)
		setExample();
	}
	
	/**
	 * This constructor get the main data by its interface. Mainly it gets the arguments passed by parameters.
	 * @param w Write code.
	 * @param r Read code.
	 * @param a involved address.
	 * @param t TypeRamExpansion defined in one of the option (128,512,640)
	 */
	public Decoder(String w, String r, String a, String t) {
		setWrite(w);
		setRead(r);
		setAddress(a);
		setTypeRAM(t);
		calc();
	}
	
	/**
	 * The method is used to show results by the defined output.
	 */
	public void showResults() {
		shell.showInputs(write, read, address, "" + this.typeramexp);
		
		if(!allICfine) {
			shell.showFaultyICs(address.getValue(), BASE, MIDDLE, TOP, binary);
		}else {
			shell.showNoFaultyRAM(address.getValue(), BASE, MIDDLE, TOP);
		}
		
	}
	
	/**
	 * It gives the binary array calculated.
	 * @return the binary resulted from the calc where 1 identify a faulty IC and 0 a IC not wrong.
	 */
	public int[] getBinArray() {
		return this.binary;
	}
	
	/**
	 * It checks if the Hexadecimal value is a valid one or not.
	 * @param h String that contains an hexadecimal number.
	 * @return True if the value is valid. False otherwise.
	 */
	private boolean isValidHEX(String h) {
		//Create a Value object to accomplish requirements.
		Value v = new Value(h);
		//Lets pass the value and return the result.
		return v.isValid();
	}
	
	/**
	 * Set the Write value code introduced into a String. If it fails shows a error message.
	 * @param v String that represent the hexadecimal code to set.
	 * @return True if the operation was done right, false otherwise.
	 */
	public boolean setWrite(String v) {
		boolean OK = isValidHEX(v);
		if(OK) {
			this.write = new Value(v);
		} else {
			shell.showErr(ErrCodes.WRONG_WRITE_VALUE,v);
		}
	
		return OK;
	}
	
	/**
	 * Set the Read value code introduced into a String. If it fails shows a error message.
	 * @param v String that represent the hexadecimal code to set.
	 * @return True if the operation was done right, false otherwise.
	 */
	public boolean setRead(String v) {
		boolean OK = isValidHEX(v);
		if(OK) {
			this.read = new Value(v);
		} else {
			shell.showErr(ErrCodes.WRONG_READ_VALUE,v);
		}
		
		return OK;
	}
	
	/**
	 * Set the Address value code introduced into a String. If it fails shows a error message.
	 * @param v String that represent the hexadecimal code to set.
	 * @return True if the operation was done right, false otherwise.
	 */
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
	
	/**
	 * Set the optional QL type RAM value code introduced into a String. If it fails shows a error message.
	 * This parameter is optional and when omitted the APP takes 128K as default value.
	 * @param v String that represent the hexadecimal code to set.
	 * @return True if the operation was done right, false otherwise.
	 */
	public boolean setTypeRAM(String v) {
		boolean OK = TypeRamExpansion.contains(v);
	    
		if(OK) {
			this.typeramexp = TypeRamExpansion.valueOf(v);
		} else {
			shell.showErr(ErrCodes.WRONG_QLRAM_VALUE,v);
		}
		
		return OK;
	}
	
	/**
	 * The method initiate the calculation of the binary result and it set it on the 
	 *  corresponded field. Also it initiate the other internal attributes related with the RAM.
	 */
	public void calc() {
		//after setting example environment, set Top RAM according to it.
		setTop();
		//set the middle point.
		Decoder.MIDDLE = getMiddle();
		calcBIN();
	}
	
	/**
	 * The main function to calculate the binary array of 8 positions with the results of faulty ICs.
	 *  Each position of the array correspond with an IC, when the value on that cell with a value of 0 or 1.
	 *   When the value is 0 the IC is right, when it is 1 the corresponded IC is faulty.
	 * @return The calculated binary array of 8 positions. Position with '1' means a faulty IC, '0' not faulty IC.
	 */
	private int calcBIN() {
		//Restart AllICfine to true.
		allICfine = true;
		//Auxiliary array to store xor values between write and read.
		int cx[] = new int[4];
		//Decimal results from OR operation on the elements of cx array.
		int orv = 0;
		
		//Firstly for each byte of the lists do XOR: Ai XOR Bi
		for(int i = 0; i < 4 ; i++) {
			cx[i] = write.getDec(i) ^ read.getDec(i);
			//do OR with each par of values into CX
			orv = orv | cx[i];
		}
		
		//Let's start by the MSB to store the bits
		for(int i = 7; i >= 0; i--) {
			binary[7-i] = (orv >> i) & 1;
			//Mark if ANY of the IC is faulty. First state = true
			if(allICfine && (binary[7-i] == 1)) allICfine = false;
		}
		
		return orv;
	}
	
	/**
	 * Method to set and calculate an demo example up with Write, Read and Address values to decode. Also set the optional value
	 *  of amount inner RAM to 128KB.
	 */
	private void setExample() {
		this.write = new Value("548C4878");
		this.read = new Value("5CCD5CCD");
		this.address = new Value("00032000");
		this.typeramexp = TypeRamExpansion.QL128;
		calc();
	}
	
	/**
	 * The function returns the type of inner RAM defined in the studied QL.
	 * @return
	 */
	private TypeRamExpansion getTypeRAM() {
		return typeramexp;
	}
	
	/**
	 * The method sets the Top address of RAM present in the QL based on the defined amount of RAM.
	 */
	private void setTop() {
		switch(typeramexp) {
		case QL128: TOP = 0x40000; break;
		case QL512: TOP = 0xA0000; break;
		case QL640: TOP = 0xC0000; break;
		default:
			TOP = 0x40000;
		}
	}
	
	/**
	 * The method returns the address middle point of the whole internal amount of RAM.
	 * @return Hexadecimal address of the internal amount of RAM.
	 */
	private int getMiddle() {
		return (TOP - BASE)/2 + BASE;
	}
	
	/**
	 * The method calculate if the issued address is outside of the internal installed amount of RAM.
	 * @return True if the issue is located into the inner RAM, false otherwise (expansion ram).
	 */
	private boolean isInnerRAM() {
		return this.address.getValue() > TOP;
	}
	
	/**
	 * Main function for console version. It process the arguments introduced from the console by the user and returns the results of the processing.
	 * 	The arguments are optional and can be combined. The arguments are described in the view class.
	 * @see view.Shell.MSG
	 * @param args [OPTION] [WRITE,READ,ADDRESS] [QLTYPEMOD] [-NC]
	 * @param [OPTION] optional switch that will show information about related with the APP.
	 * @param [WRITE,READ,ADDRESS] codes given by the RAM test (usually given by Minerva ROM) in hexadecimal of 8 numbers.
	 * @param [QLTYPEMOD] it defines the installed inner RAM on the QL placed on ICs layout they may be 128KB, 512KB or 640KB,
	 *  the default value when omitted is 128KB written as QL128, QL512 and QL640 respectively.
	 * @param [-NC] No color parameter, it shows all the information without ANSI colors codes.
	 * @return True if the process didn't face problem. Otherwise false.
	 */
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
			if(!color && args[0].equals("-re")) showResults();   		//No color and run example
			else if(!color) shell.setSwitch(args[0]);					//No color and a information parameter
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
	 * Function to keep internal tests of the APP without messing around with the main code.
	 */
	public void test() {
		//Test special case all RAM is OK
		write = new Value("548C4878");
		read = new Value("548C4878");
		address = new Value("00032000");	
		//
//		tester.typeramexp = TypeRamExpansion.QL512;
		calc();
//		shell.showAddressGraph(address.getValue(), BASE, MIDDLE, TOP);
//		shell.setSwitch("-nc");
//		shell.setSwitch("-e");
		showResults();
	}
	
	/**
	 * Launcher of the APP.
	 * @param args [OPTION] [WRITE,READ,ADDRESS] [-NC] 
	 */
	public static void main(String[] args) {
		//Create a object with default values example.
		Decoder tester = new Decoder();
		boolean ok = tester.processArgs(args);
		
		//For testing purpose. Comment/de-comment.
//		tester.test();
		
	}

}
