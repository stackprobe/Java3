package aqours.codeiq.c3314;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
	public static void main(String[] args) {
		try {
			new Main().main2();
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	private void main2() {
		try(Scanner sc = new Scanner(System.in)) {
			String line = sc.nextLine();
			int n = Integer.parseInt(line);

			int ans = search(n);

			System.out.println("" + ans);
		}
	}

	private int search(int n) {
		int ans = 0;

		for(int w = 1; w <= n; w++) {
			ans += searchJoined(w, n - w);
		}
		return ans;
	}

	private Map<String, Integer> _joinedCache = new TreeMap<String, Integer>(new Comparator<String>() {
		@Override
		public int compare(String a, String b) {
			return a.compareTo(b);
		}
	});

	private int searchJoined(int w, int r) {
		if(r <= 0) {
			return 1;
		}
		if(w <= 1) {
			return 0;
		}
		String k = w + ":" + r;

		Integer v = _joinedCache.get(k);

		//System.out.print("J:" + (v == null ? "---" : "HIT") + ":"); // test
		if(v == null) {
			v = searchJoinedNonCache(w, r);
			_joinedCache.put(k, v);
		}
		//System.out.println(k + "=" + v); // test
		return v.intValue();
	}

	private int searchJoinedNonCache(int w, int r) {
		boolean[] a = new boolean[w - 1];
		int ans = 0;

		for(int c = 1; c <= a.length && 0 <= --r; c++) {
			int[] is = new int[c];

			for(int i = 0; i < c; i++) {
				a[i] = true;
				is[i] = c - 1 - i;
			}
			for(; ; ) {
				ans += searchDivided(a, r);

				int i;
				for(i = 0; i < c; i++) {
					a[is[i]] = false;
					is[i]++;

					if(is[i] < a.length - i) {
						a[is[i]] = true;
						break;
					}
				}
				if(i == c) {
					break;
				}
				while(0 <= --i) {
					is[i] = is[i + 1] + 1;
					a[is[i]] = true;
				}
			}
		}
		return ans;
	}

	private Map<String, Integer> _dividedCache = new TreeMap<String, Integer>(new Comparator<String>() {
		@Override
		public int compare(String a, String b) {
			return a.compareTo(b);
		}
	});

	private int searchDivided(boolean[] a, int r) {
		if(r == 0) {
			return 1;
		}
		List<Integer> parts = new ArrayList<Integer>();

		for(int i = 0; i < a.length; i++) {
			if(a[i]) {
				int j;

				for(j = i + 1; j < a.length; j++) {
					if(!a[j]) {
						break;
					}
				}
				int part = j - i;

				if(2 <= part) {
					parts.add(part);
				}
				i = j - 1;
			}
		}
		if(parts.size() <= 0) {
			return 0;
		}

		Collections.sort(parts, new Comparator<Integer>() {
			@Override
			public int compare(Integer a, Integer b) {
				return a.intValue() - b.intValue();
			}
		});

		int[] iParts = getInts(parts);

		String k = String.join(":", getStrings(iParts)) + ":" + r;

		Integer v = _dividedCache.get(k);

		//System.out.print("D:" + (v == null ? "---" : "HIT") + ":"); // test
		if(v == null) {
			v = searchDividedNonCache(iParts, r);
			_dividedCache.put(k, v);
		}
		//System.out.println(k + "=" + v); // test
		return v.intValue();
	}

	private int[] getInts(List<Integer> parts) {
		int[] dest = new int[parts.size()];

		for(int i = 0; i < parts.size(); i++) {
			dest[i] = parts.get(i).intValue();
		}
		return dest;
	}

	private String[] getStrings(int[] iParts) {
		String[] dest = new String[iParts.length];

		for(int i = 0; i < iParts.length; i++) {
			dest[i] = "" + iParts[i];
		}
		return dest;
	}

	private int searchDividedNonCache(int[] ws, int r) {
		int[] rHis = new int[ws.length];

		for(int i = 0; i < ws.length; i++) {
			rHis[i] = getRHi(ws[i], r);
		}
		int rm = getSum(rHis);

		if(rm < r) {
			return 0;
		}
		int[] rs = new int[ws.length];
		int[] rxs = new int[ws.length];
		int ans = 0;
		int i = 0;
		boolean ahead = true;

		for(; ; ) {
			if(ahead) {
				if(ws.length <= i) {
					int p = 1;

					for(i = 0; i < ws.length; i++) {
						p *= searchJoined(ws[i], rs[i]);
					}
					ans += p;
					i--;
					ahead = false;
					continue;
				}
				rm -= rHis[i];
				rs[i] = Math.max(0, r - rm);
				rxs[i] = Math.min(rHis[i], r);
				r -= rs[i];
				i++;
			}
			else {
				if(i < 0) {
					break;
				}
				if(rs[i] < rxs[i]) {
					rs[i]++;
					r--;
					i++;
					ahead = true;
				}
				else {
					r += rs[i];
					rm += rHis[i];
					i--;
				}
			}
		}
		return ans;
	}

	private int getRHi(int w, int rMax) {
		for(int r = 0; r < rMax; r++) {
			if(searchJoined(w, r + 1) <= 0) {
				return r;
			}
		}
		return rMax;
	}

	private int getSum(int[] vals) {
		int ret = 0;

		for(int val : vals) {
			ret += val;
		}
		return ret;
	}
}

/*

同様に、F(1)＝1, F(3)＝2, F(4)＝3, F(11)＝135 となることが確かめられます。

1	1
2	1
3	2
4	3
5	5
6	9
7	15
8	26
9	45
10	78
11	135
12	234
13	406
14	704
15	1222
16	2120
17	3679
18	6385
19	11081
20	19232
21	33379
22	57933
23	100550
24	174519
25	302903
26	525734
27	912493
28	1583775
29	2748893
30	4771144

*/
