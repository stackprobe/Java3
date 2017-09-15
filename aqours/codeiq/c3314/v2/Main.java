package aqours.codeiq.c3314.v2;

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
			int n = Integer.parseInt(line);

			long ans = search(n);

			System.out.println("" + ans);
		}
	}

	private long search(int n) {
		long ans = 0L;

		for(int island = 1; island <= n; island++) {
			ans += search(island, n - island);
		}
		return ans;
	}

	private long search(int island, int wood) {
		return SearchIsland.get(island, wood);
	}
}
