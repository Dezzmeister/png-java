package com.dezzmeister.png.junit.converters;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.dezzmeister.png.chunks.meta.ColorType;
import com.dezzmeister.png.chunks.meta.PNGData;
import com.dezzmeister.png.color.converters.RGBConverter16;

public class RGBConverter16Test {
	private final RGBConverter16 converter = new RGBConverter16();
	
	@Test()
	public void test() {
		final int[] pixels = {
			0x0000, 0x0000, 0x0000,		0xFF00, 0x00FF, 0x0000,		0x0FF0, 0x0000, 0xFF00,
			0x1234, 0x1738, 0x0000,		0x0123, 0x4567, 0x89AB,		0xCDEF, 0x0000, 0x0000,
			0x1212, 0x6193, 0xDB16,		0x7777, 0xB18A, 0xE01C,		0x0001, 0x0002, 0x0003
		};
		
		final int width = 3;
		final int height = 3;
		
		final PNGData data = converter.convert(pixels, width, height);
		
		System.out.println("Converter should preserve bit depth of 16: " + data.bitDepth);
		assertEquals(data.bitDepth, 16);
		
		System.out.println("Converter should encode PNG color type " + ColorType.RGB.name() + ": " + data.colorType.name());
		assertEquals(data.colorType, ColorType.RGB);
		
		System.out.println("PNG scanlines should be encoded properly");
		final int[][] expectedScanlines = {
				{0x00, 0x00, 0x00, 0x00, 0x00, 0x00,		0xFF, 0x00, 0x00, 0xFF, 0x00, 0x00,		0x0F, 0xF0, 0x00, 0x00, 0xFF, 0x00},
				{0x12, 0x34, 0x17, 0x38, 0x00, 0x00,		0x01, 0x23, 0x45, 0x67, 0x89, 0xAB,		0xCD, 0xEF, 0x00, 0x00, 0x00, 0x00},
				{0x12, 0x12, 0x61, 0x93, 0xDB, 0x16,		0x77, 0x77, 0xB1, 0x8A, 0xE0, 0x1C,		0x00, 0x01, 0x00, 0x02, 0x00, 0x03}
		};
		
		assertEquals(data.scanlines.length, expectedScanlines.length);
		
		for (int line = 0; line < data.scanlines.length; line++) {
			final byte[] actual = data.scanlines[line];
			final int[] expected = expectedScanlines[line];
			
			for (int i = 0; i < actual.length; i++) {
				assertEquals(Byte.toUnsignedInt(actual[i]), expected[i]);
			}
		}
	}
}
