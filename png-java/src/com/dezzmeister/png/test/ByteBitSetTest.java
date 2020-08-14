package com.dezzmeister.png.test;

import com.dezzmeister.png.data.ByteBitSet;

public class ByteBitSetTest {

	public static void main(String[] args) {
		final ByteBitSet set = new ByteBitSet(40);
		
		set.put(5, 0b1011011001010010111, 19);
		arrayDump(set);
		set.clear();
		
		set.put(6, 0b1011011001010010111, 19);
		arrayDump(set);
		set.clear();
		
		set.put(7, 0b1011011001010010111, 19);
		arrayDump(set);
		set.clear();
		
		set.put(8, 0b1011011001010010111, 19);
		arrayDump(set);
		set.clear();
		
		set.put(9, 0b1011011001010010111, 19);
		arrayDump(set);
		set.clear();
	}
	
	private static void arrayDump(final ByteBitSet set) {
		final byte[] array = set.getArray();
		
		for (final byte b : array) {
			System.out.print(fullBinaryString(Byte.toUnsignedInt(b)) + " ");
		}
		System.out.println();
	}
	
	private static String fullBinaryString(final int num) {
		final String digits = Integer.toBinaryString(num);
		
		if (digits.length() >= 8) {
			return digits;
		}
		
		String zeroes = "";
		
		for (int i = 0; i < (8 - digits.length()); i++) {
			zeroes += "0";
		}
		
		return zeroes + digits;
	}

}
