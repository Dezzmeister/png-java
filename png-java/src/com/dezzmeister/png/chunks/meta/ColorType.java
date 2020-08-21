package com.dezzmeister.png.chunks.meta;

/**
 * The standard PNG color types.
 * 
 * @author Joe Desmond
 */
public enum ColorType {
	
	/**
	 * Each pixel is made of one sample: a grayscale value
	 */
	GRAYSCALE((byte) 0, new byte[] {1, 2, 4, 8, 16}),
	
	/**
	 * Each pixel is made of three samples: red, green, blue (in that order)
	 */
	RGB((byte) 2, new byte[] {8, 16}),
	
	/**
	 * Each pixel is made of one sample: an index into a color palette
	 */
	PALETTE((byte) 3, new byte[] {1, 2, 4, 8}),
	
	/**
	 * Each pixel is made of two samples: a grayscale value and an alpha (in that order)
	 */
	GRAYSCALE_ALPHA((byte) 4, new byte[] {8, 16}),
	
	/**
	 * Each pixel is made of four samples: red, green, blue, alpha (in that order)
	 */
	RGB_ALPHA((byte) 6, new byte[] {8, 16});
	
	/**
	 * The type code, used in the IHDR chunk to identify the color type
	 */
	private final byte typeCode;
	
	/**
	 * The acceptable bit depths for this color type
	 */
	private final byte[] bitDepths;
	
	private ColorType(final byte _typeCode, final byte[] _bitDepths) {
		typeCode = _typeCode;
		bitDepths = _bitDepths;
	}
	
	/**
	 * Returns the type code for a given color type. The type code
	 * is used in the IHDR chunk to identify the color type.
	 * 
	 * @return type code
	 */
	public byte getTypeCode() {
		return typeCode;
	}
	
	/**
	 * Returns true if the given bit depth is allowed by an image with this color type.
	 * 
	 * @param bitDepth bit depth
	 * @return true if <code>bitDepth</code> is a valid bit depth for an image of this type
	 */
	public boolean isAllowedBitDepth(final byte bitDepth) {
		for (final byte validDepth : bitDepths) {
			if (bitDepth == validDepth) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns the list of allowed bit depths for this color type.
	 * 
	 * @return allowed bit depths
	 */
	public byte[] allowedBitDepths() {
		return bitDepths;
	}
	
	/**
	 * Returns a ColorType object for the given color type code. If the color type code is invalid
	 * (i.e., if {@link #isValidColorType(byte)} returns false), an exception is thrown.
	 * 
	 * @param colorTypeCode PNG color type code (one of: 0, 2, 3, 4, 6)
	 * @return ColorType object for the given code
	 */
	public static ColorType getColorType(final byte colorTypeCode) {
		for (final ColorType type : values()) {
			if (type.typeCode == colorTypeCode) {
				return type;
			}
		}
		
		throw new IllegalArgumentException("Invalid color type! (" + colorTypeCode + ")");
	}
	
	/**
	 * Returns true if the given color type code is a valid type code. The PNG specification only recognizes
	 * 5 color type codes: 0, 2, 3, 4, 6.
	 * 
	 * @param colorTypeCode color type code
	 * @return true if the given color type code is valid
	 * @see <a href="http://www.libpng.org/pub/png/spec/1.2/PNG-Chunks.html#C.IHDR">PNG Specification</a>
	 */
	public static boolean isValidColorType(final byte colorTypeCode) {
		for (final ColorType type : values()) {
			if (type.typeCode == colorTypeCode) {
				return true;
			}
		}
		
		return false;
	}
}
