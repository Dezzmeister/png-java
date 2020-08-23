package com.dezzmeister.png.filters.functions;

import com.dezzmeister.png.filters.FilterFunction;

/**
 * The Sub filter, which only uses values in the current scanline. Both of these functions ignore
 * <code>prevLine</code> and <code>nextLine</code>.
 * 
 * @author Joe Desmond
 */
public class SubFilter implements FilterFunction {

	@Override
	public byte[] applyFilter(final byte[] prevLine, final byte[] thisLine, final int bytesPerPixel) {
		final byte[] out = new byte[thisLine.length];
		
		out[0] = thisLine[0];
		
		for (int i = 1; i < thisLine.length; i++) {
			final int prevIndex = i - bytesPerPixel;
			
			if (prevIndex < 0) {
				out[i] = thisLine[i];
			} else {
				out[i] = (byte) (thisLine[i] - thisLine[prevIndex]);
			}
		}
		
		return out;
	}

	@Override
	public byte[] removeFilter(final byte[] prevLine, final byte[] thisLine, final int bytesPerPixel) {
		final byte[] out = new byte[thisLine.length];
		
		for (int i = 0; i < thisLine.length; i++) {
			final int prevIndex = i - bytesPerPixel;
			
			if (prevIndex < 0) {
				out[i] = thisLine[i];
			} else {
				out[i] = (byte) (thisLine[i] + out[prevIndex]);
			}
		}
		
		return out;
	}

}
