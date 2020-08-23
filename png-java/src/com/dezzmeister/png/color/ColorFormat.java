package com.dezzmeister.png.color;

import com.dezzmeister.png.chunks.meta.PNGData;
import com.dezzmeister.png.color.converters.ARGBConverter16;
import com.dezzmeister.png.color.converters.ARGBConverter8;
import com.dezzmeister.png.color.converters.GrayscaleConverter;
import com.dezzmeister.png.color.converters.RGBConverter16;
import com.dezzmeister.png.color.converters.RGBConverter8;

/**
 * Various color formats. Each format has a conversion function to convert pixels of the format
 * to a standard PNG format. There are five PNG color types, and several accepted bit depths (depending on the color
 * type).
 * 
 * TODO: Optimize the converter to use a palette if possible
 * 
 * @author Joe Desmond
 * @see <a href="http://www.libpng.org/pub/png/spec/1.2/PNG-Chunks.html#C.IHDR">PNG specification</a>
 */
public enum ColorFormat {
	
	/**
	 * 16-bit grayscale. Each input int is a single 16 bit sample (the most significant
	 * 16 bits are wasted). If the samples can be represented in less than 16 bits,
	 * the converter will transform the samples to reduce the size of the PNG.
	 * 
	 * @see GrayscaleConverter
	 */
	GRAYSCALE(new GrayscaleConverter()),
	
	/**
	 * 16-bit RGB. Each input int is a single 16 bit sample (the most significant
	 * 16 bits are wasted). The converter does not try to optimize the bit depth or
	 * use a palette instead.
	 */
	RGB_16(new RGBConverter16()),
	
	/**
	 * 16-bit ARGB. Each input int is a single 16 bit sample (the most significant
	 * 16 bits are wasted). The converter does not try to optimize the bit depth or
	 * use a palette instead.
	 */
	ARGB_16(new ARGBConverter16(true)),
	
	/**
	 * 16-bit RGBA. Just like {@link #ARGB_16}, except the alpha sample comes after the RGB samples.
	 */
	RGBA_16(new ARGBConverter16(false)),
	
	/**
	 * 8-bit RGB. Each input int contains all three samples (the most significant
	 * 8 bits are wasted).
	 */
	RGB_888(new RGBConverter8()),
	
	/**
	 * 8-bit ARGB. Each input int contains 4 8-bit samples, starting with alpha
	 * (most significant byte) and ending with blue (least significant byte).
	 */
	ARGB_8888(new ARGBConverter8(true)),
	
	/**
	 * 8-bit RGBA. Just like {@link #ARGB_8888}, except the alpha sample is last.
	 * PNG scanlines with alpha samples are created with the alpha sample last, so this
	 * format may provide faster encoding than others.
	 */
	RGBA_8888(new ARGBConverter8(false));
	
	/**
	 * 4-bit RGB. Each input int contains only one pixel (three samples per pixel;
	 * the most significant 20 bits are wasted).
	 * 
	 * TODO: Complete 4-bit RGB, ARGB, and RGBA
	 */
	// RGB_444,
	// ARGB_4444,
	// RGBA_4444;
	
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
