package aqours.codeiq.c3314.v2_1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import charlotte.tools.ArrayTools;
import charlotte.tools.IntTools;
import charlotte.tools.RunnableEx;

public class MaskIslands {
	public int[][] get(int div, int islandsTotalMax) throws Exception {
		//if(div < 1) throw null; // assert
		//if(islandsTotalMax < div) throw null; // assert
		//if(found == null) throw null; // assert

		_div = div;
		_dest = new ArrayList<int[]>();
		_islands = new int[div];

		search(0, islandsTotalMax);

		return _dest.toArray(new int[_dest.size()][]);
	}

	private int _div;
	private List<int[]> _dest;
	private int[] _islands;

	public void search(int index, int spaceRem) throws Exception {
		if(index < _div) {
			//if(spaceRem < 1) throw null; // assert

			final int ISLAND_MAX = spaceRem - (_div - index - 1);

			//if(ISLAND_MAX < 1) throw null; // assert

			for(_islands[index] = 1; _islands[index] <= ISLAND_MAX; _islands[index]++) {
				search(index + 1, spaceRem - _islands[index]);
			}
		}
		else {
			//if(spaceRem < 0) throw null; // assert

			_dest.add(
					Arrays.copyOf(_islands, _islands.length)
					);
		}
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
			new int[] { 1 },
			new int[] { 2 },
			});
		test01(1, 3, new int[][] {
			new int[] { 1 },
			new int[] { 2 },
			new int[] { 3 },
			});

		test01(2, 2, new int[][] {
			new int[] { 1, 1 },
			});
		test01(2, 3, new int[][] {
			new int[] { 1, 1 },

			new int[] { 1, 2 },
			new int[] { 2, 1 },
			});
		test01(2, 4, new int[][] {
			new int[] { 1, 1 },

			new int[] { 1, 2 },
			new int[] { 2, 1 },

			new int[] { 1, 3 },
			new int[] { 2, 2 },
			new int[] { 3, 1 },
			});

		test01(3, 3, new int[][] {
			new int[] { 1, 1, 1 },
			});
		test01(3, 4, new int[][] {
			new int[] { 1, 1, 1 },

			new int[] { 1, 1, 2 },
			new int[] { 1, 2, 1 },
			new int[] { 2, 1, 1 },
			});
		test01(3, 5, new int[][] {
			// 3
			new int[] { 1, 1, 1 },

			// 4
			new int[] { 1, 1, 2 },
			new int[] { 1, 2, 1 },
			new int[] { 2, 1, 1 },

			// 5
			new int[] { 1, 1, 3 },
			new int[] { 1, 3, 1 },
			new int[] { 3, 1, 1 },

			new int[] { 1, 2, 2 },
			new int[] { 2, 1, 2 },
			new int[] { 2, 2, 1 },
			});
		test01(3, 6, new int[][] {
			// 3
			new int[] { 1, 1, 1 },

			// 4
			new int[] { 1, 1, 2 },
			new int[] { 1, 2, 1 },
			new int[] { 2, 1, 1 },

			// 5
			new int[] { 1, 1, 3 },
			new int[] { 1, 3, 1 },
			new int[] { 3, 1, 1 },

			new int[] { 1, 2, 2 },
			new int[] { 2, 1, 2 },
			new int[] { 2, 2, 1 },

			// 6
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

	private static void test01(int div, int islandsTotalMax, int[][] expect) {
		try {
			int[][] answer = new MaskIslands().get(div, islandsTotalMax);

			test01_sort(expect);
			test01_sort(answer);

			if(!ArrayTools.<int[]>isSame(
					ArrayTools.toList(expect),
					ArrayTools.toList(answer),
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

	private static void test01_sort(int[][] arr) {
		ArrayTools.sort(arr, (a, b) -> ArrayTools.<Integer>comp(
				ArrayTools.toList(a),
				ArrayTools.toList(b), IntTools.comp));
	}
}
