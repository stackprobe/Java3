package aqours.codeiq.c3239;

import java.util.ArrayList;
import java.util.List;
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

	/**
	 * 1 以上
	 */
	private int inpNum;

	private String ansMax;
	private String ansMin;

	private void main2() {
		try(Scanner sc = new Scanner(System.in)) {
			String line = sc.nextLine();
			inpNum = Integer.parseInt(line);

			search();

			System.out.println(ansMax + "," + ansMin);
		}
	}

	private void search() {
		int i = 1;

		while(getFibo(i + 1) <= inpNum) {
			i++;
		}

		StringBuffer buff = new StringBuffer();
		int n = inpNum;

		while(0 <= i) {
			int f = getFibo(i);

			if(f <= n) {
				n -= f;
				buff.append('1');
			}
			else {
				buff.append('0');
			}
			i--;
		}
		ansMax = buff.toString();
		ansMin = ansMax;

		for(; ; ) {
			String tmp = ansMin.replace("100", "011");

			if(tmp.endsWith("10")) {
				tmp = tmp.substring(0, tmp.length() - 2) + "01";
			}
			else if(ansMin.equals(tmp)) {
				break;
			}
			ansMin = tmp;
		}

		ansMin = ansMin.substring(ansMin.indexOf('1'));
		// old
		/*
		while(ansMin.startsWith("0")) {
			ansMin = ansMin.substring(1);
		}
		*/
	}

	private List<Integer> fibos = new ArrayList<Integer>();

	{
		fibos.add(1);
		fibos.add(1);
	}

	private int getFibo(int i) {
		while(fibos.size() <= i) {
			fibos.add(fibos.get(i - 2) + fibos.get(i - 1));
		}
		return fibos.get(i);
	}
}

/*

【例】
入力	出力
22	10000010,1010111
27	10010010,1101101
34	100000000,10101011

*/
