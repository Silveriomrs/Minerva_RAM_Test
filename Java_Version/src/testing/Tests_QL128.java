package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import controller.Tester;

class Tests_QL128 {

	@Test
	void test() {
		Tester tester = new Tester();
		// Test1 Binary == 00000001  ADDR > 30000h 
		tester.setWrite("70014F7B");
		tester.setRead("70014E7B");
		tester.setAddress("0000200C");
		tester.calc();
		assertAll(
				() -> assertArrayEquals(new int[]{0,0,0,0,0,0,0,1},tester.getBinArray())
				//() -> assertEquals()	
		);
		
		// Test2 Binary == 00000001  ADDR > 30000h
		Tester tester2 = new Tester("2001E548", "2001E549", "00020000", "QL128");
		tester2.calc();
		assertAll(
				() -> assertArrayEquals(new int[]{0,0,0,0,0,0,0,1},tester2.getBinArray())
		);
		
	}

}
