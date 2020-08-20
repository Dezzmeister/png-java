package com.dezzmeister.png.color;

import com.dezzmeister.png.chunks.meta.PNGData;
import com.dezzmeister.png.color.converters.GrayscaleConverter;
import com.dezzmeister.png.color.converters.RGBConverter16;

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
	 * 16-bit grayscale. Each input int is a single 16 bit sample (the most significant
	 * 16 bits are wasted). If the samples can be represented in less than 16 bits,
	 * the converter will transform the samples to reduce the size of the PNG.
	 */
	GRAYSCALE(new GrayscaleConverter()),
	
	/**
	 * 16-bit RGB. Each input int is a single 16 bit sample (the most significant
	 * 16 bits are wasted). The converter does not try to optimize the bit depth or
	 * use a palette instead. If the user specifies 16 bits of precision, the PNG encoder
	 * will respect that.
	 * 
	 * TODO: Optimize the converter to use a palette if possible
	 */
	RGB_16(new RGBConverter16()),
	ARGB_16,
	RGBA_16,
	
	/**
	 * 8-bit RGB. Each input int contains all three samples (the most significant
	 * 16 bits are wasted).
	 * 
	 * TODO: Optimize the converter to use a palette if possible
	 */
	RGB_888(),
	ARGB_8888,
	RGBA_8888,
	
	/**
	 * 4-bit RGB. Each input int contains only one pixel (three samples per pixel;
	 * the most significant 20 bits are wasted).
	 * 
	 * TODO: Optimize the converter to use a palette if possible
	 */
	RGB_444(),
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
