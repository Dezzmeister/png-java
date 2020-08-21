package com.dezzmeister.png.color.converters;

import com.dezzmeister.png.chunks.meta.ColorType;
import com.dezzmeister.png.chunks.meta.PNGData;
import com.dezzmeister.png.color.ColorSpaceConverter;
import com.dezzmeister.png.data.ByteBitSet;

/**
 * Converts 16-bit grayscale images to unfiltered PNG scanlines. If possible, a lower
 * bit depth is used (instead of the default 16 bits). The {@link #ALIGNMENTS} table defines
 * what values can be used to reduce the bit depth. If every 16-bit grayscale pixel is a multiple of one
 * of the values in the table (besides 1), the bit depth can be reduced. For example: to achieve a bit depth of 1,
 * your samples can either be 0 or 65535. To achieve a bit depth of 2, they can either be 0 or some multiple of
 * 21845. The samples must be <b>EXACT</b> multiples to force the converter to reduce the bit depth. In the interest
 * of preserving every bit of pixel data, the converter will not reduce the bit depth if samples are "close enough"
 * to a multiple.
 * 
 * @author Joe Desmond
 * @see #ALIGNMENTS
 */
public class GrayscaleConverter implements ColorSpaceConverter {
	
	/**
	 * List of allowed (PNG standard) bit depths
	 */
	public static final byte[] ALLOWED_DEPTHS = {1, 2, 8, 4, 16};
	
	/**
	 * The converter uses these values to determine if a pixel array can be represented with less than 16 bits.
	 * By default, this converter converts from 16-bit grayscale images. If all pixels are a multiple
	 * of a value in this table (or zero), the corresponding bit depth will be used instead. Alignments in this table
	 * correspond to bit depths in {@link #ALLOWED_DEPTHS}.
	 * 
	 * @see #determineOptimalBitDepth(int[])
	 */
	public static final int[] ALIGNMENTS = {65535, 21845, 4369, 256, 1};

	@Override
	public PNGData convert(final int[] pixels, final int width, final int height) {
		final int bitdepth = determineOptimalBitDepth(pixels);
		final byte[][] scanlines = new byte[height][];
		
		for (int line = 0; line < scanlines.length; line++) {
			final ByteBitSet bits = new ByteBitSet(bitdepth * width);
			long bitIndex = 0;
			final int pixIndex = line * width;
			
			for (int i = pixIndex; i < (line * width) + width; i++) {
				final int pixel = convertToBitDepth(pixels[i], bitdepth);
				bitIndex = bits.put(bitIndex, pixel, bitdepth);
			}
			
			scanlines[line] = bits.getArray();
		}
		
		return new PNGData(ColorType.GRAYSCALE, (byte) bitdepth, scanlines);
	}
	
	/**
	 * Converts a 16-bit grayscale pixel to the given bit depth.
	 * 
	 * @param pixel 16-bit grayscale pixel
	 * @param depth bit depth
	 * @return <code>pixel</code> represented in <code>depth</code> bits
	 */
	private int convertToBitDepth(final int pixel, final int depth) {
		if (depth == 16) {
			return pixel;
		}
		
		return pixel / ALIGNMENTS[depth - 1];
	}
	
	/**
	 * Determines the optimal bit depth for the given pixels. The pixels given are 16-bit grayscale, and this
	 * function will return the smallest allowed bit depth per sample.
	 * <p>
	 * For example, consider these pixels: <code>{0, 0, 65535, 65535, 65535, 0, 65535}</code><br>
	 * This function will return <code>1</code>, because only 1 bit is needed to represent a single pixel.
	 * <p>
	 * Similarly, for these pixels: <code>{0, 65535, 21845, 0, 43690, 21845, 0, 65535}</code><br>
	 * This function will return <code>2</code>, because only 2 bits are needed to represent a single pixel.
	 * <p>
	 * To force the converter to optimize the bit depth, make sure that your pixels are all multiples of 
	 * the desired value in {@link #ALIGNMENTS}. The pixel values must be exact; the converter will not consider a one-off
	 * value to be a multiple. For example: <code>{0, 65535, 65534}</code> would return a bit depth of <code>16</code>.
	 * 
	 * @param pixels 16-bit grayscale pixels
	 * @return optimal bit depth for the given pixels
	 */
	private static int determineOptimalBitDepth(final int[] pixels) {
		int bestDepthIndex = 0;
		
		for (int pixel : pixels) {
			while ((pixel % ALIGNMENTS[bestDepthIndex] != 0) && (bestDepthIndex != 4)) {
				bestDepthIndex++;
			}
			
			if (bestDepthIndex == 4) {
				return 16;
			}
		}
		
		return ALLOWED_DEPTHS[bestDepthIndex];
	}
}
