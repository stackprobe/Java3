package aqours.codeiq.c3424;

import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {
	public static void main(String[] args) {
		try {
			new Main().main2();
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
	}

	private void main2() {
		try(Scanner sc = new Scanner(System.in)) {
			String line = sc.nextLine();
			int rNum = Integer.parseInt(line);

			Set<Double> rs = getRs(rNum);

			System.out.println("" + rs.size());
		}
	}

	private Map<Integer, Set<Double>> rsCache = new TreeMap<Integer, Set<Double>>(new Comparator<Integer>() {
		@Override
		public int compare(Integer a, Integer b) {
			return a - b;
		}
	});

	private Set<Double> getRs(int rNum) {
		Set<Double> ret = rsCache.get(rNum);

		if(ret == null) {
			ret = getRs_Main(rNum);
			rsCache.put(rNum, ret);
		}
		return ret;
	}

	private Set<Double> getRs_Main(int rNum) {
		Set<Double> dest = createRSet();

		if(rNum == 1) {
			dest.add(1.0);
		}
		else {
			for(int c = 1; c <= rNum / 2; c++) {
				Set<Double> rs1 = getRs(c);
				Set<Double> rs2 = getRs(rNum - c);

				for(double r1 : rs1) {
					for(double r2 : rs2) {
						dest.add(r1 + r2);
						dest.add(1.0 / ((1.0 / r1) + (1.0 / r2)));
					}
				}
			}
		}
		return dest;
	}

	private static final double R_GOSA_MARGIN = 0.0000000001; // 1 / 10^10

	private Set<Double> createRSet() {
		return new TreeSet<Double>(new Comparator<Double>() {
			@Override
			public int compare(Double a, Double b) {
				if(a + R_GOSA_MARGIN < b) {
					return -1;
				}
				if(a - R_GOSA_MARGIN > b) {
					return 1;
				}
				return 0;
			}
		});
	}
}
