package com.dezzmeister.png.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.dezzmeister.png.junit.filters.AverageFilterTest;
import com.dezzmeister.png.junit.filters.PaethFilterTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	AverageFilterTest.class,
	PaethFilterTest.class
})
public class FilterTestSuite {

}
