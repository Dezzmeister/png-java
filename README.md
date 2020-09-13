# png-java
Simple PNG Encoder in Java

**STILL IN DEVELOPMENT**


This library is written in pure Java from scratch, with no dependencies from java.awt (or any other package not available on Android). It's very small (49 KB), and it
lacks many features of a full PNG encoder (can't encode ancillary chunks, PLTE chunks, tRNS chunks - only IHDR, IDAT, and IEND). It can encode images in several pixel
formats: grayscale, RGB 16, ARGB 16, RGBA 16, RGB 8, ARGB 8, and RGBA 8.


### Example Usage

First, create an `Encoder` object:

`final Encoder encoder = new Encoder(pixels, width, height, ColorFormat.ARGB8888)`

The PNG data will be encoded when this object is constructed. You can use one of two methods to obtain the encoded PNG data:

`public byte[] encode()`

`public void encode(final OutputStream os)`

Currently, the encoder uses dynamic filtering to pick the best filter per line. In the future, the user will have the option to select a filtering
strategy.
