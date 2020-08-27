package com.dezzmeister.png.filters.functions;

import com.dezzmeister.png.filters.FilterFunction;

/**
 * The Paeth filter, devised by Alan W. Paeth. Uses the current byte, the left byte, and the upper left byte.
 * 
 * @author Joe Desmond
 */
public class PaethFilter implements FilterFunction {

	@Override
	public byte[] applyFilter(final byte[] prevLine, final byte[] thisLine, final int bytesPerPixel) {
		final byte[] out = new byte[thisLine.length];
		
		for (int i = 0; i < thisLine.length; i++) {
			final int leftIndex = i - bytesPerPixel;
			final byte left;
			final byte up;
			final byte upperLeft;
			
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
			
			if (prevLine == null || leftIndex < 0) {
				upperLeft = 0;
			} else {
				upperLeft = prevLine[leftIndex];
			}
			
			out[i] = (byte) (thisLine[i] - paethPredictor(left, up, upperLeft));
		}
		
		return out;
	}

	@Override
	public byte[] removeFilter(final byte[] prevLine, final byte[] thisLine, final int bytesPerPixel) {
		final byte[] out = new byte[thisLine.length];
		
		for (int i = 0; i < thisLine.length; i++) {
			final int leftIndex = i - bytesPerPixel;
			final byte left;
			final byte up;
			final byte upperLeft;
			
			if (leftIndex < 0) {
				left = 0;
			} else {
				left = out[leftIndex];
			}
			
			if (prevLine == null) {
				up = 0;
			} else {
				up = prevLine[i];
			}
			
			if (prevLine == null || leftIndex < 0) {
				upperLeft = 0;
			} else {
				upperLeft = prevLine[leftIndex];
			}
			
			out[i] = (byte) (thisLine[i] + paethPredictor(left, up, upperLeft));
		}
		
		return out;
	}
	
	private byte paethPredictor(final byte left, final byte above, final byte upperLeft) {
		
		// Convert to unsigned ints
		final int ua = ((int)left & 0xFF);
		final int ub = ((int)above & 0xFF);
		final int uc = ((int)upperLeft & 0xFF);
		
		final int p = ua + ub - uc;
		final int pa = Math.abs(p - ua);
		final int pb = Math.abs(p - ub);
		final int pc = Math.abs(p - uc);
		
		if (pa <= pb && pa <= pc) {
			return left;
		} else if (pb <= pc) {
			return above;
		} else {
			return upperLeft;
		}
	}
}
