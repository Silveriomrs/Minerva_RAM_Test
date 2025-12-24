/**
 * 
 */
package view;

import model.ErrCodes;
import model.Value;

/**
 * 
 */
public class ShellMSG {
	
    private static final String VERSION = "0.93";

	public static final String RESET  = "\u001B[0m";
    public static final String RED    = "\u001B[31m";
    public static final String GREEN  = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BOLD = "\033[1m";
    public static final String UNDERLINE = "\033[4m";
    public static final String RED_BGD = "\u001B[41m"; 							// Red background
	
	public ShellMSG() {
		
	}
	
	public String showValue(String v, boolean isValid) {
		String c = (isValid)? (GREEN + BOLD) : (RED + BOLD); 
		String txt = c + v + RESET;
		return txt;
	}
	
	public void showErr(ErrCodes e, String value) {
		String txt = "";
		showValue(value,true);
		
		switch(e) {
		case NOT_ENOUGH_PARAMS: txt = RED + BOLD + "Not enough parameters"; break;
		case UNKNOWN_PARAM: txt = " : Unknown paramater"; break;
		case WRONG_WRITE_VALUE: txt = " : Wrong WRITE value"; break;
		case WRONG_READ_VALUE: txt = " : Wrong READ value"; break;
		case WRONG_ADDRESS_VALUE: txt = " : Wrong ADDRESS value"; break;
		case WRONG_QLRAM_VALUE: txt = " : Wrong QLRAM value"; break;
		default:
			txt = RED + BOLD + "\nToo many parameters\n" + RESET + "use" + GREEN + 
			BOLD + " -e " + RESET + "argument to get some valid examples.";
		}
		
		System.out.println(txt);
	}
	
	
	public void showInputs(Value write, Value read, Value address, String typeramexp) {
		String txt = BOLD + UNDERLINE + "\nINPUTS\n" + RESET;
		txt += "\nSystem:  " + YELLOW + typeramexp + RESET;
		txt += "\nWrite:   " + showValue(write.toString(), write.isValid());
		txt += "Read:    " + showValue(read.toString(), read.isValid());
		txt += "Address: " + showValue(address.toString(), address.isValid());	
		System.out.println(txt);
	}
	
	
	private void showOptions() {
		String txt = YELLOW + BOLD + UNDERLINE + "\nOptions\n" + RESET;
		txt += "java -jar ramtestdec.jar [OPTION] \n";
		txt += BOLD + "\n-a    : " + RESET + " About this application.";
		txt += BOLD + "\n-c    : " + RESET + " Shows the credits.";
		txt += BOLD + "\n-e    : " + RESET + " Shows some examples.";
		txt += BOLD + "\n-h    : " + RESET + " Shows full help.";
		txt += BOLD + "\n-o    : " + RESET + " Shows the options.";
		txt += BOLD + "\n-r    : " + RESET + " Run the APP with default example values.";
		txt += BOLD + "\n-s    : " + RESET + " Shows the syntax.";
		txt += BOLD + "\n-v    : " + RESET + " Shows the version.";
		System.out.println(txt);
	}
	
	private void showFullHelp() {
		showAbout();
		showVersion();
		showCredits();
		showSyntax();
		showOptions();
		showExamples();
	}
	
	private void showMinHelp() {
		showAbout();
		showSyntax();
	}
	
	public void showHelp(String v) {
		switch(v){
		case "-a" : showAbout(); break;
		case "-c" : showCredits(); break;										//Credits
		case "-e" : showExamples() ; break;										//Examples
		case "-h" : showFullHelp() ; break;										//Full  help
		case "-m" : showMinHelp(); break;
		case "-o" : showOptions() ; break;			
		case "-r" :  break;													    //Run the default example. No need nothing here.
		case "-s" : showSyntax() ; break;										//Syntax
		case "-v" : showVersion(); break;										//Version
		default:
			showErr(ErrCodes.UNKNOWN_PARAM,v);
		}
	}
	
	private void showSyntax() {
		String txt = YELLOW + BOLD + UNDERLINE + "\nSyntax\n" + RESET;
		txt += "java -jar ramtestdec.jar [OPTION] [WRITE READ ADDRESS] [QLRAM]";
		txt += "\nFor help type: " + BOLD + GREEN + "java -jar ramtestdec.jar -h\n" + RESET;
		System.out.println(txt);
	}
	
	private void showVersion() {
		String txt = BOLD + YELLOW + "\nVersion: " + GREEN + VERSION + RESET;
		System.out.println(txt);
	}
	
	private void showAbout() {
		String txt = YELLOW + BOLD + UNDERLINE + "\nAbout\n" + RESET;
		txt += "This aplication has the purpose of decoding the 3 hexadecimal codes returned by the Minerva ROM,"+
		"\nalso by the basic version of the Ram Tester Software programed by " + BOLD +"Dominic Brown." + RESET;
		txt += "\n\nTo report a bug, check new versions or any suggestion:"
				+ GREEN + "\nhttps://github.com/Silveriomrs/Minerva_RAM_Test";
		System.out.println(txt);
	}
	
	private void showCredits() {
		String txt = BOLD + YELLOW + UNDERLINE + "\nCredits\n" + RESET;
		txt += "The Original Program (ram3_ramfail.bas) was written for the SINCLAIR QL in SUPER BASIC language by"
				+ BOLD + " Dominic Brown (1990)." + RESET + "\nConverted in PHP language by " + BOLD + "Xad/Nightfall (8/5/2015)."
				+ RESET + " Update by Popopo to include the upgraded models (09/04/2025).";
		txt += "\nNow it's ported to Java TM for multiplatform purpose by" + BOLD + " Popopo (a.k.a Lindyhop) (21/04/2025)";
		System.out.println(txt);
	}
	
	private void showExamples() {
		String txt = "\nThe Ram Test Decoder has" + BOLD + " 3 workings modes, " + RESET + " let's see them with 3 examples:";
		txt += GREEN + BOLD + "\n\nExample 1: No arguments\n" + RESET;
		txt += "Shows information about the tool\n";
		txt += YELLOW + "java -jar ramtestdec.jar" + RESET;
		//
		txt += GREEN + BOLD + "\n\nExample 2: 3 arguments {WRITE, READ, ADDRESS} of 8 hexadecimal numbers each\n" + RESET;
		txt += "They are the main 3 codes, " + UNDERLINE + "all of them must be present " + RESET + "in order to decode them.\n";
		txt += YELLOW + "java -jar ramtestdec.jar 70014E7B 70014F7B 0000200C" + RESET;
		//
		txt += GREEN + BOLD + "\n\nExample 3: 4 arguments, same than example 3 adding the QL extra inner expansion" + RESET;
		txt += "\nThe fourth argument is optional and could be: (default) QL128 , QL512 , QL640\n";
		txt += "\nwhen it is not present the decoder takes the most common setup for Sinclair QL with 128KB of RAM\n";
		txt += YELLOW + "java -jar ramtestdec.jar 70014E7B 70014F7B 000200C" + GREEN + " QL512" + RESET;
		System.out.println(txt);

	}
	
	public String binaryToStr(int[] binary) {
		String b = "";
		for(int a : binary) { b += a; }
		return b;
	}
	
	private String getMaxRAM(int top) {
		String txt = BOLD;
		if(top == 0x40000) {
			//128K => ceil at 40000H (default)
			txt += "128K";
		}else if(top == 0xA0000) {
			//512K => ceil at A0000H
			txt += "512K";
		}else if(top == 0xC0000) {
			//640K => ceil at C0000H
			txt += "640K";
		}else {
			
		}
		
		return txt;
	}
	
	private String showAddressGraph(int addr, int bas, int mid, int top) {
		String txt = "";
		String a = String.format("%05Xh",addr);
		String b = String.format("%05Xh",bas);
		String m = String.format("%05Xh",mid);
		String t = String.format("%05Xh",top);
		
		txt += GREEN + a + RESET;
		
		if(addr >= mid && addr <= top) {
			txt += " > " + YELLOW + m + "\n" + RESET;
		}else if (addr > top) {
			txt += " > " + YELLOW + t + "\n" + RESET;
		}else if (addr == mid) {
			txt += " = " + YELLOW + m + "\n" + RESET;
		}else {
			txt += " < " + YELLOW + m + "\n" + RESET;
		}
		
		txt += "\n";
		txt +=  "--|--|--|--|--|- \n";
		txt += "|" + ((addr > top)? RED_BGD:RESET) + "              " + RESET + "| \n";

		txt += "|" + ((addr > top)? RED_BGD:RESET) + "   EXTERNAL   " + RESET + "| \n";
		txt += "|" + ((addr > top)? RED_BGD:RESET) + "     RAM      " + RESET + "| \n";
		txt += "================ " + t + "\n";	
		txt += "|" + (((addr > mid) && (addr < top) )? RED_BGD:RESET) + "    ("+ getMaxRAM(top) + ")    " + RESET + "| \n";
		txt += "|" + (((addr > mid) && (addr < top) )? RED_BGD:RESET) + "  [HIGH RAM]  " + RESET + "| \n";
		txt += "|" + (((addr > mid) && (addr < top) )? RED_BGD:RESET) + "              " + RESET + "| \n";
		txt += "-" + ((addr == mid )? RED_BGD:RESET) + "--------------- " + m + RESET + "\n";
		txt += "|" + (((addr > bas) && (addr < mid) )? RED_BGD:RESET) + "              " + RESET + "| \n";
		txt += "|" + (((addr > bas) && (addr < mid) )? RED_BGD:RESET) + "  [LOW RAM]   " + RESET + "| \n";
		txt += "|" + (((addr > bas) && (addr < mid) )? RED_BGD:RESET) + "              " + RESET + "| \n";
		txt += "================ " + b + "\n";
		txt += "|              | \n";
		txt += "|  [ROM AREA]  |\n";
		txt += "================ " + String.format("%05Xh%n",0);
				
		return txt;
	}
	
	public void showFaultyICs(int address, int BASE, int MIDDLE, int TOP , int[] binary) {
		String txt = BOLD + UNDERLINE + "\nRESULTS\n\n" + RESET;
		//TODO add to print the address comparator with the top of Inner Ram.
		txt += showAddressGraph(address,BASE,MIDDLE,TOP);
		txt += BOLD + "\nBinary result: " + YELLOW + binaryToStr(binary) + RESET + "\n";

		//
		if(address > TOP) txt += GREEN + BOLD + "Faulty RAM is in expansion memory\n" + RESET;
		else {
			String ic = "\nIC";
			// 8 ICs in high part of RAM or 8 ICs low part of RAM.
			int n = (address >= MIDDLE)? 16:8;
			//1 => BAD, 0 => GOOD
			for(int i = 0; i < 8; i++) {
				if(binary[i] == 1) txt += BOLD + ic + (n-i) + RED + " BAD" + RESET;
				else txt += ic + (n-i) + GREEN + " GOOD" + RESET;
			}
		}
		
		System.out.println(txt);
	}
}
