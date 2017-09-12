package aqours.codeiq.c3314.v2;

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

	private long search(int n) {
		long ans = 0L;

		for(int island = 1; island <= n; island++) {
			ans += search(island, n - island);
		}
		return ans;
	}

	private Map<String, Long> _joinedCache = new TreeMap<String, Long>(new Comparator<String>() {
		@Override
		public int compare(String a, String b) {
			return a.compareTo(b);
		}
	});

	private long search(int island, int wood) {
		assert 0 <= island;
		assert 0 <= wood;

		String k = island + ":" + wood;
		Long v = _joinedCache.get(k);

		if(v == null) {
			v = SearchIsland.get(island, wood);
			_joinedCache.put(k, v);
		}
		return v.longValue();
	}
}
