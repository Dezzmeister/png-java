package com.dezzmeister.png.data;

/**
 * A bit set backed by an array of bytes. Like {@link java.util.BitSet BitSet}, with a few differences:
 * <ul>
 * <li>Backed by a <code>byte[]</code> instead of a <code>long[]</code></li>
 * <li>Capacity is set at construction and cannot be changed</li>
 * <li>Bits are inserted differently; bits are not treated as separate booleans</li>
 * <li>There are no methods to retrieve bits, other than {@link #getArray()}</li>
 * </ul>
 * 
 * This class is well suited for PNG encoding; which requires color samples to be packed into bitstreams
 * regardless of byte boundaries.
 * 
 * @author Joe Desmond
 */
public class ByteBitSet {
	
	/**
	 * Bitmask lookup table with 33 bitmasks
	 */
	private static final int[] BITMASKS = generateBitmasks();
	
	/**
	 * Byte array containing all the bits
	 */
	private final byte[] array;
	
	/**
	 * Constructs a ByteBitSet with the given number of bits. If <code>numBits</code> is
	 * not divisible by 8, there will be some extra bits available (to fill the last byte in the
	 * backing array).
	 * 
	 * @param numBits number of bits to allocate
	 */
	public ByteBitSet(final long numBits) {
		int numBytes = (int)(numBits >>> 3);
		int remainder = (int)(numBits - (numBytes << 3));
		if (remainder != 0) {
			numBytes++;
		}
		
		array = new byte[numBytes];
	}
	
	/**
	 * Zeroes every bit in the set.
	 */
	public void clear() {
		for (int i = 0; i < array.length; i++) {
			array[i] = 0;
		}
	}
	
	/**
	 * Puts a string of bits in the set. <code>bitIndex</code> is the index at which to
	 * insert the bits. This refers to a bit index, not a byte index. <code>value</code> contains the bits to insert at
	 * the given index. <code>numBits</code> is the number of bits to insert. The least significant bits are taken from
	 * <code>value</code> and inserted in the backing array starting at <code>bitIndex</code>. Bits are inserted
	 * starting with the most significant bit.
	 * 
	 * @param bitIndex bit index at which to insert bits
	 * @param value integer containing bits to insert
	 * @param numBits number of bits to insert from <code>value</code>
	 * @return the bit index at which the last bit was inserted, plus one. This can be used as the <code>bitIndex</code>
	 * 		of the next call to <code>put()</code>
	 */
	public long put(final long bitIndex, final int value, final int numBits) {
		final int byteIndex = (int)(bitIndex >>> 3); // Divide by 8
		final int localBitIndex = (int)(bitIndex - (byteIndex << 3)); // Find the remainder
		final int masked = value & BITMASKS[numBits];
		
		if (numBits <= (8 - localBitIndex)) {
			array[byteIndex] |= (masked << (8 - numBits - localBitIndex));
		} else {
			int valueByteIndex = numBits >>> 3;
			final int valueExtraBits = numBits - (valueByteIndex << 3);
			final int availableSpace = 8 - localBitIndex;
			final long shifted;
			
			if (valueExtraBits < availableSpace) {
				shifted = ((long) masked) << (availableSpace - valueExtraBits);
			} else if (valueExtraBits > availableSpace) {
				shifted = (((long) masked) << 8) >>> (valueExtraBits - availableSpace);
				valueByteIndex++;
			} else {
				shifted = masked;
			}
			
			putLong(byteIndex, shifted, valueByteIndex);
		}
		
		return bitIndex + numBits;
	}
	
	/**
	 * Inserts the given long into the bitset. This method is used internally by {@link #put(long, int, int)},
	 * which aligns the bits in the long with the previous bits in the bitset. <code>valueStartByte</code>
	 * identifies the first byte in the long that contains nonzero bits (indexed from LSB) to prevent unnecessary
	 * bitwise or operations.
	 * 
	 * @param byteIndex byte index to backing array (<code>value</code> is inserted here)
	 * @param value bits to insert
	 * @param valueStartByte first byte in <code>value</code> containing nonzero bits
	 */
	private void putLong(final int byteIndex, final long value, final int valueStartByte) {
		final byte[] bytes = longToBytes(value);
		int arrayIndex = byteIndex;
		
		for (int i = (7 - valueStartByte); i < 8; i++) {
			array[arrayIndex] |= bytes[i];
			arrayIndex++;
		}
	}
	
	/**
	 * Breaks a long into an array of 8 bytes. The first byte in the array is the
	 * MSB (most significant byte).
	 * 
	 * @param l long to break into bytes
	 * @return 8 byte array
	 */
	private byte[] longToBytes(final long l) {
		final byte[] out = new byte[8];
		
		out[0] = (byte)((l & 0xFF00000000000000L) >>> 56);
		out[1] = (byte)((l & 0xFF000000000000L) >>> 48);
		out[2] = (byte)((l & 0xFF0000000000L) >>> 40);
		out[3] = (byte)((l & 0xFF00000000L) >>> 32);
		out[4] = (byte)((l & 0xFF000000L) >>> 24);
		out[5] = (byte)((l & 0xFF0000L) >>> 16);
		out[6] = (byte)((l & 0xFF00L) >>> 8);
		out[7] = (byte) (l & 0xFF);
		
		return out;
	}
	
	/**
	 * Generates a bitmask with the given length. The mask will contain <code>numBits</code> ones
	 * starting from the least significant bit. 
	 * 
	 * @param numBits number of bits to set to 1
	 * @return integer bitmask
	 */
	private static int createMask(int numBits) {
		int mask = 0;
		
		while (numBits > 0) {
			mask = 1 | (mask << 1);
			numBits--;
		}
		
		return mask;
	}
	
	/**
	 * Generates an array of bitmasks. The index of a bitmask is the number of ones
	 * in the bitmask (starting from the least significant bit).
	 * 
	 * @return 33 bitmasks
	 */
	private static int[] generateBitmasks() {
		final int[] out = new int[33];
		
		for (int i = 0; i < out.length; i++) {
			out[i] = createMask(i);
		}
		
		return out;
	}
	
	/**
	 * Returns the backing array for this ByteBitSet.
	 * <p>
	 * <b>WARNING:</b> Changes to this array will modify this ByteBitSet!
	 * 
	 * @return array of bits
	 */
	public byte[] getArray() {
		return array;
	}
}
