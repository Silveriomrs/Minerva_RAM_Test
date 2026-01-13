/**
 * View part of the application.
 * @author Silverio MRS. (aka Popopo,Lindyhop)
 * @version 1.0
 */
package view;

import model.ErrCodes;
import model.Value;

/**
 * The class is in charge of the view component of the tool.
 *  Mainly hosts the messages and functions to represent the texts properly by the console.
 * @author Silverio MRS. (aka Popopo,Lindyhop)
 * @version 1.0
 */
public class ShellMSG {
	
    private static final String VERSION = "0.98b";

	public static String RESET  = "\u001B[0m";
    public static String RED    = "\u001B[31m";
    public static String GREEN  = "\u001B[32m";
    public static String YELLOW = "\u001B[33m";
    public static String BOLD = "\033[1m";
    public static String UNDERLINE = "\033[4m";
    public static String RED_BGD = "\u001B[41m"; 						// Red background
    public static String CYAN_BGD = "\u001B[46m"; 						// Yellow background
   
    private boolean NC = false;											//Flag No color activated.
	
    /**
     * Constructor of the class.
     */
	public ShellMSG() {
		
	}
	
	/**
	 * The function format a received value (usually hexadecimal), with the ANSI colors
	 *  depending is valid or not. Returning the result.  
	 * @param v Value to print, usually Hexadecimal number in String format.
	 * @param isValid when the value must be printed in Green color for a valid one, otherwise Red color.
	 * @return The formatted text of the value with Green or Red color and bold chars.
	 */
	private String showValue(String v, boolean isValid) {
		String c = (isValid)? (GREEN + BOLD) : (RED + BOLD); 
		String txt = c + v + RESET;
		return txt;
	}
	
	
	/**
	 * It conforms the established help information calling to the
	 * individual procedures.
	 */
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
	
	/**
	 * It shows the  minimum information about the APP to the user.
	 * Usually when the users doesn't introduce an option as argument.
	 */
	private void showMinHelp() {
		showAbout();
		showSyntax();
		showDisclamer();
	}
	
	
	/**
	 * The procedure disable all ANSI codes used by the messages to be printed by the console.
	 *  It replaces those codes by an empty String "".
	 */
	private void disableColors() {
		NC = true;
		RESET  = "";
	    RED    = "";
	    GREEN  = "";
	    YELLOW = "";
	    BOLD = "";
	    UNDERLINE = "";
	    RED_BGD = "";
	    CYAN_BGD = "";
	}
	
	/**
	 * It shows the basic valid syntax for the APP with optional parameters.
	 */
	private void showSyntax() {
		String txt = YELLOW + BOLD + UNDERLINE + "\nSyntax\n" + RESET;
		if(NC) txt += "------\n";

		
		txt += "java -jar ramtestdec.jar [OPTION] [WRITE READ ADDRESS] [QLRAM] [-NC]";
		txt += "\nFor help type: " + BOLD + GREEN + "java -jar ramtestdec.jar -h\n" + RESET;
		System.out.println(txt);
	}
	
	/**
	 * It shows the APP version formated by ANSI codes.
	 */
	private void showVersion() {
		String txt = BOLD + YELLOW + "\nVersion: " + GREEN + VERSION + RESET;		
		System.out.println(txt);
	}
	
	/**
	 * The procedure shows the information about what this APP is for, also contact information
	 *  to report bugs or suggestions. The message is ANSI formated.
	 */
	private void showAbout() {
		String txt = YELLOW + BOLD + UNDERLINE + "\nAbout\n" + RESET;
		if(NC) txt += "-----\n";
		
		txt += "This aplication has the purpose of decoding the 3 hexadecimal codes returned by the Minerva ROM,"+
		"\nalso by the basic version of the Ram Tester Software programed by " + BOLD +"Dominic Brown." + RESET;
		txt += "\n\nTo report a bug, suggestion, new versions or more information:"
				+ GREEN + "\nhttps://github.com/Silveriomrs/Minerva_RAM_Test";
		System.out.println(txt);
	}
	
	/**
	 * This shows the credits of the original program to the user with formatting ANSI Codes.
	 */
	private void showCredits() {
		String txt = BOLD + YELLOW + UNDERLINE + "\nCredits\n" + RESET;
		if(NC) txt += "-------\n";
		
		txt += "The Original Program (ram3_ramfail.bas) was written for the SINCLAIR QL in SUPER BASIC language by"
				+ BOLD + " Dominic Brown (1990)." + RESET + "\nConverted in PHP language by " + BOLD + "Xad/Nightfall (8/5/2015)."
				+ RESET + " Update by Popopo to include the upgraded models (09/04/2025).";
		txt += "\nNow it's ported to Java TM for multiplatform purpose by" + BOLD + " Popopo (a.k.a Lindyhop) (21/04/2025)";
		System.out.println(txt);
	}
	
	/**
	 * It gives a warning about the QL640 option. It should be shown every time this option
	 *  is used.
	 */
	private void showWarningQL640() {
		//Special Warning about QL640 option-
		String txt = "";
		txt += "\n" + RED_BGD + BOLD + UNDERLINE + "Warning" + RESET + BOLD + ": The internal mod 640K has not been tested yet." + RESET 
				+ " Thus QL640 option is still experimental."
				+ "\nIn case you have one of those machines, please to contact to me and bring information"
				+ "\nand adjust the algorithm (if it needs to).";
		System.out.println(txt);
	}
	
	/**
	 * It shows a colored by ANSI codes disclaimer message for the user.
	 */
	private void showDisclamer() {
		String txt = BOLD + YELLOW + UNDERLINE + "\nDisclamer\n" + RESET;
		if(NC) txt += "---------\n"; //
		
		txt += "This software aims to be an extra support and help to filter suspected faulty ICs."
				+ BOLD + " Use it under your own risk." + RESET + UNDERLINE + BOLD +"\nThere is not any guarantee for its use."
				+ RESET + " Be aware that some expansions may not fit the algorithm. "
						+ "\nThereby the responsability for the results relies on the user. Please to get informed ";
		System.out.println(txt);
	}
	
	/**
	 * It shows the user guide of the APP for the user.
	 *  The procedure support ANSI codes.
	 */
	private void showUserGuide() {
		String txt = BOLD + YELLOW + UNDERLINE + "\nUser Guide\n" + RESET;
		if(NC) txt += "----------\n";

		txt += "This software requires 3 hexadecimal codes that you may obtain mainly from the use of"
				+ UNDERLINE +"\nMinerva ROM" + RESET + " or another software that implement the algorithm. "
				+ "\nThe three codes represent a " + BOLD + "writen" + RESET + " value at the " + BOLD + "address" + RESET + " indicated by"
				+ " the third one, \nand the " + BOLD + "read " + RESET +"value (second one) from this address after writing operation.\n";
		
		txt += "\nThose values must be typed in the following order:" + BOLD + " {WRITE,READ,ADDRESS}" + RESET + " which is exactly"
				+ "\nthe order that Minerva ROM gives them. After decoding those values, the software will"
				+ "\nshow the results, pointing to the faulty RAM ICs " + BOLD + UNDERLINE + "are suspects" 
				+ RESET + ".";
		
		txt += "\n\nThe default system is a stock QL with 128K of RAM, but you may need to select other"
				+ "\nconfiguration depending of the memory installed in your main board. Thus the results"
				+ "\nmay vary significally. Set your option accordingly.";
		
		txt += "\n\nThe decoder works properly in QL systems whose inner memory are placed on the original"
				+ "\nRAM ICs places, the quantity of memory in each place keep the same and balanced." + RESET;
		txt += "\n\n" + RED_BGD + "It's highly recommended to check ICs before any desoldering or manipulation" + RESET;
		
		System.out.println(txt);
	}
	
	/**
	 * It shows some examples to use the APP.
	 *  The text is formatted by ANSI codes.
	 */
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
	
	
	private String binaryToStr(int[] binary) {
		String b = "";
		for(int a : binary) { b += a; }
		return b;
	}
	

	
	/**
	 * Gives the present amount of RAM in the PCB based on the TOP address given.
	 * <P>It may vary on 3 main values: 128K, 512K and 640K. Depending on the address given in hexadecimal (unsigned).
	 * @param top of RAM that may be: 40000H for 128K, A0000h for 612K and C0000h for 640K.
	 * @return The String with the amount of RAM present on the main PCB.
	 */
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
			txt += "UNKNOWN";
		}
		
		return txt;
	}
	
	/**
	 * Prepares the graphical representation of the faulty ICs, the area affected and listed binary result.
	 *  The function returns a String with the ASCII graphical information colored or signaled by symbols.
	 *   the parameters are in Hexadecimal unsigned representation.
	 * @param addr Address of the issue given by Minerva.
	 * @param bas Address of the RAM base (after ROM).
	 * @param mid Address of the middle of RAM.
	 * @param top Last address of the RAM.
	 * @return Graphical representation in ASCII of the results colored or with symbols.
	 */
	private String showAddressGraph(int addr, int bas, int mid, int top) {
		String txt = "";
		String a = String.format("%05Xh",addr);
		String b = String.format("%05Xh",bas);
		String m = String.format("%05Xh",mid);
		String t = String.format("%05Xh",top);
		//Precalc the affected zone.
		boolean isExternal,isHighRam,isLowRam,isMiddle;
		isExternal = (addr > top);
		isHighRam = (addr > mid) && (addr <= top);
		isLowRam = (addr > bas) && (addr < mid);
		isMiddle = addr == mid;
		//
		txt += "Address   threshold\n";
		txt += GREEN + a + RESET;
		
		if(addr >= mid && addr <= top) {
			txt += "  > " + YELLOW + m + "\n" + RESET;
		}else if (isExternal) {
			txt += "  > " + YELLOW + t + "\n" + RESET;
		}else if (isMiddle) {
			txt += "  = " + YELLOW + m + "\n" + RESET;
		}else {
			txt += "  < " + YELLOW + m + "\n" + RESET;
		}
		
		//TODO: Determinate if the limit (top, middle, whatever) is included when addressing. Can write at top?
		txt += "\n";
		txt +=  "--|--|--|--|--|- \n";
		txt += "|" + ((isExternal)? RED_BGD:RESET) + "              " + RESET + "|\n";
		txt += "|" + ((isExternal)? RED_BGD:RESET) + "   EXTERNAL   " + RESET + "|" + ((isExternal && NC)? " <= Error zone":"") + "\n";
		txt += "|" + ((isExternal)? RED_BGD:RESET) + "     RAM      " + RESET + "|\n";
		txt += "================ " + t  + " => Total RAM: " + getMaxRAM(top) + "\n";	
		txt += "|" + ((isHighRam)? RED_BGD:RESET) + "              " + RESET + "|\n";
		txt += "|" + ((isHighRam)? RED_BGD:RESET) + "  [HIGH RAM]  " + RESET + "|" + ((isHighRam && NC)? " <= Error zone":"") + "\n";
		txt += "|" + ((isHighRam)? RED_BGD:RESET) + "              " + RESET + "|\n";
		txt += "-" + ((isMiddle)? RED_BGD:RESET) + "--------------- " + m + RESET + ((isMiddle && NC)? " <= Error zone":"") + "\n";
		txt += "|" + ((isLowRam)? RED_BGD:RESET) + "              " + RESET + "|\n";
		txt += "|" + ((isLowRam)? RED_BGD:RESET) + "  [LOW RAM]   " + RESET + "|" + ((isLowRam && NC)? " <= Error zone":"") + "\n";
		txt += "|" + ((isLowRam)? RED_BGD:RESET) + "              " + RESET + "|\n";
		txt += "================ " + b + "\n";
		txt += "|              |\n";
		txt += "|  [ROM AREA]  |\n";
		txt += "================ " + String.format("%05Xh%n",0);
				
		return txt;
	}
	
	/**
	 * This function draws the layout of the PCB centered in the RAM area.
	 * Particularly the RAM ICs.
	 * <P>It needs the array set of bits for each IC (16 bits) in increasing order that match the numbered ICs (from IC1 to IC16).
	 * @param binary array with the representation of each faulty IC (bit = 1) or not (bit = 0) in increasing order.
	 * @return The String with the layout and market faulty ICs properly (colored or symbolic)
	 */
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
	
	/**
	 * Compose the full array for each IC on the QL (16). Also the new array is
	 *  written in the reverse order to match the right count from IC1 to IC16.
	 * @param binary Result binary that is written with the higher IC representation in the lowest position.
	 * @param row it indicate if the array belongs to the first row or the second one. So for row1 (IC1 to IC8) set it to 1. For 
	 * Set 2 for row 2 (IC9 to IC16).
	 * @return The full binary results for all the ICs (IC1 till IC16) with matching order.
	 */
	private int[] composeFullBin(int[] binary, int row) {
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
	
	/**
	 * The function shows a representation of the PCB Layout focused on the RAM area,
	 *  showing from the IC1 to IC16. The ICs are tainted with colors depending on if they are 
	 *   considered faulty or not.
	 * @param binary Binary array with the results from the calc of decoding the introduced values as arguments.
	 * @param row Row of the RAM that the results are referred. It may be Row 1 for ICs1-8 or Row 2 for ICs9-16.
	 * @return The drawn layout with the colored and formatted ICs marking the wrong ICs.
	 */
	private String showLayout(int[] binary, int row) {
		String txt = "\n";
		int[] bin = composeFullBin(binary,row);
		txt += drawLayout(bin);
		
		return txt;
	}
	
	/**
	 * The procedure draws a line of 80 chars (for console width separator).
	 *  There are 2 types of lines, underscore and minus symbol for choice.
	 *  @param 1 for lines composed by minus '-', 2 for underscore component '_'.
	 */
	private void drawLine(int a) {
		//Later it may adjust itself to console wide dimension.
		String txt = "";
		String line = (a == 1)? "----------":"___________"; 					//reducing the loop by composing the core to 10 units.
		
		for(int i = 0; i < 8 ; i++) txt += line;								// each time x 10 symbols.
		
		System.out.println(txt + "\n");
	}
	
	/**
	 * This procedure prints an error code message based on the received parameter.
	 * 	It's conformed to the input values for arguments introduced by the user from the console.
	 * @param e Error code from the class {@link model.ErrCodes}.
	 * @param value the referenced value that produce the error message.
	 */
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
	
	/* PUBLIC FUNTIONS/PROCEDURES */
	
	/**
	 * The procedure conforms a message with the introduced values to decode from the user,
	 *  {WRITE,READ,ADDRESS,TYPERAMEXP}. This colored message is used for the final report after
	 *   decoding the values.
	 *   Those values are introduced by the user with the APP arguments.
	 * @param write written value that the test returns.
	 * @param read read value returned by the test.
	 * @param address address where the discordance was found, returned by the test.
	 * @param typeramexp Type of internal amount of RAM.
	 */
	public void showInputs(Value write, Value read, Value address, String typeramexp) {
		String txt = BOLD + UNDERLINE + "\nINPUTS\n" + RESET;
		if(NC) txt += "------\n";
		
		txt += "\nSystem:  " + YELLOW + typeramexp + RESET;
		txt += "\nWrite:   " + showValue(write.toString(), write.isValid());
		txt += "Read:    " + showValue(read.toString(), read.isValid());
		txt += "Address: " + showValue(address.toString(), address.isValid());	
		System.out.println(txt);
	}
	
	/**
	 * The procedure shows the options that contain the APP to the user.
	 */
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
	
	/**
	 * This function shows the information and/or set some flags determined by the
	 *  received switch 'v'. In case that the value doesn't match a right value, it
	 *   will show a error message.
	 * @param v Value conformed as string '-x' where 'x' is one of the contained values.
	 */
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
	
	/**
	 * It creates a message when there is not a faulty RAM, formatting the introduced values,
	 *  explaining why it may happen and how should use that information.
	 *  The message use ANSI codes to format the message and receives the memory values calculated by the
	 *   APP where supposedly the issue was localized.
	 * @param addr Memory address of the issue (value introduced by the user).
	 * @param bas Memory address where the RAM begins (after ROM zone).
	 * @param mid Memory address in the middle point of the whole inner RAM.
	 * @param top Last memory address of the ram (higher position).
	 */
	public void showNoFaultyRAM(int addr, int bas, int mid, int top) {
		String txt = BOLD + UNDERLINE + "\nRESULTS\n\n" + RESET;
		//Change remark to yellow color to remark related issue but not faulty RAM IC
		RED_BGD = CYAN_BGD;
		txt += showAddressGraph(addr,bas,mid,top);
		//Explanation.
		txt += BOLD + YELLOW + UNDERLINE + "\nRARE CASE\n" + RESET;
		txt += "\nInitially " + UNDERLINE + GREEN + "there is no faulty RAM" + RESET + ". Written data match read, but somehow"
				+ "\nthe routine was fired by a non controlled event with an erratic Address.";
		//
		txt += "\n\nIt doesn't mean that all RAM is 100% right yet, but the test (Minerva, external test,"
				+ "\netc) cannot assert there is a faulty one. Actually, this kind of situation means that a"
				+ "\nline/s controlled by an IC (CPU, ULA, external card, CPLD, etc) is being altered"
				+ "\nfiring the test with a fake RAM error.";
		//
		txt += YELLOW + "\n\nIf the error in your QL (3 codes) appears repetidly, it's recommended to " + UNDERLINE + "remove any"
				+ "\nexternal or expansion card" + RESET + YELLOW + " and observe if it continues happening.";
		
		txt += "\n\n" + BOLD + "Not only" + RESET + YELLOW + " but the suspects in priority order are:"
				+ "\nExternal card, a loose cable, CPU, ZX8301, ZX8302. In Iss6/7 boards also IC38 (HAL)" + RESET;
		System.out.println(txt);
		drawLine(2);

	}
	
	
	/**
	 * This is main procedure. It is in charge of showing the faulty ICs graphically for the console,
	 *  the memory region affected, also the binary code properly.
	 * @param address Address of the issue.
	 * @param BASE Base of the RAM memory in hexadecimal (where the RAM starts)
	 * @param MIDDLE Address of the half of the RAM memory point.
	 * @param TOP Final RAM address.
	 * @param binary Binary results of decoding that identify faulty ICs.
	 */
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
				if(binary[i] == 1) txt += BOLD + ic + (n-i) + RED + (((n-i)<10)? "  BAD":" BAD") + RESET;
				else txt += ic + (n-i) + GREEN + (((n-i)<10)? "  GOOD":" GOOD") + RESET;
			}
			//Shows Layout formated. To get the row we divide number of ICs by 8
			txt += showLayout(binary,n/8);
		}
		
		System.out.println(txt);
		
		//Special warning if the test is for a modified QL with 640KB of internal RAM.
		if(TOP == 0xC0000) showWarningQL640(); 
		
		drawLine(2);
	}
}
