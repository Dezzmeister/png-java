package com.dezzmeister.png.test;

import java.util.Arrays;

import com.dezzmeister.png.filters.FilterFunction;
import com.dezzmeister.png.filters.functions.SubFilter;

public class FilterTest {
	
	public static void main(final String[] args) {
		final FilterFunction filter = new SubFilter();
		
		final byte[] line = new byte[256];
		
		for (int i = 0; i < 256; i++) {
			line[i] = (byte)i;
		}
		
		final byte[] filtered = filter.applyFilter(null, line, 1);
		System.out.println(Arrays.toString(filtered));
		
		final byte[] reversed = filter.removeFilter(null, filtered, 1);
		System.out.println(Arrays.toString(reversed));
	}
}
