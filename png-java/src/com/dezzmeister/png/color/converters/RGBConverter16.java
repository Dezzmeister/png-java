package com.dezzmeister.png.color.converters;

import com.dezzmeister.png.chunks.meta.ColorType;
import com.dezzmeister.png.chunks.meta.PNGData;
import com.dezzmeister.png.color.ColorSpaceConverter;
import com.dezzmeister.png.data.ByteBitSet;

/**
 * Converts an array of RGB pixels with 16-bit samples to a PNG format.
 * 
 * @author Joseph Desmond
 */
public class RGBConverter16 implements ColorSpaceConverter {

	@Override
	public PNGData convert(final int[] pixels, final int width, final int height) {
		final int bitdepth = 16;
		final byte[][] scanlines = new byte[height][];
		
		for (int line = 0; line < height; line++) {
			final ByteBitSet bits = new ByteBitSet(width * 48);
			long bitIndex = 0;
			final int pixIndex = line * width * 3;
			
			for (int i = pixIndex; i < ((line + 1) * width * 3); i++) {
				bitIndex = bits.put(bitIndex, pixels[i], bitdepth);
			}
			
			scanlines[line] = bits.getArray();
		}
		
		return new PNGData(ColorType.RGB, (byte) bitdepth, scanlines);
	}
}
