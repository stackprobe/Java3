package aqours.codeiq.c3285.labo;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import charlotte.tools.IntTools;

public class Main01 {
	public static void main(String[] args) {
		try {
			new Main01().main2();
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
	}

	private int _n;
	private Map<Integer, Integer> _gCache = new TreeMap<Integer, Integer>(IntTools.comp);
	private Map<Integer, Integer> _footps = new TreeMap<Integer, Integer>(IntTools.comp);

	private void main2() {
		try(Scanner sc = new Scanner(System.in)) {
			String line = sc.nextLine();
			_n = Integer.parseInt(line);

			int ans = h();

			System.out.println("" + ans);
		}
	}

	private int h() {
		int sum = 0;

		for(int k = 0; k <= _n; k++) {
			sum += g(k);
		}
		return sum;
	}

	private int g(int k) {
		Integer ret = _gCache.get(k);

		if(ret == null) {
			gMain(k);
			ret = _gCache.get(k);
		}
		return ret.intValue();
	}

	private void gMain(int k) {
		_footps.clear();

		int x = k;
		int i = 0;

		do {
			_footps.put(x, i);
			x = f(x);
			i++;

			if(_gCache.get(x) != null) {
				i += _gCache.get(x).intValue();
				x = k;

				while(_gCache.get(x) == null) {
					_gCache.put(x, i);
					x = f(x);
					i--;
				}
				return;
			}
		}
		while(_footps.get(x) == null);

		int r = i - _footps.get(x).intValue();

		do {
			_gCache.put(x, r);
			x = f(x);
		}
		while(_gCache.get(x) == null);

		i = r + _footps.get(x).intValue();
		x = k;

		while(_gCache.get(x) == null) {
			_gCache.put(x, i);
			x = f(x);
			i--;
		}
	}

	private int f(int x) {
		return (int)((4 * (long)x * (_n - x)) / _n);
	}
}
