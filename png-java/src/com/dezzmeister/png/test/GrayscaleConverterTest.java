package com.dezzmeister.png.test;

public class GrayscaleConverterTest {
	
	public static final void main(final String[] args) {
		final int[] pixels = {0, 65535, 0, 0, 0, 65535, 21845, 43690, 4369};
		
		System.out.println(determineOptimalBitDepth(pixels));
	}
	
	private static int determineOptimalBitDepth(final int[] pixels) {
		final byte[] allowedDepths = {1, 2, 8, 4, 16};
		final int[] alignments = {65535, 21845, 4369, 256, 1};
		int bestDepthIndex = 0;
		
		for (int pixel : pixels) {
			while ((pixel % alignments[bestDepthIndex] != 0) && (bestDepthIndex != 4)) {
				bestDepthIndex++;
			}
			
			if (bestDepthIndex == 4) {
				return 16;
			}
		}
		
		return allowedDepths[bestDepthIndex];
	}
}
