package aqours.codeiq.c3314.v2;

public class MakeIslands {
	public interface Found {
		public void action(int[] islands);
	}

	public static boolean search(int island, int div, Found found) {
		assert 1 <= island;
		assert 1 <= div;

		found.action(null); // TODO

		// divが大きすぎるとき、falseを返す。
		return false; // TODO
	}
}
