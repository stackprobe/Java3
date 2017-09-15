package aqours.codeiq.c3314.v2;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		try {
			main2();
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	private static void main2() throws Exception {
		try(Scanner sc = new Scanner(System.in)) {
			String line = sc.nextLine();
			int n = Integer.parseInt(line);

			long ans = search(n);

			System.out.println("" + ans);
		}
	}

	private static long search(int wood) throws Exception {
		long ans = 0L;

		for(int island = 1; island <= wood; island++) {
			ans += search(island, wood - island);
		}
		return ans;
	}

	public static long search(int island, int wood) throws Exception {
		if(island < 1) throw null;
		if(wood < 0) throw null;

		if(wood == 0) {
			return 1L;
		}
		if(island == 1) {
			return 0L;
		}
		int ground = island - 1;
		long ans = 0L;

		for(int div = 1; ; div++) {
			long ret = new SearchGWD().action(ground, wood, div);

			if(ret == 1L) {
				break;
			}
			ans += ret;
		}
		return ans;
	}

	public static class SearchGWD {
		private long _ans;

		private long action(int ground, int wood, int div) throws Exception {
			if(ground < 1) throw null;
			if(wood < 1) throw null;
			if(div < 1) throw null;

			if(wood < div) {
				return -1L;
			}
			int islandsTotalMax = ground - (div - 1);

			if(islandsTotalMax < div) {
				return -1L;
			}
			_ans = 0L;

			new MaskIslands().search(div, islandsTotalMax, (islands) -> {
				int islandsTotal = 0;

				if(islands == null) throw null;
				if(islands.length != div) throw null;
				for(int island : islands) {
					if(island < 1) throw null;
					islandsTotal += island;
				}
				if(islandsTotal < div) throw null;
				if(islandsTotalMax < islandsTotal) throw null;

				int woodRem = wood - islandsTotal;

				long layout = new IslandsLayout().get(ground, islands);

				if(layout < 1L) throw null;

				new AllocWoods().search(islands, woodRem, (woods) -> {
					int woodsTotal = 0;

					if(woods == null) throw null;
					if(woods.length != div) throw null;
					for(int w : woods) {
						if(w < 0) throw null;
						woodsTotal += w;
					}
					if(woodsTotal != woodRem) throw null;

					long currAns = 1L;

					for(int index = 0; index < div; index++) {
						long ret = search(islands[index], woods[index]);

						if(ret < 1L) throw null;

						currAns *= ret;
					}
					_ans += currAns * layout;
				});
			});
			return _ans;
		}
	}
}
