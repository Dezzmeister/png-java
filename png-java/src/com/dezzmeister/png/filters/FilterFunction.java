package com.dezzmeister.png.filters;

/**
 * A PNG filtering function. Specifies two functions: one to apply the filter, and another to remove it.
 * Filters are applied to individual scanlines instead of the entire image at once, so that different filters can be
 * applied to each line.
 * 
 * @author Joe Desmond
 */
public interface FilterFunction {
	
	/**
	 * Applies a filter to <code>thisLine</code> and returns the filtered line. <code>prevLine</code>
	 * may not be used, depending on the filtering algorithm. If this argument is null, 
	 * the filter function will treat it as zeroes (for example, when filtering the first line).
	 * 
	 * @param prevLine previous scanline
	 * @param thisLine scanline being filtered
	 * @param bytesPerPixel number of bytes in one pixel, rounded up to 1 if fractional
	 * @return filtered scanline
	 */
	byte[] applyFilter(byte[] prevLine, byte[] thisLine, int bytesPerPixel);
	
	/**
	 * Removes a filter from <code>thisLine</code> and returns the unfiltered line. <code>prevLine</code>
	 * may not be used, depending on the filtering algorithm. If this argument is null, 
	 * the filter reversal function will treat it as zeroes (for example, when reversing the first line).
	 * 
	 * @param prevLine previous <b>DECODED</b> (unfiltered) scanline, or null
	 * @param thisLine scanline with filter being removed
	 * @param bytesPerPixel number of bytes in one pixel, rounded up to 1 if fractional
	 * @return unfiltered scanline
	 */
	byte[] removeFilter(byte[] prevLine, byte[] thisLine, int bytesPerPixel);
}
