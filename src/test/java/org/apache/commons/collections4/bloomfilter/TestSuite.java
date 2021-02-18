package org.apache.commons.collections4.bloomfilter;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({ ArrayCountingBloomFilterTest.class, IndexFilterTest.class })
public class TestSuite {
	// nothing
}