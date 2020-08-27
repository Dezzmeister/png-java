package com.dezzmeister.png.functions;

/**
 * Static class containing 32-bit CRC functions.
 * {@link #crc(byte[])} can be used to compute the CRC of some data.
 * 
 * @author Joe Desmond
 */
public class CRC {
	
	/**
	 * CRC lookup table
	 */
	private static final long[] CRC_TABLE = buildCRCTable();
	
	private static long[] buildCRCTable() {
		final long[] out = new long[256];
		
		long c;
		
		for (int n = 0; n < 256; n++) {
			c = (long) n;
			
			for (int k = 0; k < 8; k++) {
				if ((c & 1) == 1) {
					c = 0xEDB88320L ^ (c >>> 1);
				} else {
					c = c >>> 1;
				}
			}
			out[n] = c;
		}
		
		return out;
	}
	
	/**
	 * Updates an existing CRC with the given data.
	 * 
	 * @param crc existing CRC
	 * @param bytes data
	 * @return new CRC with <code>bytes</code>
	 */
	public static final long updateCRC(final long crc, final byte[] bytes) {
		long c = crc;
		
		for (int n = 0; n < bytes.length; n++) {
			c = CRC_TABLE[(int) ((c ^ bytes[n]) & 0xFF)] ^ (c >>> 8);
		}
		
		return c;
	}
	
	/**
	 * Computes a 32-bit CRC with the given data
	 * 
	 * @param bytes input data
	 * @return 32-bit CRC checksum
	 */
	public static final long crc(final byte[] bytes) {
		return updateCRC(0xFFFFFFFFL, bytes) ^ 0xFFFFFFFFL;
	}
}
