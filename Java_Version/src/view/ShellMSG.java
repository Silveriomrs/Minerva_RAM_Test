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
	
    private static final String VERSION = "0.97c";

	public static String RESET  = "\u001B[0m";
    public static String RED    = "\u001B[31m";
    public static String GREEN  = "\u001B[32m";
    public static String YELLOW = "\u001B[33m";
    public static String BOLD = "\033[1m";
    public static String UNDERLINE = "\033[4m";
    public static String RED_BGD = "\u001B[41m"; 							// Red background
    //No color activated or non activated.
    private boolean NC = false;
	
	public ShellMSG() {
		
	}
	
	private String showValue(String v, boolean isValid) {
		String c = (isValid)? (GREEN + BOLD) : (RED + BOLD); 
		String txt = c + v + RESET;
		return txt;
	}
	
	public void showErr(ErrCodes e, String value) {
		String txt = "";
		txt += showValue(value,false);
		
		switch(e) {
		case NOT_ENOUGH_PARAMS: txt = RED + BOLD + "Not enough parameters"; break;
		case UNKNOWN_PARAM: txt += " : Unknown paramater"; break;
		case WRONG_WRITE_VALUE: txt += " : Wrong WRITE value"; break;
		case WRONG_READ_VALUE: txt += " : Wrong READ value"; break;
		case WRONG_ADDRESS_VALUE: txt += " : Wrong ADDRESS value"; break;
		case WRONG_QLRAM_VALUE: txt += " : Wrong QLRAM value"; break;
		default:
			txt = RED + BOLD + "\nToo many parameters\n" + RESET + "use" + GREEN + 
			BOLD + " -e " + RESET + "argument to get some valid examples.";
		}
		
		System.out.println(txt);
	}
	
	
	public void showInputs(Value write, Value read, Value address, String typeramexp) {
		String txt = BOLD + UNDERLINE + "\nINPUTS\n" + RESET;
		if(NC) txt += "------\n";
		
		txt += "\nSystem:  " + YELLOW + typeramexp + RESET;
		txt += "\nWrite:   " + showValue(write.toString(), write.isValid());
		txt += "Read:    " + showValue(read.toString(), read.isValid());
		txt += "Address: " + showValue(address.toString(), address.isValid());	
		System.out.println(txt);
	}
	
	
	private void showOptions() {
		String txt = YELLOW + BOLD + UNDERLINE + "\nOptions\n" + RESET;
		if(NC) txt += "-------\n";
		
		txt += "java -jar ramtestdec.jar [OPTION] \n";
		txt += BOLD + "\n-a    : " + RESET + " About this application.";
		txt += BOLD + "\n-c    : " + RESET + " Shows the credits.";
		txt += BOLD + "\n-e    : " + RESET + " Shows some examples.";
		txt += BOLD + "\n-h    : " + RESET + " Shows full help.";
		txt += BOLD + "\n-nc   : " + RESET + " Disables Ansi colors for non supported consoles. Must be the last argument.";
		txt += BOLD + "\n-o    : " + RESET + " Shows the options.";
		txt += BOLD + "\n-re   : " + RESET + " Run an example case.";
		txt += BOLD + "\n-s    : " + RESET + " Shows the syntax.";
		txt += BOLD + "\n-u    : " + RESET + " Shows the user's guide.";
		txt += BOLD + "\n-v    : " + RESET + " Shows the version.";
		System.out.println(txt);
	}
	
	private void showFullHelp() {
		showAbout();
		showDisclamer();
		showVersion();
		showCredits();
		showOptions();
		showUserGuide();
		showSyntax();
		showExamples();	
	}
	
	private void showMinHelp() {
		showAbout();
		showSyntax();
		showDisclamer();
	}
	
	public void setSwitch(String v) {
		switch(v.toLowerCase()){
		case "-a" : showAbout(); break;
		case "-c" : showCredits(); break;										//Credits
		case "-e" : showExamples() ; break;										//Examples
		case "-h" : showFullHelp() ; break;										//Full  help
		case "-m" : showMinHelp(); break;
		case "-nc" : disableColors(); break;									//Disable ANSI Colors.
		case "-o" : showOptions() ; break;			
		case "-re" : break;													    //Run the default example. No need nothing here.
		case "-s" : showSyntax() ; break;										//Syntax
		case "-u" : showUserGuide() ; break;										//Syntax
		case "-v" : showVersion(); break;										//Version
		default:
			showErr(ErrCodes.UNKNOWN_PARAM,v);
		}
		
		drawLine(2);
	}
	
	private void disableColors() {
		NC = true;
		RESET  = "";
	    RED    = "";
	    GREEN  = "";
	    YELLOW = "";
	    BOLD = "";
	    UNDERLINE = "";
	    RED_BGD = "";
	}
	
	private void showSyntax() {
		String txt = YELLOW + BOLD + UNDERLINE + "\nSyntax\n" + RESET;
		if(NC) txt += "------\n";

		
		txt += "java -jar ramtestdec.jar [OPTION] [WRITE READ ADDRESS] [QLRAM] [-NC]";
		txt += "\nFor help type: " + BOLD + GREEN + "java -jar ramtestdec.jar -h\n" + RESET;
		System.out.println(txt);
	}
	
	private void showVersion() {
		String txt = BOLD + YELLOW + "\nVersion: " + GREEN + VERSION + RESET;		
		System.out.println(txt);
	}
	
	private void showAbout() {
		String txt = YELLOW + BOLD + UNDERLINE + "\nAbout\n" + RESET;
		if(NC) txt += "-----\n";
		
		txt += "This aplication has the purpose of decoding the 3 hexadecimal codes returned by the Minerva ROM,"+
		"\nalso by the basic version of the Ram Tester Software programed by " + BOLD +"Dominic Brown." + RESET;
		txt += "\n\nTo report a bug, suggestion, new versions or more information:"
				+ GREEN + "\nhttps://github.com/Silveriomrs/Minerva_RAM_Test";
		System.out.println(txt);
	}
	
	private void showCredits() {
		String txt = BOLD + YELLOW + UNDERLINE + "\nCredits\n" + RESET;
		if(NC) txt += "-------\n";
		
		txt += "The Original Program (ram3_ramfail.bas) was written for the SINCLAIR QL in SUPER BASIC language by"
				+ BOLD + " Dominic Brown (1990)." + RESET + "\nConverted in PHP language by " + BOLD + "Xad/Nightfall (8/5/2015)."
				+ RESET + " Update by Popopo to include the upgraded models (09/04/2025).";
		txt += "\nNow it's ported to Java TM for multiplatform purpose by" + BOLD + " Popopo (a.k.a Lindyhop) (21/04/2025)";
		System.out.println(txt);
	}
	
	private void showWarningQL640() {
		//Special Warning about QL640 option-
		String txt = "";
		txt += "\n" + RED_BGD + BOLD + UNDERLINE + "Warning" + RESET + BOLD + ": The internal mod 640K has not been tested yet." + RESET 
				+ " Thus QL640 option is still experimental."
				+ "\nIn case you have one of those machines, please to contact to me and bring information"
				+ "\nand adjust the algorithm (if it needs to).";
		System.out.println(txt);
	}
	
	private void showDisclamer() {
		String txt = BOLD + YELLOW + UNDERLINE + "\nDisclamer\n" + RESET;
		if(NC) txt += "---------\n"; //
		
		txt += "This software aims to be an extra support and help to filter suspected faulty ICs."
				+ BOLD + " Use it under your own risk." + RESET + UNDERLINE + BOLD +"\nThere is not any guarantee for its use."
				+ RESET + " Be aware that some expansions may not fit the algorithm. "
						+ "\nThereby the responsability for the results relies on the user. Please to get informed ";
		System.out.println(txt);
	}
	
	private void showUserGuide() {
		String txt = BOLD + YELLOW + UNDERLINE + "\nUser Guide\n" + RESET;
		if(NC) txt += "----------\n";

		txt += "This software requires 3 hexadecimal codes that you may obtain mainly from the use of"
				+ UNDERLINE +"\nMinerva ROM" + RESET + " or another software that implement the algorithm. "
				+ "\nThe three codes represent a " + BOLD + "writen" + RESET + " value at the " + BOLD + "address" + RESET + " indicated by"
				+ "the third one, \nand the value " + BOLD + "read " + RESET +"(second value) from this address after writing operation.\n";
		
		txt += "\nThose values must be typed in the following order:" + BOLD + " {WRITE,READ,ADDRESS}" + RESET + " which is exactly"
				+ "\nthe order that Minerva ROM gives them. After decoding those values, the software will"
				+ "\nshow the results, indicanting which faulty RAM ICs " + BOLD + UNDERLINE + "are suspects" 
				+ RESET + ".";
		
		txt += "\n\nThe default system is a stock QL with 128K of RAM, but you may need to select other"
				+ "\nconfiguration depending of the memory installed in your main board. Thus the results"
				+ "\nmay vary significally. Set your option accordingly.";
		
		txt += "\n\nThe decoder works properly in QL systems whose inner memory are placed on the original"
				+ "\nRAM ICs places, the quantity of memory in each place keep the same and balanced." + RESET;
		txt += "\n\n" + RED_BGD + "It's highly recommended to check ICs before any desoldering or manipulation" + RESET;
		
		System.out.println(txt);
	}
	
	private void showExamples() {
		String txt = BOLD + YELLOW + UNDERLINE + "\nExamples\n" + RESET;
		if(NC) txt += "--------\n";

		txt += "The Ram Test Decoder has" + BOLD + " 4 workings modes, " + RESET + " let's see them with some examples:";
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
		//
		txt += GREEN + BOLD + "\n\nExample 4: No colors, same before adding the switch -nc at the end" + RESET;
		txt += "\nThe '-nc' argument is optional and deactivate ANSI console codes for systems that are not able to show them properly";
		txt += YELLOW + "\njava -jar ramtestdec.jar 70014E7B 70014F7B 000200C" + GREEN + " -nc" + RESET;
		txt += YELLOW + "\njava -jar ramtestdec.jar -h" + GREEN + " -nc" + RESET;
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
		
		txt += "Address   threshold\n";
		txt += GREEN + a + RESET;
		
		if(addr >= mid && addr <= top) {
			txt += "  > " + YELLOW + m + "\n" + RESET;
		}else if (addr > top) {
			txt += "  > " + YELLOW + t + "\n" + RESET;
		}else if (addr == mid) {
			txt += "  = " + YELLOW + m + "\n" + RESET;
		}else {
			txt += "  < " + YELLOW + m + "\n" + RESET;
		}
		
		//TODO: Determinate if the limit (top, middle, whatever) is included when addressing. Can write at top?
		
		txt += "\n";
		txt +=  "--|--|--|--|--|- \n";
		txt += "|" + ((addr > top)? RED_BGD:RESET) + "              " + RESET + "| \n";

		txt += "|" + ((addr > top)? RED_BGD:RESET) + "   EXTERNAL   " + RESET + "| \n";
		txt += "|" + ((addr > top)? RED_BGD:RESET) + "     RAM      " + RESET + "| \n";
		txt += "================ " + t  + " => Total RAM: " + getMaxRAM(top) + "\n";	
		txt += "|" + (((addr > mid) && (addr <= top) )? RED_BGD:RESET) + "              " + RESET + "| \n";
		txt += "|" + (((addr > mid) && (addr <= top) )? RED_BGD:RESET) + "  [HIGH RAM]  " + RESET + "| \n";
		txt += "|" + (((addr > mid) && (addr <= top) )? RED_BGD:RESET) + "              " + RESET + "| \n";
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
	
	private String drawLayout(int[] binary) {
		String txt = "";
		String row1, row2;
		row1 = row2 = "";
		
		String gap = "  ";
		String bar = RESET + "|";

		int n = 1;
		
		for(int i=1; i<=2; i++) {
			//Initialize the parts each round.
			String top, head, name, id, end;
			top = head = name = id = end = "";
			String color8 = "";
			
			//Creation of the row.
			for (int j=1 ; j<=8 ; j++) {
				String color = gap + bar + ((binary[n-1] == 1)? RED_BGD:RESET);
				//Ignore the 8th position. Draw others.
				if(n!=8) {
					top += gap + " __ ";
					head += color + ((NC && (binary[n-1] == 1))? " X":"  ") + bar;
					name += color + "IC" + bar;
					id += color;
				}			
				
				//For ID number and 8th IC special treatment.
				if(n == 8) {
					//Skip it	
				}else if(n==16) {
					//For IC16
					id += n + bar;
					//Let's Stick the IC8 to the IC16.
					color8 = gap + bar + ((binary[7] == 1)? RED_BGD:RESET);		//Pick the color up for IC8
					top += gap + " __ ";
					head += color8 + ((NC && (binary[7] == 1))? " X":"  ") + bar;
					name += color8 + "IC" + bar;					
					id += color8 + " " + 8  + bar;
				}else if(n<10) {
					//Special space
					id += " " + n + bar;
				}else {
					//No space in the ID
					id += n + bar;
				}
				
				if(n !=8) end += color + "__" + bar;							//ignore the 8th position, draw others.
				
				if(n == 16) end += color8 + "__" + bar;							//Add next to IC16 the end of IC8
				
				n++;
			}
			
			//Here we composite all the lines that compounds the ICs representation (by rows & lines)
			if(i==1) row1 = top + "\n" + head + "\n" + name + "\n" + id + "\n" + end + "\n";
			else row2 = top + "\n" + head + "\n" + name + "\n" + id + "\n" + end + "\n";
		}
		
		txt = row1 + row2;
		return txt;
	}
	
	
	private int[] composeBin(int[] binary, int row) {
		int[] bin = new int[16];
		for (int i=0; i<16; i++) {
			bin[i] = 0;
			
			//Binary array is structured in reverser order ( bit for IC1 is the last bit of the array bit#8) 
			if(row == 1 && i<8) {   			//From 0 to 7 are first 8.
				bin[i] = binary[7 - i];
			}else if(row == 2 && i>=8){			//From 8 to 15 are second row.
				bin[i] = binary[7 - (i - 8)];
			}
		}
		
		return bin;
	}
	
	
	private String showLayout(int[] binary, int row) {
		String txt = "\n";
		int[] bin = composeBin(binary,row);
		
		txt += drawLayout(bin);
		
		return txt;
	}
	
	private void drawLine(int a) {
		//Later it may adjust itself to console wide dimension.
		String txt = "";
		String line = (a == 1)? "----------":"___________"; 					//reducing the loop by composing the core to 10 units.
		
		for(int i = 0; i < 8 ; i++) {											// each time x 10 symbols.
			txt += line;
		}
		
		System.out.println(txt + "\n");
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
			//Shows Layout formated. To get the row we divide number of ICs by 8
			txt += showLayout(binary,n/8);
		}
		
		System.out.println(txt);
		
		//Special warning if the test is for a modded QL with 640KB of internal RAM.
		if(TOP == 0xC0000) showWarningQL640(); 
		
		drawLine(2);
		
	}
}
