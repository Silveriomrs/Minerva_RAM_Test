/**
 * 
 */
package view;

import model.Value;

/**
 * 
 */
public class ShellMSG {
	
	public static final String RESET  = "\u001B[0m";
    public static final String RED    = "\u001B[31m";
    public static final String GREEN  = "\u001B[32m";
    public static final String BOLD = "\033[1m";
    public static final String UNDERLINE = "\033[4m";
	
	public ShellMSG() {
		
	}
	
	
	public void showInputs(Value write, Value read, String typeramexp, String bin) {
		System.out.println(BOLD + UNDERLINE + "INPUTS\n" + RESET);
		System.out.println("Write " + write.toString());
		System.out.println("Read " + read.toString());
		System.out.println("System: " + typeramexp);
		System.out.println("Binary result: " + bin);
	}
	
	public void showHelp() {
		String txt = "";
		
	}
	
	public void showCredits() {
		String txt = BOLD + UNDERLINE + "About\n" + RESET;
		txt += "The Original Program (ram3_ramfail.bas) was written for the SINCLAIR QL in SUPER BASIC language by"
				+ " Dominic Brown (1990). Converted in PHP language by Xad/Nightfall (8/5/2015). Update by Popopo to include more"
				+ " internal upgrading models (09/04/2025)";
		txt += "\n Now it is ported to Java TM for multiplatform purpose by Popopo (a.k.a Lindyhop) (21/04/2025)";
	}
	
	public void showExamples() {
		String txt = "";

	}
	
	public void showFaultyICs(int address, int MIDDLE, int[] binary, boolean isInnerRam) {
		System.out.println(BOLD + UNDERLINE + "\nRESULTS\n" + RESET);
		if(isInnerRam) System.out.println(GREEN + BOLD + "Faulty RAM is in expansion memory\n" + RESET);
		else {
			String ic = "IC";
			// 8 ICs in high part of RAM or 8 ICs low part of RAM.
			int n = (address >= MIDDLE)? 16:8;
			//1 => BAD, 0 => GOOD
			for(int i = 0; i < 8; i++) {
				if(binary[i] == 1) System.out.println(BOLD + ic + (n-i) + RED + " BAD" + RESET);
				else System.out.println(ic + (n-i) + GREEN + " GOOD" + RESET);
			}
		}
	}
}
