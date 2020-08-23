package com.dezzmeister.png.junit.converters;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.dezzmeister.png.chunks.meta.ColorType;
import com.dezzmeister.png.chunks.meta.PNGData;
import com.dezzmeister.png.color.converters.GrayscaleConverter;

public class GrayscaleConverterTest {
	
	private GrayscaleConverter converter = new GrayscaleConverter();

	@Test
	public void test() {
		final int[] pixels = {0, 65535, 21845, 21845, 43690, 65535, 43690, 0, 0, 0, 65535, 21845};
		final int width = 3;
		final int height = 4;
		
		final PNGData data = converter.convert(pixels, width, height);
		
		System.out.println("Converter should infer bit depth of 2: " + data.bitDepth);
		assertEquals(data.bitDepth, 2);
		
		System.out.println("Converter should encode PNG color type " + ColorType.GRAYSCALE.name() + ": " + data.colorType.name());
		assertEquals(data.colorType, ColorType.GRAYSCALE);
		
		System.out.println("PNG scanlines should be encoded properly");
		final byte[][] expectedScanlines = {
				{0x34},
				{0x6C},
				{(byte) 0x80},
				{0x34}
		};
		
		assertEquals(data.scanlines.length, expectedScanlines.length);
		
		for (int i = 0; i < data.scanlines.length; i++) {
			assertArrayEquals(data.scanlines[i], expectedScanlines[i]);
		}
	}

}
