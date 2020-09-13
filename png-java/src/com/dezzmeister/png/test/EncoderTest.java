package com.dezzmeister.png.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.dezzmeister.png.Encoder;
import com.dezzmeister.png.color.ColorFormat;

@SuppressWarnings("unused")
public class EncoderTest {
	
	public static final void main(final String[] args) throws IOException {
		test1("test/encoded/0.png");
	}
	
	private static final void test2(final String fileName) throws IOException {
		final int width = 400;
		final int height = 400;
		final int[] pixels = new int[width * height];
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = (int)(Math.random() * Integer.MAX_VALUE);
		}
		
		final Encoder encoder = new Encoder(pixels, width, height, ColorFormat.ARGB_8888);
		final FileOutputStream fos = new FileOutputStream(new File(fileName));
		encoder.encode(fos);
		fos.close();
	}
	
	private static final void test1(final String fileName) throws IOException {
		final int width = 400;
		final int height = 400;
		final int[] pixels = new int[width * height];
		
		for (int row = 0; row < height; row++) {
			for (int x = 0; x < width; x++) {
				final int i = (x + (row * width));
				final int alpha = 255;
				final int red = (int) ((row / (float) height) * 256);
				final int green = 127;
				final int blue = (int) ((x / (float) width) * 256);
				
				final int pixel = (alpha << 24) | (red << 16) | (green << 8) | blue;
				pixels[i] = pixel;
			}
		}
		
		final Encoder encoder = new Encoder(pixels, width, height, ColorFormat.ARGB_8888);
		final FileOutputStream fos = new FileOutputStream(new File(fileName));
		encoder.encode(fos);
		fos.close();
	}
}
