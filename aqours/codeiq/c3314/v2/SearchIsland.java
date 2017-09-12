package aqours.codeiq.c3314.v2;

public class SearchIsland {
	public static long get(int island, int wood) {
		assert 1 <= island;
		assert 0 <= wood;

		if(wood == 0) {
			return 1L;
		}
		if(island == 1) {
			return 0L;
		}
		return 0L; // TODO
	}
}
