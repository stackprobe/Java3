package aqours.codeiq.c3314.v2;

public class AllocWoods {
	public interface Found {
		public void action(int[] woods);
	}

	public void search(int[] islands, int wood, Found found) {
		assert 1 <= islands.length;
		assert 1 <= wood;
		for(int island : islands) {
			assert 1 <= island;
		}

		found.action(null); // TODO
	}
}
