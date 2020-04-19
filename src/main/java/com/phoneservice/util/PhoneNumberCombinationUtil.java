package com.phoneservice.util;

import java.util.ArrayList;
import java.util.List;

public class PhoneNumberCombinationUtil {
	private PhoneNumberCombinationUtil() {
		super();
	}
	static String[] phoneNumberMapping = { "0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };
	public static List<String> fetchPhoneNumberCombinations(String phoneNumber) {
		List<String> combinationsList = new ArrayList<>();
		recursiveCombinations(phoneNumber, combinationsList, 0, "");
		return combinationsList;
	}

	private static void recursiveCombinations(
			String phoneNumber, List<String> combinationsList, int position, String current) {
		if(position == phoneNumber.length()) {
			combinationsList.add(current);
			return;
		}
		String characters = phoneNumberMapping[Integer.parseInt(String.valueOf(phoneNumber.charAt(position)))];
		for (int i = 0; i < characters.length(); i++) {
			recursiveCombinations(phoneNumber, combinationsList,position + 1, current + characters.charAt(i)) ;
		}
	}
}
