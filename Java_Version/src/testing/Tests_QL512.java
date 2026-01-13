package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import controller.Decoder;

class Tests_QL512 {
	//private int[] bin;
	@Test
	void test() {

		// Test3 For 512K Fault IC: IC6 Binary result: 00100000  ADDR > 60000
		Decoder tester = new Decoder("FFFFFFDF", "FFFFFFFF", "00030060", "QL512");
		//bin = new int[]{0,0,1,0,0,0,0,0};
		tester.calc();
		assertAll(
				() -> assertArrayEquals(new int[]{0,0,1,0,0,0,0,0}, tester.getBinArray())
		);
		
		// Test4 for 512K Fault IC: IC8 Binary results: 10000000 - ADDR > 60000
		Decoder tester2 = new Decoder("FFFFFFFF", "FF7FFF7F", "00050808", "QL512");
		//bin = new int[]{1,0,0,0,0,0,0,0};
		tester2.calc();
		assertAll(
				() -> assertArrayEquals(new int[]{1,0,0,0,0,0,0,0}, tester2.getBinArray())
		);
	}

}
