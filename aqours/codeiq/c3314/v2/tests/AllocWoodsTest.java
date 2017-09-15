package aqours.codeiq.c3314.v2.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aqours.codeiq.c3314.v2.AllocWoods;
import charlotte.tools.ArrayTools;

public class AllocWoodsTest {
	public static void main(String[] args) {
		try {
			main2();
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
	}

	private static void main2() throws Exception {
		perform(new int[] { 1 }, 1, new int[][] {});
		perform(new int[] { 1 }, 2, new int[][] {});
		perform(new int[] { 1 }, 3, new int[][] {});
	}

	private static void perform(int[] islands, int wood, int[][] expect) {
		List<int[]> answer = new ArrayList<int[]>();

		AllocWoods.search(islands, wood, (ans) -> answer.add(ans));

		if(!ArrayTools.<int[]>isSame(
				answer,
				Arrays.asList(expect),
				(a, b) -> ArrayTools.<Integer>comp(
						ArrayTools.toList(a),
						ArrayTools.toList(b),
						(aa, bb) -> aa - bb
						)
				)) {
			throw null;
		}
	}
}
