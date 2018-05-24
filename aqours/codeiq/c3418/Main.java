package aqours.codeiq.c3418;

import java.util.Scanner;

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
			String[] tokens = line.split("[,]");
			long l = Long.parseLong(tokens[0].trim());
			long h = Long.parseLong(tokens[1].trim());

			String ans = search(l, h);

			System.out.println(ans);
		}
	}

	private String search(long vl, long vh) {
		Palindrome l = new Palindrome(vl);
		Palindrome h = new Palindrome(vh);

		if(l.vlaue() < vl) {
			l.next();
		}
		if(vh < h.vlaue()) {
			h.prev();
		}
		if(h.vlaue() < l.vlaue()) {
			return "-";
		}
		while(l.rank() < h.rank()) {
			long r99Span = l.r99Span();
			long r10Span = h.r10Span();

			long span = Math.min(r99Span, r10Span);

			l.left += span;
			h.left -= span;

			l.next();
			h.prev();
		}
		if(l.rank() == h.rank()) {
			long span = h.left - l.left;

			span++;
			span /= 2;

			l.left += span;
			h.left -= span;
		}
		if(l.vlaue() == h.vlaue()) {
			return "" + l.vlaue();
		}
		return h.vlaue() + "," + l.vlaue();
	}

	private class Palindrome {
		public long left;
		public boolean odd;

		public Palindrome(long value) {
			String sVal = "" + value;
			int rank = sVal.length();
			left = Long.parseLong(sVal.substring(0, (rank + 1) / 2));
			odd = rank % 2 == 1;
		}

		public long vlaue() {
			String sVal = "" + left;
			sVal += reverse(sVal).substring(odd ? 1 : 0);
			return Long.parseLong(sVal);
		}

		public int leftRank() {
			return 1 <= left ? ("" + left).length() : 0;
		}

		public int rank() {
			return leftRank() * 2 - (odd ? 1 : 0);
		}

		public void next() {
			int r = leftRank();

			left++;

			if(r < leftRank()) {
				if(odd) {
					left /= 10;
				}
				odd = !odd;
			}
		}

		public void prev() {
			int r = leftRank();

			left--;

			if(leftRank() < r) {
				if(!odd) {
					left *= 10;
					left += 9;
				}
				odd = !odd;
			}
		}

		public long r99Span() {
			return Long.parseLong(repeat("9", leftRank())) - left;
		}

		public long r10Span() {
			return left - Long.parseLong("1" + repeat("0", leftRank() - 1));
		}
	}

	private String reverse(String str) {
		StringBuffer buff = new StringBuffer();

		for(int index = str.length() - 1; 0 <= index; index--) {
			buff.append(str.charAt(index));
		}
		return buff.toString();
	}

	private String repeat(String str, int count) {
		StringBuffer buff = new StringBuffer();

		while(0 < count--) {
			buff.append(str);
		}
		return buff.toString();
	}
}
