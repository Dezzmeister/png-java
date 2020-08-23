package com.dezzmeister.png.filters.functions;

import com.dezzmeister.png.filters.FilterFunction;

/**
 * This filter function doesn't modify the data in any way; it's only included because
 * every {@link com.dezzmeister.png.filters.Filter Filter} instance needs to have a filter function.
 * 
 * @author Joe Desmond
 */
public class NoneFilter implements FilterFunction {

	@Override
	public byte[] applyFilter(final byte[] prevLine, final byte[] thisLine, final int bytesPerPixel) {
		
		return thisLine;
	}

	@Override
	public byte[] removeFilter(final byte[] prevLine, final byte[] thisLine, final int bytesPerPixel) {
		
		return thisLine;
	}

}
