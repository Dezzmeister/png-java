package com.dezzmeister.png.color;

import com.dezzmeister.png.chunks.meta.PNGData;

/**
 * A function to convert pixels in one color space to another. Pixels are represented as integers
 * (formats specified by {@link ColorFormat}) and a {@link PNGData} object is returned. 
 * The header info contains information about the color space and bit depth, as well as the raw
 * data in the destination color format.
 * 
 * @author Joe Desmond
 */
@FunctionalInterface
public interface ColorSpaceConverter {
	
	/**
	 * Convert the given 4-byte pixel array to a byte array with header info
	 * (such as color type and bit depth).
	 * 
	 * @param pixels pixel array
	 * @param width image width
	 * @param height image height
	 * @return header info with raw data (to be compressed in IDAT chunks)
	 */
	PNGData convert(int[] pixels, int width, int height);
}
