package com.dezzmeister.png.color.converters;

import com.dezzmeister.png.chunks.meta.ColorType;
import com.dezzmeister.png.chunks.meta.PNGData;
import com.dezzmeister.png.color.ColorSpaceConverter;

/**
 * Converts an array of RGB pixels (8 bits per sample) to a PNG format.
 * 
 * @author Joe Desmond
 */
public class RGBConverter8 implements ColorSpaceConverter {

	@Override
	public PNGData convert(final int[] pixels, final int width, final int height) {
		final byte bitdepth = 8;
		final byte[][] scanlines = new byte[height][];
		
		for (int line = 0; line < height; line++) {
			final byte[] samples = new byte[width * 3];
			int sampleIndex = 0;
			
			for (int i = (line * width); i < ((line + 1) * width); i++) {
				final int pixel = pixels[i];
				final int red = (pixel >>> 16) & 0xFF;
				final int green = (pixel >>> 8) & 0xFF;
				final int blue = pixel & 0xFF;
				
				samples[sampleIndex++] = (byte) red;
				samples[sampleIndex++] = (byte) green;
				samples[sampleIndex++] = (byte) blue;
			}
			
			scanlines[line] = samples;
		}
		
		return new PNGData(ColorType.RGB, bitdepth, scanlines, width, height);
	}

}
