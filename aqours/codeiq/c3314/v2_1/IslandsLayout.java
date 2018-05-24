package aqours.codeiq.c3314.v2_1;

import charlotte.tools.RunnableEx;

public class IslandsLayout {
	public long get(int ground, int[] islands) {
		int islandsTotal = 0;

		//if(ground < 1) throw null; // assert
		//if(islands.length < 1) throw null; // assert
		for(int island : islands) {
			//if(island < 1) throw null; // assert
			islandsTotal += island;
		}

		int between = islands.length - 1;
		int gap = islands.length + 1;

		int space = ground - islandsTotal - between;

		//if(space < 0) throw null; // assert

		return search(gap, space);
	}

	private long search(int gap, int spaceRem) {
		if(1 < gap) {
			long ans = 0L;

			for(int space = 0; space <= spaceRem; space++) {
				ans += search(gap - 1, spaceRem - space);
			}
			return ans;
		}
		else {
			return 1L;
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
		// error series
		//test01(1, new int[] { 2 }, 1);
		//test01(3, new int[] { 1, 2 }, 1);
		//test01(5, new int[] { 2, 3 }, 1);
		//test01(10, new int[] { 3, 3, 3 }, 1);

		test01(1, new int[] { 1 }, 1);
		test01(2, new int[] { 1 }, 2);
		test01(3, new int[] { 1 }, 3);
		test01(3, new int[] { 2 }, 2);
		test01(3, new int[] { 3 }, 1);
		test01(3, new int[] { 1, 1 }, 1);
		test01(4, new int[] { 1, 1 }, 3);
		test01(4, new int[] { 1, 2 }, 1);
		test01(4, new int[] { 2, 1 }, 1);
		test01(7, new int[] { 1, 2, 2 }, 1);
		test01(7, new int[] { 2, 1, 2 }, 1);
		test01(7, new int[] { 2, 2, 1 }, 1);
		test01(8, new int[] { 1, 2, 2 }, 4);
		test01(8, new int[] { 2, 1, 2 }, 4);
		test01(8, new int[] { 2, 2, 1 }, 4);

		test01(15, new int[] { 4, 4, 5 }, 1);
		test01(16, new int[] { 4, 4, 5 }, 4);
		test01(17, new int[] { 4, 4, 5 }, 10);

		test01(4, new int[] { 3 }, 2);
	}

	private static void test01(int ground, int[] islands, long expect) {
		try {
			long answer = new IslandsLayout().get(ground, islands);

			if(expect != answer) {
				throw null;
			}
		}
		catch(Throwable e) {
			throw RunnableEx.re(e);
		}
	}
}
