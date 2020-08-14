package com.dezzmeister.png.chunks;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * List of supported chunk types. {@link #getByteName()} can be used to get the four byte
 * chunk type.
 * 
 * @author Joe Desmond
 */
public enum ChunkType {
	IHDR,
	IDAT,
	IEND;
	
	/**
	 * 4-byte chunk type
	 */
	private byte[] byteName;
	
	private ChunkType() {
		byteName = name().getBytes(StandardCharsets.US_ASCII);
	}
	
	/**
	 * Returns the 4-byte chunk type. This can be written to a PNG file.
	 * 
	 * @return 4-byte chunk type
	 */
	public byte[] getByteName() {
		return byteName;
	}
	
	/**
	 * Returns the chunk type with the given name. The name is given as it would
	 * appear in a PNG file (as a string of bytes). Throws an exception if the given chunk type
	 * is not supported or the byte array is not the right length (4 bytes).
	 * {@link #isSupportedChunkType(byte[])} can be used to ensure the chunk type is supported.
	 * 
	 * @param bytes chunk type (ex. {@link String#getBytes(java.nio.charset.Charset) "IHDR".getBytes(StandardCharsets.US_ASCII)})
	 * @return chunk type (as enum)
	 */
	public static ChunkType getChunkType(final byte[] bytes) {
		if (bytes.length != 4) {
			throw new IllegalArgumentException("Chunk type must be four bytes long");
		}
		
		for (final ChunkType type : ChunkType.values()) {
			if (Arrays.equals(bytes, type.getByteName())) {
				return type;
			}
		}
		
		final String chunkName = new String(bytes, StandardCharsets.US_ASCII);
		
		throw new IllegalArgumentException("Unsupported chunk type: " + chunkName);
	}
	
	/**
	 * Returns true if the given chunk type is supported. The chunk type is given
	 * as it would appear in a PNG file (as a string of bytes).
	 * 
	 * @param bytes chunk type (ex. {@link String#getBytes(java.nio.charset.Charset) "IHDR".getBytes(StandardCharsets.US_ASCII)})
	 * @return true if the given chunk type is supported
	 */
	public static boolean isSupportedChunkType(final byte[] bytes) {
		for (final ChunkType type : values()) {
			if (Arrays.equals(bytes, type.getByteName())) {
				return true;
			}
		}
		
		return false;
	}
}
