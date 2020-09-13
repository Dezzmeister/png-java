package com.dezzmeister.png;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import com.dezzmeister.png.chunks.IDAT;
import com.dezzmeister.png.chunks.IEND;
import com.dezzmeister.png.chunks.IHDR;
import com.dezzmeister.png.chunks.meta.PNGData;
import com.dezzmeister.png.color.ColorFormat;
import com.dezzmeister.png.filters.Filter;

/**
 * Encodes PNG files. Can either return raw PNG bytes, or write them to an {@link OutputStream}.
 * 
 * @author Joe Desmond
 */
public class Encoder {
	
	/**
     * Standard PNG header. See <a href=http://www.libpng.org/pub/png/spec/1.2/PNG-Rationale.html#R.PNG-file-signature>PNG Spec</a>
     */
    private static final byte[] PNG_HEADER = {(byte)137, 80, 78, 71, 13, 10, 26, 10};
	
	/**
	 * PNG file data
	 */
	private final PNGData pngData;
	
	private final byte[] ihdrChunk;
	
	private final byte[] idatChunk;
	
	private final byte[] iendChunk;
	
	/**
	 * Creates an encoder for this image. Encodes the PNG on construction.
	 * 
	 * @param pixels pixels (in color format <code>colorFormat</code>)
	 * @param width pixel width
	 * @param height pixel height
	 * @param colorFormat color format of <code>pixels</code>
	 */
	public Encoder(final int[] pixels, final int width, final int height, final ColorFormat colorFormat) {
		pngData = colorFormat.convertToPNGFormat(pixels, width, height);

		ihdrChunk = IHDR.encode(pngData);
		idatChunk = IDAT.encode(pngData, Filter.DYNAMIC);
		iendChunk = IEND.encode();
	}
	
	public byte[] encode() {
		final ByteBuffer out = ByteBuffer.allocate(PNG_HEADER.length + ihdrChunk.length + idatChunk.length + iendChunk.length);
		
		out.put(PNG_HEADER);
		out.put(ihdrChunk);
		out.put(idatChunk);
		out.put(iendChunk);
		
		return out.array();
	}
	
	public void encode(final OutputStream os) throws IOException {
		os.write(PNG_HEADER);
		os.write(ihdrChunk);
		os.write(idatChunk);
		os.write(iendChunk);
	}
}
