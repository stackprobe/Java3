package aqours.codeiq.c3314.v2_1;

import java.util.ArrayList;
import java.util.List;

import charlotte.tools.ReflecTools;

public class Tester {
	public static void main(String[] args) {
		try {
			main2();
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
	}

	private static void main2() throws Exception {
		List<String> dest = new ArrayList<String>();

		long startMillis = System.currentTimeMillis();

		for(int n = 1; ; n++) {
			long elapseMillis = System.currentTimeMillis() - startMillis;
			System.out.println(n + " now... " + elapseMillis + " [ms]");
			if(30000 < elapseMillis) {
				System.out.println("timeout!");
				break;
			}

			Main m = new Main();
			dest.add(n + "\t" + ReflecTools.invokeDeclaredMethod(m.getClass(), "search", m, new Object[] { n }));
		}
		for(String line : dest) {
			System.out.println(line);
		}
	}
}
