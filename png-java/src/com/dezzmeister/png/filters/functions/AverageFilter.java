package com.dezzmeister.png.filters.functions;

import com.dezzmeister.png.filters.FilterFunction;

public class AverageFilter implements FilterFunction {

	@Override
	public byte[] applyFilter(final byte[] prevLine, final byte[] thisLine, final int bytesPerPixel) {
		final byte[] out = new byte[thisLine.length];
		
		for (int i = 0; i < thisLine.length; i++) {
			final int leftIndex = i - bytesPerPixel;
			final byte left;
			final byte up;
			
			if (leftIndex < 0) {
				left = 0;
			} else {
				left = thisLine[leftIndex];
			}
			
			if (prevLine == null) {
				up = 0;
			} else {
				up = prevLine[i];
			}
			
			out[i] = (byte) ((left + up) >>> 1);
		}
		
		return out;
	}

	@Override
	public byte[] removeFilter(final byte[] prevLine, final byte[] thisLine, final int bytesPerPixel) {
		// TODO Auto-generated method stub
		return null;
	}

}
