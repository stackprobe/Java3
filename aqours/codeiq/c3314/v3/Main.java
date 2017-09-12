package aqours.codeiq.c3314.v3;

import java.util.Comparator;
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

			long ans = search(n);

			System.out.println("" + ans);
		}
	}

	private long search(int wood) {
		return search(1, wood - 1);
	}

	private Map<String, Long> _cache = new TreeMap<String, Long>(new Comparator<String>() {
		@Override
		public int compare(String a, String b) {
			return a.compareTo(b);
		}
	});

	private long search(int hi, int rem) {
		if(rem == 0) {
			return 1;
		}

		String k = hi + ":" + rem;
		Long v = _cache.get(k);

		if(v == null) {
			v = search_nc(hi, rem);
			_cache.put(k, v);
		}
		return v.longValue();
	}

	private long search_nc(int hi, int rem) {
		long ans = 0;
		int nextHiMax = Math.min(hi + 1, rem);

		for(int nextHi = 1; nextHi <= nextHiMax; nextHi++) {
			ans += search(nextHi, rem - nextHi);
		}
		return ans;
	}
}
