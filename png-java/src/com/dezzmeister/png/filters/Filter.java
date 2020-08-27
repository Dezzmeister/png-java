package com.dezzmeister.png.filters;

import com.dezzmeister.png.filters.functions.AverageFilter;
import com.dezzmeister.png.filters.functions.NoneFilter;
import com.dezzmeister.png.filters.functions.PaethFilter;
import com.dezzmeister.png.filters.functions.SubFilter;
import com.dezzmeister.png.filters.functions.UpFilter;

/**
 * PNG filter functions. Only the first five filters are listed in the PNG standard. The PNG standard does not
 * include an "dynamic" filter; this option is only given so that the user can let the encoder pick the best filter
 * per line. If any other filter is specified, that filter will be used for every line.
 * <p>
 * <b>NOTE:</b> The Dynamic option does NOT have an associated filter function, and the type code is garbage
 * 
 * @author Joe Desmond
 */
public enum Filter {
	
	/**
	 * No filter
	 */
	NONE((byte) 0, new NoneFilter()),
	
	/**
	 * The Sub filter
	 */
	SUB((byte) 1, new SubFilter()),
	
	/**
	 * The Up filter
	 */
	UP((byte) 2, new UpFilter()),
	
	/**
	 * The Average filter
	 */
	AVERAGE((byte) 3, new AverageFilter()),
	
	/**
	 * The Paeth filter
	 */
	PAETH((byte) 4, new PaethFilter()),
	
	/**
	 * A custom filtering strategy. If this is specified, the encoder will choose the appropriate
	 * filter for each line.
	 */
	DYNAMIC((byte) -1, null);
	
	/**
	 * The PNG filter type code of this filter. A line using this filter will start with
	 * this byte.
	 */
	public final byte typeCode;
	
	/**
	 * The actual filter function. Contains a function to apply the filter and one to reverse it.
	 * Can be used by calling {@link #filterFunction()}.
	 */
	private final FilterFunction filterFunction;
	
	private Filter(final byte _typeCode, final FilterFunction _filterFunction) {
		typeCode = _typeCode;
		filterFunction = _filterFunction;
	}
	
	/**
	 * Returns the filter function, which can be used to apply or reverse this filter.
	 * 
	 * @return the filter function
	 */
	public FilterFunction filterFunction() {
		return filterFunction;
	}
	
	/**
	 * Returns the filter with the given filter type code. Throws an exception if the filter type code is unrecognized
	 * (if {@link #isSupportedFilterType(byte)} returns false).
	 * 
	 * @param filterTypeCode filter type code
	 * @return the filter with the given type code
	 */
	public static Filter getFilter(final byte filterTypeCode) {
		for (final Filter filter : Filter.values()) {
			if (filter.typeCode == filterTypeCode) {
				return filter;
			}
		}
		
		throw new IllegalArgumentException("Unknown filter type: " + filterTypeCode);
	}
	
	/**
	 * Returns true if the given filter type code is supported. A simple check could be done here
	 * to test if <code>filterTypeCode</code> is between 0 and 4 (inclusive), but a loop is used
	 * in case a new filter type is added, although this is unlikely.
	 * 
	 * @param filterTypeCode filter type code
	 * @return true if the filter type code is a supported filter
	 */
	public static boolean isSupportedFilterType(final byte filterTypeCode) {
		for (final Filter filter : Filter.values()) {
			if (filter.typeCode == filterTypeCode) {
				return true;
			}
		}
		
		return false;
	}
}
