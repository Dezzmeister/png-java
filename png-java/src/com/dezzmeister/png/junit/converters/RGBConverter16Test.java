package com.dezzmeister.png.junit.converters;

import org.junit.jupiter.api.Test;

import com.dezzmeister.png.chunks.meta.PNGData;
import com.dezzmeister.png.color.converters.RGBConverter16;

public class RGBConverter16Test {
	private static RGBConverter16 converter = new RGBConverter16();
	
	@Test()
	public static void test() {
		final int[] pixels = {
			0x0000, 0x0000, 0x0000,		0xFF00, 0x00FF, 0x0000,		0x0FF0, 0x0000, 0xFF00,
			0x1234, 0x1738, 0x0000,		0x0123, 0x4567, 0x89AB,		0xCDEF, 0x0000, 0x0000,
			0x1212, 0x6193, 0xDB16,		0x7777, 0xB18A, 0xE01C,		0x0001, 0x0002, 0x0003
		};
		
		final int width = 3;
		final int height = 3;
		
		final PNGData data = converter.convert(pixels, width, height);
		System.out.println(data.debugInfo());
	}
	
	public static final void main(final String[] args) {
		test();
	}
}
