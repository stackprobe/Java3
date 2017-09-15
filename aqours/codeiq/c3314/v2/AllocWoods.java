package aqours.codeiq.c3314.v2;

import charlotte.tools.ValueSetter;

public class AllocWoods {
	public static void search(int[] islands, int wood, ValueSetter<int[]> found) {
		if(islands.length < 1) throw null; // assert
		if(wood < 1) throw null; // assert
		for(int island : islands) {
			if(island < 1) throw null; // assert
		}

		//found.set(null); // TODO
	}
}
