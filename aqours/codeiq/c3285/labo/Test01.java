package aqours.codeiq.c3285.labo;

import aqours.codeiq.c3285.Main;
import charlotte.tools.FileTools;
import charlotte.tools.ReflecTools;
import charlotte.tools.SecurityTools;
import charlotte.tools.StringTools;
import charlotte.tools.SystemTools;

public class Test01 {
	public static void main(String[] args) {
		try {
			new Test01().main2();
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	private void main2() throws Exception {
		for(int n = 1; n <= 300; n++) {
			test01(n);
		}
		for(int n = 299990; n <= 300000; n++) {
			test01(n);
		}
		for(; ; ) {
			test01(SecurityTools.cRandom(1, 300000));
		}
	}

	private void test01(int n) throws Exception {
		int ans1;
		int ans2;

		System.out.println("n: " + n);

		// ans1
		{
			/*
			SystemTools.execOnBatch(
					// need / -> \\
					"ECHO " + n + " | C:\\Dev\\Annex\\codeiq\\q3285\\a.exe > C:/temp/1.txt"
					);
					*/
			SystemTools.execOnBatch(
					"C:/Dev/Annex/codeiq/q3285/Tests/a.exe h " + n + " > C:/temp/1.txt"
					);
			ans1 = Integer.parseInt(FileTools.readAllLines(
					"C:/temp/1.txt",
					StringTools.CHARSET_ASCII
					).get(0));
		}

		System.out.println("ans1: " + ans1);

		// ans2
		{
			Main m = new Main();

			ReflecTools.setObject(
					new ReflecTools.FieldData(ReflecTools.getField(Main.class, "n"), m),
					n
					);
			ans2 = (int)ReflecTools.invokeDeclaredMethod(Main.class, "h", m, new Object[0]);
		}

		System.out.println("ans2: " + ans2);

		if(ans1 != ans2) {
			throw null;
		}
	}
}
