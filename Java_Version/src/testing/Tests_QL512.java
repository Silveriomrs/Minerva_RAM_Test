package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import controller.Tester;

class Tests_QL512 {

	@Test
	void test() {

		// Test3 For 512K Fault IC: IC6 Binary result: 00100000  ADDR > 60000
		Tester tester3 = new Tester("FFFFFFDF", "FFFFFFFF", "00030060", "QL512");
		tester3.calc();
		assertAll(
				() -> assertEquals("00100000",tester3.binaryToStr())
		);
		
		// Test4 for 512K Fault IC: IC8 Binary results: 10000000 - ADDR > 60000
		Tester tester4 = new Tester("FFFFFFFF", "FF7FFF7F", "00050808", "QL512");
		tester4.calc();
		assertAll(
				() -> assertEquals("10000000",tester4.binaryToStr())
		);
	}

}
