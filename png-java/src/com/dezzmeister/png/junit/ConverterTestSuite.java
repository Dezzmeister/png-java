package com.dezzmeister.png.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.dezzmeister.png.junit.converters.ARGBConverter16Test;
import com.dezzmeister.png.junit.converters.GrayscaleConverterTest;
import com.dezzmeister.png.junit.converters.RGBConverter16Test;
import com.dezzmeister.png.junit.converters.RGBConverter8Test;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	GrayscaleConverterTest.class,
	RGBConverter16Test.class,
	ARGBConverter16Test.class,
	RGBConverter8Test.class
})
public class ConverterTestSuite {

}
