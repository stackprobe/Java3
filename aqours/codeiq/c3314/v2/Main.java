package aqours.codeiq.c3314.v2;

import java.util.Arrays;
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

		for(int bwn = 1; bwn <= n; bwn++) {
			ans += searchJoined(bwn, n - bwn);
		}
		return ans;
	}

	private Map<String, Long> _joinedCache = new TreeMap<String, Long>(new Comparator<String>() {
		@Override
		public int compare(String a, String b) {
			return a.compareTo(b);
		}
	});

	/**
	 *
	 * @param bwn base wood num
	 * @param rem remain wood
	 * @return この条件におけるパターン数
	 */
	private long searchJoined(int bwn, int rem) {
		assert 1 <= bwn;
		assert 0 <= rem;

		if(rem == 0) {
			return 1;
		}
		if(bwn == 1) {
			return 0;
		}

		String k = bwn + ":" + rem;
		Long v = _joinedCache.get(k);

		if(v == null) {
			v = searchJoinedNonCache(bwn, rem);
			_joinedCache.put(k, v);
		}
		return v.longValue();
	}

	/**
	 * searchJoined本体
	 * @param bwn
	 * @param rem
	 * @return
	 */
	private long searchJoinedNonCache(int bwn, int rem) {
		assert 2 <= bwn;
		assert 1 <= rem;

		long ans = 0L;

		for(int div = 1; div < bwn / 2; div++) {
			ans += searchJoined(bwn, rem, div);
		}
		return ans;
	}

	/**
	 *
	 * @param bwn base wood num
	 * @param rem remain wood
	 * @param div part num
	 * @return この条件におけるパターン数
	 */
	private long searchJoined(int bwn, int rem, int div) {
		assert 2 <= bwn;
		assert 1 <= rem;
		assert 1 <= div;

		if(rem < div) {
			return 0;
		}

		long ans = 0L;
		int wid = bwn - 1;
		int pwTotalMax = Math.min(wid - div + 1, rem);
		int pwTotal = 0;
		int[] pws = new int[div];
		int pi = 0;
		boolean ahead = true;

		for(; ; ) {
			if(ahead) {
				if(div <= pi) {
					ans += searchDivided(pws, rem - pwTotal) * getArrange(pws, pwTotal, wid);
					pi--;
					ahead = false;
					continue;
				}
				pws[pi] = 1;
			}
			else {
				pws[pi]++;
			}
			int afterParts = div - pi - 1;

			if(pwTotal + pws[pi] + afterParts <= pwTotalMax) {
				pwTotal += pws[pi];
				pi++;
				ahead = true;
			}
			else {
				if(pi <= 0) {
					break;
				}
				pi--;
				ahead = false;
				pwTotal -= pws[pi];
			}
		}
		return ans;
	}

	private Map<String, Long> _dividedCache = new TreeMap<String, Long>(new Comparator<String>() {
		@Override
		public int compare(String a, String b) {
			return a.compareTo(b);
		}
	});

	/**
	 *
	 * @param pws part wood array
	 * @param rem remain wood
	 * @return この条件におけるパターン数
	 */
	private long searchDivided(int[] pws, int rem) {
		assert 1 <= pws.length;
		assert 0 <= rem;

		pws = elimPws(pws);

		String k = toKey(pws, rem);
		Long v = _dividedCache.get(k);

		if(v == null) {
			v = searchDividedNonCache(pws, rem);
			_dividedCache.put(k, v);
		}
		return v.longValue();
	}

	private int[] elimPws(int[] pws) {
		int n = 0;

		for(int pw : pws) {
			if(2 <= pw) {
				n++;
			}
		}
		int[] dest = new int[n];
		n = 0;

		for(int pw : pws) {
			if(2 <= pw) {
				dest[n++] = pw;
			}
		}
		Arrays.sort(dest);
		return dest;
	}

	private String toKey(int[] pws, int rem) {
		StringBuffer buff = new StringBuffer();

		for(int pw : pws) {
			buff.append(pw);
			buff.append(":");
		}
		buff.append(rem);
		return buff.toString();
	}

	/**
	 * searchDivided本体
	 * @param pws
	 * @param rem
	 * @return
	 */
	private long searchDividedNonCache(int[] pws, int rem) {
		assert 1 <= pws.length;
		assert 0 <= rem;

		long ans = 0L;
		int div = pws.length;
		int[] rems = new int[div];
		int[] maxs = new int[div];
		int[] rMaxs = new int[div];
		int pi = 0;
		boolean ahead = true;

		int maxSum = 0;
		for(int i = 0; i < div; i++) {
			maxs[i] = getPyramidSize(pws[i] - 1);
			maxSum += maxs[i];
		}
		if(maxSum < rem) {
			return 0L;
		}
		int remRem = rem;

		// このへん多分何か変 --->

		for(; ; ) {
			if(ahead) {
				if(div <= pi) {
					assert remRem != 0;
					ans += searchDivided(pws, rems);
					pi--;
					ahead = false;
					continue;
				}
				rMaxs[pi] = Math.min(remRem, maxs[pi]);

				int rCnt = remRem;
				for(int i = pi + 1; i < div; i++) {
					rCnt -= maxs[i];
				}
				rems[pi] = Math.max(rCnt, 0);

				assert rems[pi] <= rMaxs[pi];
				remRem -= rems[pi];
				pi++;
			}
			else {
				if(pi < 0) {
					break;
				}
				remRem += rems[pi];

				if(rems[pi] < rMaxs[pi]) {
					rems[pi]++;
					remRem -= rems[pi];
					pi++;
					ahead = true;
				}
				else {
					pi--;
				}
			}

			// <---
		}
		return ans;
	}

	private int getPyramidSize(int w) {
		if(w <= 0) {
			return 0;
		}
		int ret = 1;

		for(; 2 <= w; w--) {
			ret *= w;
		}
		return ret;
	}

	private long searchDivided(int[] pws, int[] rems) {
		assert pws.length != rems.length;

		int div = pws.length;
		long ans = 1L;

		for(int i = 0; i < div; i++) {
			ans *= searchJoined(pws[i], rems[i]);
		}
		return ans;
	}

	/**
	 * pwsの順序は固定、先頭と終端の隙間は 0<= それ以外の隙間は 1<=
	 * @param pws part wood array
	 * @param pwTotal total of pws
	 * @param wid width
	 * @return 何通りの配置があるか
	 */
	private long getArrange(int[] pws, int pwTotal, int wid) {
		return 1L; // TODO
	}
}
