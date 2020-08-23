package com.dezzmeister.png.filters;

import com.dezzmeister.png.filters.functions.NoneFilter;
import com.dezzmeister.png.filters.functions.SubFilter;
import com.dezzmeister.png.filters.functions.UpFilter;

/**
 * PNG filter functions. Only these five filters are listed in the PNG standard.
 * 
 * @author Joe Desmond
 */
public enum Filter {
	NONE((byte) 0, new NoneFilter()),
	SUB((byte) 1, new SubFilter()),
	UP((byte) 2, new UpFilter()),
	AVERAGE,
	PAETH;
	
	private final byte typeCode;
	
	private final FilterFunction filterFunction;
	
	private Filter(final byte _typeCode, final FilterFunction _filterFunction) {
		typeCode = _typeCode;
		filterFunction = _filterFunction;
	}
}
