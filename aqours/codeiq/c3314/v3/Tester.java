package aqours.codeiq.c3314.v3;

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

		for(int n = 1; n <= 60; n++) {
			Main m = new Main();
			dest.add(n + "\t" + ReflecTools.invokeDeclaredMethod(m.getClass(), "search", m, new Object[] { n }));
		}
		for(String line : dest) {
			System.out.println(line);
		}
	}
}
