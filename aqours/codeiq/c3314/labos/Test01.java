package aqours.codeiq.c3314.labos;

public class Test01 {
	public static void main(String[] args) {
		try {
			main2();
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
	}

	private static int[] al = new int[] {
			1,
			1,
			2,
			3,
			5,
			9,
			15,
			26,
			45,
			78,
			135,
			234,
			406,
			704,
			1222,
			2120,
			3679,
			6385,
			11081,
			19232,
			33379,
			57933,
			100550,
			174519,
			302903,
			525734,
			912493,
			1583775,
			2748893,
			4771144,
	};

	private static void main2() {
		int[] a = al;

		for(int c = 0; c < 10; c++) {
			a = kaisa2(a);

			for(int v : a) {
				System.out.println("" + v);
			}
			System.out.println("");
		}
	}

	private static int[] kaisa(int[] src) {
		int[] dest = new int[src.length];

		dest[0] = src[0];

		for(int index = 1; index < src.length; index++) {
			dest[index] = src[index] - src[index - 1];
		}
		return dest;
	}

	private static int[] kaisa2(int[] src) {
		int[] dest = new int[src.length];

		dest[0] = src[0];
		dest[1] = src[1];

		for(int index = 2; index < src.length; index++) {
			dest[index] = src[index] - src[index - 1] - src[index - 2];
		}
		return dest;
	}
}
