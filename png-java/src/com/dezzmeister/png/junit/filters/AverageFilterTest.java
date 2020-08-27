package com.dezzmeister.png.junit.filters;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;

import org.junit.Test;

import com.dezzmeister.png.filters.FilterFunction;
import com.dezzmeister.png.filters.functions.AverageFilter;

public class AverageFilterTest {
	private final FilterFunction filter = new AverageFilter();
	
	@Test
	public void test() {
		
		// Simulate RGB pixels
		final int bytesPerPixel = 3;
		final int height = 32;
		final int width = 32;
		
		final byte[][] scanlines = new byte[height][width * bytesPerPixel];
		
		for (int line = 0; line < height; line++) {
			for (int i = 0; i < scanlines[line].length; i++) {
				scanlines[line][i] = (byte)(Math.random() * 256);
			}
		}
		
		final byte[][] filtered = applyFilter(scanlines, bytesPerPixel);
		
		System.out.println("Filtered image should be different from original");
		
		for (int line = 0; line < filtered.length; line++) {
			assertFalse(Arrays.equals(scanlines[line], filtered[line]));
		}
		
		final byte[][] reversed = removeFilter(filtered, bytesPerPixel);
		
		System.out.println("Reversed filter should be exactly like original");
		
		for (int line = 0; line < reversed.length; line++) {
			assertArrayEquals(scanlines[line], reversed[line]);
		}		
	}
	
	private byte[][] applyFilter(final byte[][] scanlines, final int bytesPerPixel) {
		final byte[][] out = new byte[scanlines.length][scanlines[0].length];
		
		out[0] = filter.applyFilter(null, scanlines[0], bytesPerPixel);
		
		for (int line = 1; line < scanlines.length; line++) {
			out[line] = filter.applyFilter(scanlines[line - 1], scanlines[line], bytesPerPixel);
		}
		
		return out;
	}
	
	private byte[][] removeFilter(final byte[][] filtered, final int bytesPerPixel) {
		final byte[][] out = new byte[filtered.length][filtered[0].length];
		
		out[0] = filter.removeFilter(null, filtered[0], bytesPerPixel);
		
		for (int line = 1; line < filtered.length; line++) {
			out[line] = filter.removeFilter(out[line - 1], filtered[line], bytesPerPixel);
		}
		
		return out;
	}
}
