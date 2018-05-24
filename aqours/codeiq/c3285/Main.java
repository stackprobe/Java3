package aqours.codeiq.c3285;

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

	private static Comparator<Integer> comp = new Comparator<Integer>() {
		@Override
		public int compare(Integer a, Integer b) {
			return a - b;
		}
	};

	private Map<Integer, Integer> footps = new TreeMap<Integer, Integer>(comp);
	private Map<Integer, Integer> gs = new TreeMap<Integer, Integer>(comp);
	private int n;

	private void main2() {
		try(Scanner sc = new Scanner(System.in)) {
			String line = sc.nextLine();
			n = Integer.parseInt(line);

			System.out.println("" + h());
		}
	}

	private int h() {
		int sum = 0;

		for(int k = 0; k <= n; k++) {
			sum += g(k);
		}
		return sum;
	}

	private int g(int k) {
		Integer ret = gs.get(k);

		if(ret == null) {
			addToGs(k);
			ret = gs.get(k);
		}
		return ret.intValue();
	}

	private void addToGs(int k) {
		footps.clear();

		int x = k;
		int i = 0;

		RING : do {
			do {
				footps.put(x, i);
				x = f(x);
				i++;

				if(gs.get(x) != null) {
					i += gs.get(x).intValue();
					break RING;
				}
			}
			while(footps.get(x) == null);

			int r = i - footps.get(x).intValue();

			do {
				gs.put(x, r);
				x = f(x);
			}
			while(gs.get(x) == null);

			i = r + footps.get(x).intValue();
		}
		while(false);

		x = k;

		while(gs.get(x) == null) {
			gs.put(x, i);
			x = f(x);
			i--;
		}
	}

	private int f(int x) {
		return (int)((4 * (long)x * (n - x)) / n);
	}
}
