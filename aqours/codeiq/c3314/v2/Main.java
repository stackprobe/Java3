package aqours.codeiq.c3314.v2;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import charlotte.tools.StringTools;

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
			long ret = search(island, wood - island);
			//System.out.println(island + ", " + (wood - island) + " -> " + ret); // test
			ans += ret;
		}
		return ans;
	}

	private static Map<String, Long> _searchCache = new TreeMap<String, Long>(StringTools.comp);

	public static long search(int island, int wood) throws Exception {
		if(island < 1) throw null; // assert
		if(wood < 0) throw null; // assert

		if(wood == 0) {
			return 1L;
		}
		if(island == 1) {
			return 0L;
		}

		// cache
		String k = island + ":" + wood;
		Long v = _searchCache.get(k);
		if(v != null) {
			return v.longValue();
		}

		int ground = island - 1;
		long ans = 0L;

		for(int div = 1; ; div++) {
			long ret = new SearchGWD().action(ground, wood, div);

			if(ret == -1L) {
				break;
			}
			ans += ret;
		}

		// cache
		_searchCache.put(k, ans);

		return ans;
	}

	public static class SearchGWD {
		private long _ans;

		private long action(int ground, int wood, int div) throws Exception {
			//if(ground < 1) throw null; // assert
			//if(wood < 1) throw null; // assert
			//if(div < 1) throw null; // assert

			if(wood < div) {
				return -1L;
			}
			int islandsTotalMax = ground - (div - 1);

			if(islandsTotalMax < div) {
				return -1L;
			}
			islandsTotalMax = Math.min(islandsTotalMax, wood);
			final int f_islandsTotalMax = islandsTotalMax;

			_ans = 0L;

			new MaskIslands().search(div, islandsTotalMax, (islands) -> {
				int islandsTotal = 0;

				//if(islands == null) throw null; // assert
				//if(islands.length != div) throw null; // assert
				for(int island : islands) {
					//if(island < 1) throw null; // assert
					islandsTotal += island;
				}
				//if(islandsTotal < div) throw null; // assert
				//if(f_islandsTotalMax < islandsTotal) throw null; // assert

				int woodRem = wood - islandsTotal;

				long layout = new IslandsLayout().get(ground, islands);
				//System.out.println("layout: " + ground + ", {" + StringTools.join(", ", StringTools.toStrings(islands)) + "} -> " + layout); // test

				//if(layout < 1L) throw null; // assert

				new AllocWoods().search(islands, woodRem, (woods) -> {
					int woodsTotal = 0;

					//if(woods == null) throw null; // assert
					//if(woods.length != div) throw null; // assert
					for(int w : woods) {
						//if(w < 0) throw null; // assert
						woodsTotal += w;
					}
					//if(woodsTotal != woodRem) throw null; // assert

					long currAns = 1L;

					for(int index = 0; index < div; index++) {
						long ret = search(islands[index], woods[index]);

						//if(ret < 1L) throw null; // assert

						currAns *= ret;
					}
					//System.out.println("layout: " + layout); // test
					_ans += currAns * layout;
				});
			});
			return _ans;
		}
	}
}
