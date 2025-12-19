package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import controller.Tester;
import model.TypeRamExpansion;

class Tests_QL128 {

	@Test
	void test() {
		Tester tester = new Tester();
		// Test1 Binary == 00000001  ADDR > 30000h 
		tester.setWrite("70014F7B");
		tester.setRead("70014E7B");
		tester.setAddress(0x0000200C);
		tester.calc();
		assertAll(
				() -> assertEquals("00000001",tester.getBinaryStr())
				//() -> assertEquals()	
		);
		
		// Test2 Binary == 00000001  ADDR > 30000h
		Tester tester2 = new Tester("2001E548", "2001E549", 0x00020000, TypeRamExpansion.QL128);
		tester2.calc();
		assertAll(
				() -> assertEquals("00000001",tester2.getBinaryStr())
		);
		
		// Test3 For 512K Fault IC: IC6 Binary result: 00100000  ADDR > 60000
		Tester tester3 = new Tester("FFFFFFDF", "FFFFFFFF", 0x00030060, TypeRamExpansion.QL512);
		tester3.calc();
		assertAll(
				() -> assertEquals("00100000",tester3.getBinaryStr())
		);
		
		// Test4 for 512K Fault IC: IC8 Binary results: 10000000 - ADDR > 60000
		Tester tester4 = new Tester("FFFFFFFF", "FF7FFF7F", 0x00050808, TypeRamExpansion.QL512);
		tester4.calc();
		assertAll(
				() -> assertEquals("10000000",tester4.getBinaryStr())
		);
	}

}
