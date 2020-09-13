package com.dezzmeister.png.chunks;

import java.nio.ByteBuffer;

import com.dezzmeister.png.chunks.meta.PNGData;
import com.dezzmeister.png.functions.CRC;

/**
 * IHDR chunk encoding/decoding class.
 * 
 * @author Joe Desmond
 * @see <a href=http://www.libpng.org/pub/png/spec/1.2/PNG-Chunks.html>PNG Spec - Chunks</a>
 */
public class IHDR {
	
	public static byte[] encode(final PNGData imageData) {
		// 4 byte chunk type + 13 byte chunk data
		final ByteBuffer data = ByteBuffer.allocate(4 + 13);
		data.put(ChunkType.IHDR.getByteName());
		data.putInt(imageData.width); // Width
		data.putInt(imageData.height); // Height
		data.put(imageData.bitDepth);
		data.put(imageData.colorType.getTypeCode());
		data.put((byte) 0); // Compression method (must be 0)
		data.put((byte) 0); // Filter method (must be 0)
		data.put((byte) 0); // Interlace method (interlacing not supported)
		final int crc = (int) CRC.crc(data.array());
		
		final ByteBuffer out = ByteBuffer.allocate(4 + 4 + 13 + 4);
		out.putInt(13);
		out.put(data.array());
		out.putInt(crc);
		
		return out.array();
	}
}
