package evergarden.xxxtools_test;

import evergarden.xxxtools.FilingCase2;

public class FilingCase2Test {
	public static void main(String[] args) {
		try {
			test01();

			System.out.println("OK!");
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
	}

	private static void test01() throws Exception {
		new FilingCase2("C:/temp/123");
	}
}
