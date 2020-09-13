package com.dezzmeister.png.color.converters;

import com.dezzmeister.png.chunks.meta.ColorType;
import com.dezzmeister.png.chunks.meta.PNGData;
import com.dezzmeister.png.color.ColorSpaceConverter;

public class ARGBConverter8 implements ColorSpaceConverter {
	private final boolean alphaFirst;
	
	public ARGBConverter8(final boolean _alphaFirst) {
		alphaFirst = _alphaFirst;
	}

	@Override
	public PNGData convert(final int[] pixels, final int width, final int height) {
		final byte bitdepth = 8;
		final byte[][] scanlines = new byte[height][];
		
		for (int line = 0; line < height; line++) {
			final byte[] samples = new byte[width * 4];
			int sampleIndex = 0;
					
			for (int i = (line * width); i < ((line + 1) * width); i++) {
				int pixel = pixels[i];
				
				if (alphaFirst) {
					final int alpha = (pixel >>> 24) & 0xFF;
					pixel = (pixel << 8) | alpha;
				}
				
				sampleIndex = putInt32(pixel, samples, sampleIndex);
			}
			
			scanlines[line] = samples;
		}
		
		return new PNGData(ColorType.RGB_ALPHA, bitdepth, scanlines, width, height);
	}
	
	private int putInt32(final int value, final byte[] bytes, final int startIndex) {
		final byte b0 = (byte) ((value >>> 24) & 0xFF);
		final byte b1 = (byte) ((value >>> 16) & 0xFF);
		final byte b2 = (byte) ((value >>> 8) & 0xFF);
		final byte b3 = (byte) (value & 0xFF);
		
		bytes[startIndex] = b0;
		bytes[startIndex + 1] = b1;
		bytes[startIndex + 2] = b2;
		bytes[startIndex + 3] = b3;
		
		return startIndex + 4;
	}
}
