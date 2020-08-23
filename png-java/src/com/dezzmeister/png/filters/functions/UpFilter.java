package com.dezzmeister.png.filters.functions;

import com.dezzmeister.png.filters.FilterFunction;

/**
 * The Up filter, which uses values in the current and previous scanlines. If the current scanline
 * is the first one, the filter assumes that the previous scanline is zero. The Up filter
 * does not depend of the number of bytes per pixel.
 * 
 * @author Joe Desmond
 */
public class UpFilter implements FilterFunction {

	@Override
	public byte[] applyFilter(final byte[] prevLine, final byte[] thisLine, final int bytesPerPixel) {
		final byte[] out = new byte[thisLine.length];
		
		if (prevLine == null) {
			System.arraycopy(thisLine, 0, out, 0, thisLine.length);
		} else {
			for (int i = 0; i < thisLine.length; i++) {
				out[i] = (byte) (thisLine[i] - prevLine[i]);
			}
		}
		
		return out;
	}

	@Override
	public byte[] removeFilter(final byte[] prevLine, final byte[] thisLine, final int bytesPerPixel) {
		final byte[] out = new byte[thisLine.length];
		
		if (prevLine == null) {
			System.arraycopy(thisLine, 0, out, 0, thisLine.length);
		} else {
			for (int i = 0; i < thisLine.length; i++) {
				out[i] = (byte) (thisLine[i] + prevLine[i]);
			}
		}
		
		return out;
	}

}
