package aqours.codeiq.c3314.v2;

public class SearchIsland {
	public static long get(int island, int wood) {
		if(island < 1) throw null; // assert
		if(wood < 0) throw null; // assert

		if(wood == 0) {
			return 1L;
		}
		if(island == 1) {
			return 0L;
		}
		return 0L; // TODO
	}
}
