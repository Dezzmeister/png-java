package com.dezzmeister.png.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.dezzmeister.png.junit.converters.GrayscaleConverterTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	GrayscaleConverterTest.class
})
public class ConverterTestSuite {

}
