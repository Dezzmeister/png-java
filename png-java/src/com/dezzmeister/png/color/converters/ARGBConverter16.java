package com.dezzmeister.png.color.converters;

import com.dezzmeister.png.chunks.meta.ColorType;
import com.dezzmeister.png.chunks.meta.PNGData;
import com.dezzmeister.png.color.ColorSpaceConverter;

/**
 * Converts an array of ARGB (or RGBA) pixels with 16-bit samples to a PNG format.
 * 
 * @author Joseph Desmond
 */
public class ARGBConverter16 implements ColorSpaceConverter {
	private final boolean alphaFirst;
	
	public ARGBConverter16(final boolean _alphaFirst) {
		alphaFirst = _alphaFirst;
	}

	@Override
	public PNGData convert(final int[] pixels, final int width, final int height) {
		final int bitdepth = 16;
		final byte[][] scanlines = new byte[height][];
		
		for (int line = 0; line < height; line++) {
			final byte[] samples = new byte[width * 8];
			int sampleIndex = 0;
			final int pixIndex = line * width * 4;
			
			for (int i = pixIndex; i < ((line + 1) * width * 4); i += 4) {
				final int alpha;
				final int red;
				final int green;
				final int blue;
				
				if (alphaFirst) {
					alpha = pixels[i];
					red = pixels[i + 1];
					green = pixels[i + 2];
					blue = pixels[i + 3];
				} else {
					red = pixels[i];
					green = pixels[i + 1];
					blue = pixels[i + 2];
					alpha = pixels[i + 3];
				}
				
				putInt16(red, samples, sampleIndex);
				putInt16(green, samples, sampleIndex + 2);
				putInt16(blue, samples, sampleIndex + 4);
				putInt16(alpha, samples, sampleIndex + 6);
				
				sampleIndex += 8;
			}
			
			scanlines[line] = samples;
		}
		
		return new PNGData(ColorType.RGB_ALPHA, (byte) bitdepth, scanlines, width, height);
	}
	
	private void putInt16(final int value, final byte[] bytes, final int index) {
		final byte b0 = (byte)((value >>> 8) & 0xFF);
		final byte b1 = (byte)(value & 0xFF);
		
		bytes[index] = b0;
		bytes[index + 1] = b1;
	}
}
