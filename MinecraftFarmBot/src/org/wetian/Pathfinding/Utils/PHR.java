package org.wetian.Pathfinding.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class PHR {

	public static final String PLACEHOLDER = "{}";

	public static String r(String text, Object... values) {
		List<Integer> placeholderIndices = determinePlaceholderIndices(text);

		if (placeholderIndices.size() != values.length) {
			throw new IllegalArgumentException("given values: " + values.length + ", found placeholders: " + placeholderIndices.size());	
		}
			
		return replacePlaceholdersWithValues(text, placeholderIndices, values);
	}

	private static List<Integer> determinePlaceholderIndices(String text) {
		List<Integer> placeholderIndices = new ArrayList<Integer>();
		
		int searchFrom = 0;
		
		while(true) {
			int index = text.indexOf(PLACEHOLDER, searchFrom);
			
			if(index == -1) {
				break;
			}

			placeholderIndices.add(index);
			
			searchFrom = index + 1;
		}

		return placeholderIndices;
	}

	private static String replacePlaceholdersWithValues(String text, List<Integer> placeholderIndices, Object[] values) {
		String filledInString = text;

		for (int i = placeholderIndices.size() - 1; i >= 0; i--) {
			int placeholderStartIndex = placeholderIndices.get(i);

			String beforePlaceholder = filledInString.substring(0, placeholderStartIndex);
			String valueForPlaceholder = Objects.toString(values[i]);
			String afterPlaceholder = filledInString.substring(placeholderStartIndex + PLACEHOLDER.length());

			filledInString = beforePlaceholder+valueForPlaceholder+afterPlaceholder;
		}

		return filledInString;
	}
}
