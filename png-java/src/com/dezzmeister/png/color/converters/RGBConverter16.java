package com.dezzmeister.png.color.converters;

import com.dezzmeister.png.chunks.meta.ColorType;
import com.dezzmeister.png.chunks.meta.PNGData;
import com.dezzmeister.png.color.ColorSpaceConverter;
import com.dezzmeister.png.data.ByteBitSet;

public class RGBConverter16 implements ColorSpaceConverter {

	@Override
	public PNGData convert(final int[] pixels, final int width, final int height) {
		final int bitdepth = 16;
		final byte[][] scanlines = new byte[height][];
		
		for (int line = 0; line < height; line++) {
			final ByteBitSet bits = new ByteBitSet(width * 48);
			long bitIndex = 0;
			final int pixIndex = line * width * 3;
			
			for (int i = pixIndex; i < (line * width * 3) + width; i++) {
				bitIndex = bits.put(bitIndex, pixels[pixIndex], bitdepth);
			}
			
			scanlines[line] = bits.getArray();
		}
		
		return new PNGData(ColorType.RGB, (byte) bitdepth, scanlines);
	}
}
