package com.phoneservice.util;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

import com.phoneservice.util.PhoneNumberCombinationUtil;

public class PhoneNumberCombinationUtilTest {

	@Test
	void testFetchPhoneNumberCombinations() {
		List<String> result = PhoneNumberCombinationUtil.fetchPhoneNumberCombinations("7733838074");
		assertEquals(46656, result.size());	
	}
	@Test
	void testFetchPhoneNumberCombinations1() {
		List<String> result = PhoneNumberCombinationUtil.fetchPhoneNumberCombinations("7733838074");
		assertEquals("ppddtdt0pg", result.get(0));	
	}
}
