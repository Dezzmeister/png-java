package com.dezzmeister.png.junit.converters;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.dezzmeister.png.chunks.meta.ColorType;
import com.dezzmeister.png.chunks.meta.PNGData;
import com.dezzmeister.png.color.converters.RGBConverter8;

public class RGBConverter8Test {
	
	private RGBConverter8 converter = new RGBConverter8();
	
	@Test
	public void test() {
		final int[] pixels = {
			0x112233, 0x445566, 0x778899,		0xAABBCC, 0xDDEEFF, 0xFFEEDD,
			0xCCBBAA, 0x998877, 0x665544,		0x332211, 0x00FF00, 0xFF00FF
		};
		
		final int width = 6;
		final int height = 2;
		
		final PNGData data = converter.convert(pixels, width, height);
		
		System.out.println("Bit depth should be 8: " + data.bitDepth);
		assertEquals(data.bitDepth, 8);
		
		System.out.println("Converter should encode PNG color type " + ColorType.RGB.name() + ": " + data.colorType.name());
		assertEquals(data.colorType, ColorType.RGB);
		
		final int[][] expectedScanlines = {
				{0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77, 0x88, 0x99, 0xAA, 0xBB, 0xCC, 0xDD, 0xEE, 0xFF, 0xFF, 0xEE, 0xDD},
				{0xCC, 0xBB, 0xAA, 0x99, 0x88, 0x77, 0x66, 0x55, 0x44, 0x33, 0x22, 0x11, 0x00, 0xFF, 0x00, 0xFF, 0x00, 0xFF}
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
