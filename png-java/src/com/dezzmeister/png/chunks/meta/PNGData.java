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
	
	public PNGData(final ColorType _colorType, final byte _bitDepth, final byte[][] _scanlines) {
		colorType = _colorType;
		bitDepth = _bitDepth;
		scanlines = _scanlines;
	}
}
