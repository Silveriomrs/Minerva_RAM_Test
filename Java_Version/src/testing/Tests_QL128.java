package testing;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import controller.Decoder;

class Tests_QL128 {

	@Test
	void test() {
		Decoder tester = new Decoder();
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
		Decoder tester2 = new Decoder("2001E548", "2001E549", "00020000", "QL128");
		tester2.calc();
		assertAll(
				() -> assertArrayEquals(new int[]{0,0,0,0,0,0,0,1},tester2.getBinArray())
		);
		
	}

}
