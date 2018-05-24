package aqours.codeiq.c3418;

import charlotte.tools.ReflecTools;
import charlotte.tools.SecurityTools;
import charlotte.tools.StringTools;

public class Tester {
	public static void main(String[] args) {
		try {
			main2();

			System.out.println("OK!");
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	private static void main2() throws Exception {
		final int[] SCALES = new int[] { 10, 100, 1000, 10000, 100000, 1000000 };

		for(int c = 0; c < 100; c++) {
			for(int j = 0; j < SCALES.length; j++) {
				for(int i = 0; i <= j; i++) {
					test01(SCALES[i], SCALES[j]);
				}
			}
		}
	}

	private static void test01(int lScale, int hScale) throws Exception {
		long[] vals = mkVals(lScale, hScale);

		long l = vals[0];
		long h = vals[1];

		System.out.println("<" + l + "," + h);

		String ans = (String)ReflecTools.invokeDeclaredMethod(
				Main.class,
				"search",
				new Main(),
				new Object[] { l, h }
				);
		String ansTest = searchTest(l, h);

		System.out.println(">" + ans);
		System.out.println(">" + ansTest);

		if(ans.equals(ansTest) == false) throw null;
	}

	private static long[] mkVals(int lScale, int hScale) {
		long l;
		long h;

		do {
			l = SecurityTools.cRandom(lScale) + 1;
			h = SecurityTools.cRandom(hScale) + 1;
		}
		while(l == h);

		return new long[] {
				Math.min(l, h),
				Math.max(l, h),
				};
	}

	private static String searchTest(long l, long h) {
		while(isPalindrome(l) == false) {
			l++;
		}
		while(isPalindrome(h) == false) {
			h--;
		}
		if(h < l) {
			return "-";
		}
		while(l < h) {
			while(isPalindrome(++l) == false);
			while(isPalindrome(--h) == false);
		}
		if(l == h) {
			return "" + l;
		}
		return h + "," + l;
	}

	private static boolean isPalindrome(long value) {
		String sVal = "" + value;
		return sVal.equals(StringTools.reverse(sVal));
	}
}
