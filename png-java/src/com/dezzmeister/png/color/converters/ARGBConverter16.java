package com.dezzmeister.png.color.converters;

import com.dezzmeister.png.chunks.meta.ColorType;
import com.dezzmeister.png.chunks.meta.PNGData;
import com.dezzmeister.png.color.ColorSpaceConverter;
import com.dezzmeister.png.data.ByteBitSet;

/**
 * Converts an array of ARGB pixels with 16-bit samples to a PNG format.
 * 
 * @author Joseph Desmond
 */
public class ARGBConverter16 implements ColorSpaceConverter {

	@Override
	public PNGData convert(final int[] pixels, final int width, final int height) {
		final int bitdepth = 16;
		final byte[][] scanlines = new byte[height][];
		
		for (int line = 0; line < height; line++) {
			final ByteBitSet bits = new ByteBitSet(width * 64);
			long bitIndex = 0;
			final int pixIndex = line * width * 4;
			
			for (int i = pixIndex; i < (line * width * 3) + width; i += 4) {
				final int alpha = pixels[i];
				final int red = pixels[i + 1];
				final int green = pixels[i + 2];
				final int blue = pixels[i + 3];
				
				bitIndex = bits.put(bitIndex, red, bitdepth);
				bitIndex = bits.put(bitIndex, green, bitdepth);
				bitIndex = bits.put(bitIndex, blue, bitdepth);
				bitIndex = bits.put(bitIndex, alpha, bitdepth);
			}
			
			scanlines[line] = bits.getArray();
		}
		
		return new PNGData(ColorType.RGB_ALPHA, (byte) bitdepth, scanlines);
	}
}
