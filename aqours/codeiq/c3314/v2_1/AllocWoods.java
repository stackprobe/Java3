package aqours.codeiq.c3314.v2_1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import charlotte.tools.ArrayTools;
import charlotte.tools.IntTools;
import charlotte.tools.RunnableEx;

public class AllocWoods {
	private List<int[]> _dest;
	private int _div;
	private int[] _grounds;
	private int[] _woodMaxs;
	private int[] _afterTotals; // [i] == total of _woodMaxs[(i + 1) ... ]
	private int[] _woods;

	public int[][] get(int[] islands, int wood) throws Exception {
		//if(islands == null) throw null; // assert
		//if(islands.length < 1) throw null; // assert
		for(int island : islands) {
			//if(island < 1) throw null; // assert
		}
		//if(wood < 0) throw null; // assert

		_dest = new ArrayList<int[]>();
		_div = islands.length;
		_grounds = new int[_div];
		_woodMaxs = new int[_div];
		_afterTotals = new int[_div];
		_woods = new int[_div];

		for(int index = 0; index < _div; index++) {
			_grounds[index] = islands[index] - 1;
			_woodMaxs[index] = getPylamidSize(_grounds[index]);
		}

		{
			int total = 0;

			for(int index = _div - 1; 0 <= index; index--) {
				_afterTotals[index] = total;
				total += _woodMaxs[index];
			}

			if(total < wood) { // ? 木が多すぎて分配不可能
				return getRet();
			}
		}

		search(0, wood);

		return getRet();
	}

	private int[][] getRet() {
		return _dest.toArray(new int[_dest.size()][]);
	}

	/**
	 *
	 * @param ground
	 * @return ground + (ground - 1) + (ground - 2) + ... + 1
	 */
	private static int getPylamidSize(int ground) {
		return (ground * (ground + 1)) / 2;
	}

	private void search(int index, int woodRem) throws Exception {
		if(index + 1 < _div) {
			final int WOOD_MIN = Math.max(0, woodRem - _afterTotals[index]);
			final int WOOD_MAX = Math.min(woodRem, _woodMaxs[index]);

			for(_woods[index] = WOOD_MIN; _woods[index] <= WOOD_MAX; _woods[index]++) {
				search(index + 1, woodRem - _woods[index]);
			}
		}
		else {
			if(woodRem < 0) throw null; // assert
			if(_woodMaxs[index] < woodRem) throw null; // assert

			_woods[index] = woodRem;
			_dest.add(
					Arrays.copyOf(_woods, _woods.length)
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
		test01(new int[] { 1 }, 0, new int[][] {
			new int[] { 0 },
			});
		test01(new int[] { 1, 2 }, 0, new int[][] {
			new int[] { 0, 0 },
			});
		test01(new int[] { 1, 2, 3 }, 0, new int[][] {
			new int[] { 0, 0, 0 },
			});

		// 配置不可能series
		test01(new int[] { 1 }, 1, new int[][] { });
		test01(new int[] { 1 }, 2, new int[][] { });
		test01(new int[] { 2 }, 2, new int[][] { });

		test01(new int[] { 2 }, 1, new int[][] {
			new int[] { 1 },
			});
		test01(new int[] { 3 }, 1, new int[][] {
			new int[] { 1 },
			});
		test01(new int[] { 3 }, 2, new int[][] {
			new int[] { 2 },
			});
		test01(new int[] { 3 }, 3, new int[][] {
			new int[] { 3 },
			});
		test01(new int[] { 3 }, 4, new int[][] {
			// 配置不可能
			});
		test01(new int[] { 4 }, 1, new int[][] {
			new int[] { 1 },
			});
		test01(new int[] { 4 }, 6, new int[][] {
			new int[] { 6 },
			});
		test01(new int[] { 4 }, 7, new int[][] {
			// 配置不可能
			});

		test01(new int[] { 2, 2 }, 2, new int[][] {
			new int[] { 1, 1 },
			});
		test01(new int[] { 2, 2 }, 1, new int[][] {
			new int[] { 0, 1 },
			new int[] { 1, 0 },
			});

		test01(new int[] { 2, 1, 2 }, 2, new int[][] {
			new int[] { 1, 0, 1 },
			});
		test01(new int[] { 2, 1, 2 }, 1, new int[][] {
			new int[] { 0, 0, 1 },
			new int[] { 1, 0, 0 },
			});

		test01(new int[] { 3, 3 }, 2, new int[][] {
			new int[] { 0, 2 },
			new int[] { 2, 0 },
			new int[] { 1, 1 },
			});
		test01(new int[] { 3, 3 }, 3, new int[][] {
			new int[] { 0, 3 },
			new int[] { 3, 0 },
			new int[] { 2, 1 },
			new int[] { 1, 2 },
			});
		test01(new int[] { 3, 3 }, 6, new int[][] {
			new int[] { 3, 3 },
			});
		test01(new int[] { 3, 3 }, 7, new int[][] {
			// 配置不可能
			});

		test01(new int[] { 4, 4 }, 12, new int[][] {
			new int[] { 6, 6 },
			});
		test01(new int[] { 4, 4 }, 13, new int[][] {
			// 配置不可能
			});
		test01(new int[] { 4, 4 }, 10, new int[][] {
			new int[] { 4, 6 },
			new int[] { 6, 4 },
			new int[] { 5, 5 },
			});
		test01(new int[] { 4, 4 }, 8, new int[][] {
			new int[] { 2, 6 },
			new int[] { 6, 2 },
			new int[] { 3, 5 },
			new int[] { 5, 3 },
			new int[] { 4, 4 },
			});

		test01(new int[] { 3, 3, 3 }, 9, new int[][] {
			new int[] { 3, 3, 3 },
			});
		test01(new int[] { 3, 3, 3 }, 10, new int[][] {
			// 配置不可能
			});
		test01(new int[] { 3, 3, 3 }, 6, new int[][] {
			new int[] { 0, 3, 3 },
			new int[] { 3, 0, 3 },
			new int[] { 3, 3, 0 },

			new int[] { 1, 2, 3 },
			new int[] { 1, 3, 2 },
			new int[] { 2, 1, 3 },
			new int[] { 3, 1, 2 },
			new int[] { 2, 3, 1 },
			new int[] { 3, 2, 1 },

			new int[] { 2, 2, 2 },
			});
		test01(new int[] { 3, 3, 3 }, 3, new int[][] {
			new int[] { 0, 0, 3 },
			new int[] { 0, 3, 0 },
			new int[] { 3, 0, 0 },

			new int[] { 2, 0, 1 },
			new int[] { 2, 1, 0 },
			new int[] { 0, 2, 1 },
			new int[] { 1, 2, 0 },
			new int[] { 0, 1, 2 },
			new int[] { 1, 0, 2 },

			new int[] { 1, 1, 1 },
			});
	}

	private static void test01(int[] islands, int wood, int[][] expect) {
		try {
			int[][] answer = new AllocWoods().get(islands, wood);

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
