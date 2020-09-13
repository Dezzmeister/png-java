package com.dezzmeister.png.chunks;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import com.dezzmeister.png.functions.CRC;

public class IEND {
	
	public static byte[] encode() {
		final ByteBuffer out = ByteBuffer.allocate(12);
		final byte[] chunkType = "IEND".getBytes(StandardCharsets.US_ASCII);
		
		out.putInt(0);
		out.put(chunkType);
		out.putInt((int) CRC.crc(chunkType));
		
		return out.array();
	}
}
