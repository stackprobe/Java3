package evergarden.violet;

import charlotte.tools.StringTools;

public class Test05 {
	public static void main(String[] args) {
		try {
			test01();
			test02();
			test03();

			System.out.println("OK!");
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
	}

	private static void test01() {
		try {
			//throw new RuntimeException(); // -> ex
			//throw null; // -> ex
			//throw new OutOfMemoryError(); // -> th
			//doStackOverflow(0); // -> th
			//byte[] b = new byte[Integer.MAX_VALUE]; // -> th
			int d = 0; d /= d; // -> ex
		}
		catch(Exception e) {
			System.out.println("ex: " + e);
		}
		catch(Throwable e) {
			System.out.println("th: " + e);
		}
	}

	private static void doStackOverflow(int prm) {
		doStackOverflow(prm + 1);
	}

	private static void test02() {
		Test_e eTest;

		eTest = Test_e.Bloccoly;

		System.out.println("" + eTest);
	}

	private static enum Test_e {
		Broccoli,
		Bloccoly,
		Blockoly,
		Blocklie,
	}

	private static void test03() throws Exception {
		String str = "ABC\0def";
		byte[] bStr = str.getBytes(StringTools.CHARSET_UTF8);
		System.out.println(StringTools.toHex(bStr)); // -> "41424300646566"
	}
}
