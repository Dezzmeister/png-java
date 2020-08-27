package com.dezzmeister.png.data;

/**
 * Contains functions to convert between various types, such as an int to byte[] or vice-versa.
 * 
 * @author Joe Desmond
 */
public class Conversions {
	
	/**
	 * Converts an int into its equivalent 4-byte array. This is akin to a C union
	 * of an int and a byte[]: the most significant byte in the int is indexed first.
	 * 
	 * @param value integer
	 * @return 4 bytes of integer
	 */
	public static final byte[] fromInt(final int value) {
		final byte[] out = new byte[4];
		
		out[0] = (byte) ((value >>> 24) & 0xFF);
		out[1] = (byte) ((value >>> 16) & 0xFF);
		out[2] = (byte) ((value >>> 8) & 0xFF);
		out[3] = (byte) (value & 0xFF);
		
		return out;
	}
}
