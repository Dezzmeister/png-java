package com.dezzmeister.png.color;

import com.dezzmeister.png.chunks.meta.PNGData;
import com.dezzmeister.png.color.converters.GrayscaleConverter;

/**
 * Various color formats. Each format has a conversion function to convert pixels of the format
 * to a standard PNG format. There are five PNG color types, and several accepted bit depths (depending on the color
 * type).
 * 
 * @author Joe Desmond
 * @see <a href="http://www.libpng.org/pub/png/spec/1.2/PNG-Chunks.html#C.IHDR">PNG specification</a>
 */
public enum ColorFormat {
	
	/**
	 * 16-bit grayscale. If the samples can be represented in less than 16 bits,
	 * the converter will transform the samples to reduce the size of the PNG.
	 */
	GRAYSCALE(new GrayscaleConverter()),
	RGB_16,
	ARGB_16,
	RGBA_16,
	RGB_888,
	ARGB_8888,
	RGBA_8888,
	RGB_4444,
	ARGB_4444,
	RGBA_4444;
	
	/**
	 * Conversion function: converts pixels of this color space to a PNG color space
	 */
	private final ColorSpaceConverter converter;
	
	private ColorFormat(final ColorSpaceConverter _converter) {
		converter = _converter;
	}
	
	/**
	 * Converts the given image data to a PNG format. The data is not filtered or compressed after this step.
	 * 
	 * @param pixels pixel array (in this ColorFormat)
	 * @param width pixel width of the image
	 * @param height pixel height of the image
	 * @return PNG scanlines and color info
	 */
	public final PNGData convertToPNGFormat(final int[] pixels, final int width, final int height) {
		return converter.convert(pixels, width, height);
	}
}
