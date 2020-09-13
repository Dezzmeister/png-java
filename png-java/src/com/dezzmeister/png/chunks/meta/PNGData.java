package com.dezzmeister.png.chunks.meta;

/**
 * Raw PNG daa. The raw pixel data is stored along with the header info, in the format
 * specified by the header's fields. The pixel data is stored as an array of unfiltered scanlines.
 * 
 * @author Joe Desmond
 */
public class PNGData {
	
	/**
	 * The color type of the pixel data. This is one of several standard PNG color types
	 */
	public final ColorType colorType;
	
	/**
	 * The number of bits per sample
	 */
	public final byte bitDepth;
	
	/**
	 * Unfiltered scanlines. The data in this array will be filtered and compressed to form 1 or more IDAT chunks.
	 */
	public final byte[][] scanlines;
	
	public final int width;
	
	public final int height;
	
	public PNGData(final ColorType _colorType, final byte _bitDepth, final byte[][] _scanlines, final int _width, final int _height) {
		colorType = _colorType;
		bitDepth = _bitDepth;
		scanlines = _scanlines;
		width = _width;
		height = _height;
	}
	
	public final String debugInfo() {
		final StringBuilder sb = new StringBuilder();
		
		sb.append("Color type: " + colorType.name() + "\n");
		sb.append("Bit depth: " + bitDepth + "\n");
		sb.append("========Scanlines========");
		
		for (int line = 0; line < scanlines.length; line++) {
			final byte[] scanline = scanlines[line];
			sb.append("\n");
			
			for (int i = 0; i < scanline.length; i++) {
				sb.append(Integer.toHexString(Byte.toUnsignedInt(scanline[i])) + " ");
			}
		}
		
		return sb.toString();
	}
}
