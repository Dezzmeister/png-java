package com.dezzmeister.png.chunks;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.zip.Deflater;

import com.dezzmeister.png.chunks.meta.ColorType;
import com.dezzmeister.png.chunks.meta.PNGData;
import com.dezzmeister.png.data.Conversions;
import com.dezzmeister.png.filters.Filter;
import com.dezzmeister.png.functions.CRC;

/**
 * IDAT chunk encoding/decoding class.
 * 
 * @author Joe Desmond
 */
public class IDAT {
	
	/**
	 * Encodes an IDAT chunk and returns the entire chunk (includes 'IDAT' header, length, compressed
	 * data, and CRC).
	 * 
	 * @param imageData image data to encode
	 * @param filter filtering strategy
	 * @return encoded IDAT chunk
	 */
	public static byte[] encode(final PNGData imageData, final Filter filter) {
		final byte[][] scanlines = imageData.scanlines;
		final int width = scanlines[0].length;
		final int height = scanlines.length;
		
		// Allocate enough memory to hold the compressed scanlines
		final ByteBuffer filteredLines = ByteBuffer.allocate(((width + 1) * height));
		
		for (int line = 0; line < height; line++) {
			final byte[] prevLine = (line == 0) ? null : scanlines[line - 1];
			final byte[] thisLine = scanlines[line];
			
			final byte[] filtered = filter(prevLine, thisLine, imageData, filter);
			filteredLines.put(filtered);
		}
		
		final byte[] rawLines = filteredLines.array();
		final Deflater deflater = new Deflater();
		
		deflater.setInput(rawLines);
		deflater.finish();
		
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(ChunkType.IDAT.getByteName(), 0, 4);
		byte[] buffer = new byte[1024];
		int bytesRead = 0;
		
		while ((bytesRead = deflater.deflate(buffer)) != 0) {
			baos.write(buffer, 0, bytesRead);
		}
		
		final byte[] rawData = baos.toByteArray();
		final int length = rawData.length - 4;
		final int crc = (int) CRC.crc(rawData);
		
		final ByteBuffer out = ByteBuffer.allocate(rawData.length + 8);
		final byte[] lengthBytes = Conversions.fromInt(length);
		final byte[] crcBytes = Conversions.fromInt(crc);
		
		out.put(lengthBytes);
		out.put(rawData);
		out.put(crcBytes);
		
		return out.array();
	}
	
	/**
	 * Filters the given scanline. If a specific filter is specified, it will be used here. Otherwise,
	 * if the filter is dynamic, the best filter is chosen using the minimum sum of absolute differences heuristic.
	 * 
	 * @param prevLine previous scanline
	 * @param line current scanline
	 * @param imageData image data
	 * @param filter filter type
	 * @return filtered scanline
	 */
	private static byte[] filter(final byte[] prevLine, final byte[] line, final PNGData imageData, final Filter filter) {
		final int bytesPerPixel = imageData.colorType.getBytesPerPixel(imageData.bitDepth);
		
		if (filter != Filter.DYNAMIC) {
			final byte[] out = new byte[line.length + 1];
			out[0] = filter.typeCode;
			
			final byte[] filtered = filter.filterFunction().applyFilter(prevLine, line, bytesPerPixel);
			System.arraycopy(filtered, 0, out, 1, filtered.length);
			
			return filtered;
		}
		
		if (imageData.colorType == ColorType.GRAYSCALE && imageData.bitDepth < 8) {
			// Default to NONE for grayscale images with bit depth less than 8. To see the rationale
			// for this, visit http://www.libpng.org/pub/png/book/chapter09.html
			
			final byte[] out = new byte[line.length + 1];
			out[0] = Filter.NONE.typeCode;
			System.arraycopy(line, 0, out, 1, line.length);
			
			return out;
		}
		
		// We need to pick the best filter
		
		final byte[] rawData = new byte[line.length + 1]; // Plus 1 to hold filter type code
		
		final byte[] none = Filter.NONE.filterFunction().applyFilter(prevLine, line, bytesPerPixel);
		final byte[] sub = Filter.SUB.filterFunction().applyFilter(prevLine, line, bytesPerPixel);
		final byte[] up = Filter.UP.filterFunction().applyFilter(prevLine, line, bytesPerPixel);
		final byte[] average = Filter.AVERAGE.filterFunction().applyFilter(prevLine, line, bytesPerPixel);
		final byte[] paeth = Filter.PAETH.filterFunction().applyFilter(prevLine, line, bytesPerPixel);
		
		final FilterCandidate noneCand = new FilterCandidate(none, sum(none), Filter.NONE);
		final FilterCandidate subCand = new FilterCandidate(sub, sum(sub), Filter.SUB);
		final FilterCandidate upCand = new FilterCandidate(up, sum(up), Filter.UP);
		final FilterCandidate averageCand = new FilterCandidate(average, sum(average), Filter.AVERAGE);
		final FilterCandidate paethCand = new FilterCandidate(paeth, sum(paeth), Filter.PAETH);
		
		final FilterCandidate best = min(new FilterCandidate[] {noneCand, subCand, upCand, averageCand, paethCand});
		
		rawData[0] = best.filter.typeCode;
		System.arraycopy(best.filtered, 0, rawData, 1, best.filtered.length);
		
		return rawData;
	}
	
	private static class FilterCandidate {
		final byte[] filtered;
		final int sum;
		final Filter filter;
		
		FilterCandidate(final byte[] _filtered, final int _sum, final Filter _filter) {
			filtered = _filtered;
			sum = _sum;
			filter = _filter;
		}
	}
	
	private static FilterCandidate min(final FilterCandidate[] cands) {
		FilterCandidate min = cands[0];
		
		for (int i = 1; i < cands.length; i++) {
			if (cands[i].sum < min.sum) {
				min = cands[i];
			}
		}
		
		return min;
	}
	
	private static int sum(final byte[] items) {
		int sum = 0;
		
		for (int i = 0; i < items.length; i++) {
			sum += Math.abs(items[i]);
		}
		
		return sum;
	}
}
