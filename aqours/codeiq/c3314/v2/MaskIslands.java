package aqours.codeiq.c3314.v2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import charlotte.tools.ArrayTools;
import charlotte.tools.IntTools;
import charlotte.tools.RunnableEx;

public class MaskIslands {
	public interface Found {
		public void action(int[] islands) throws Exception;
	}

	public void search(int div, int islandsTotalMax, Found found) throws Exception {
		if(div < 1) throw null;
		if(islandsTotalMax < div) throw null;
		if(found == null) throw null;

		_div = div;
		_found = found;
		_islands = new int[div];

		search(0, islandsTotalMax);

		_div = -1;
		_found = null;
		_islands = null;
	}

	private int _div;
	private Found _found;
	private int[] _islands;

	public void search(int index, int spaceRem) throws Exception {

		// bug is here !!!!
		// bug is here !!!!
		// bug is here !!!!

		if(spaceRem < 1) throw null; // test

		if(index + 1 < _div) {
			final int ISLAND_MAX = spaceRem - (_div - index - 1);

			if(ISLAND_MAX < 1) throw null; // test

			for(_islands[index] = 1; _islands[index] <= ISLAND_MAX; _islands[index]++) {
				search(index + 1, spaceRem - _islands[index]);
			}
		}
		else {
			_islands[index] = spaceRem;
			_found.action(_islands);
		}

		// bug is here !!!!
		// bug is here !!!!
		// bug is here !!!!
	}

	// ---- test

	public static void main(String[] args) {
		try {
			test01();

			System.out.println("OK!");
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	private static void test01() {
		test01(1, 1, new int[][] {
			new int[] { 1 },
			});
		test01(1, 2, new int[][] {
			new int[] { 2 },
			});
		test01(1, 3, new int[][] {
			new int[] { 3 },
			});

		test01(2, 2, new int[][] {
			new int[] { 1, 1 },
			});
		test01(2, 3, new int[][] {
			new int[] { 1, 2 },
			new int[] { 2, 1 },
			});
		test01(2, 4, new int[][] {
			new int[] { 1, 3 },
			new int[] { 2, 2 },
			new int[] { 3, 1 },
			});

		test01(3, 3, new int[][] {
			new int[] { 1, 1, 1 },
			});
		test01(3, 4, new int[][] {
			new int[] { 1, 1, 2 },
			new int[] { 1, 2, 1 },
			new int[] { 2, 1, 1 },
			});
		test01(3, 5, new int[][] {
			new int[] { 1, 1, 3 },
			new int[] { 1, 3, 1 },
			new int[] { 3, 1, 1 },

			new int[] { 1, 2, 2 },
			new int[] { 2, 1, 2 },
			new int[] { 2, 2, 1 },
			});
		test01(3, 6, new int[][] {
			new int[] { 1, 1, 4 },
			new int[] { 1, 4, 1 },
			new int[] { 4, 1, 1 },

			new int[] { 1, 2, 3 },
			new int[] { 1, 3, 2 },
			new int[] { 2, 1, 3 },
			new int[] { 3, 1, 2 },
			new int[] { 2, 3, 1 },
			new int[] { 3, 2, 1 },

			new int[] { 2, 2, 2 },
			});
	}

	private static void test01(int div, int islandsTotalMax, int[][] prmExpect) {
		try {
			List<int[]> expect = Arrays.asList(prmExpect);
			List<int[]> answer = new ArrayList<int[]>();

			new MaskIslands().search(div, islandsTotalMax,
					(islands) -> answer.add(Arrays.copyOf(islands, islands.length))
					);

			test01_sort(expect);
			test01_sort(answer);

			if(!ArrayTools.<int[]>isSame(
					expect,
					answer,
					(a, b) -> ArrayTools.<Integer>comp(
							ArrayTools.toList(a),
							ArrayTools.toList(b),
							(aa, bb) -> aa - bb
							)
					)) {
				throw null;
			}
		}
		catch(Throwable e) {
			throw RunnableEx.re(e);
		}
	}

	private static void test01_sort(List<int[]> list) {
		ArrayTools.sort(list, (a, b) -> ArrayTools.<Integer>comp(
				ArrayTools.toList(a),
				ArrayTools.toList(b), IntTools.comp));
	}
}
