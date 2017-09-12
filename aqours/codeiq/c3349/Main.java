package aqours.codeiq.c3349;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
		System.exit(0);
	}

	private Comparator<String> _compString = new Comparator<String>() {
		@Override
		public int compare(String a, String b) {
			return a.compareTo(b);
		}
	};

	private Set<String> _emptyDepends = new TreeSet<String>(_compString);

	private void main2() {
		Map<String, Set<String>> jobs = new TreeMap<String, Set<String>>(_compString);

		try(Scanner sc = new Scanner(System.in)) {
			while(sc.hasNextLine()) {
				String line = sc.nextLine();

				// スペースや制御文字を除くASCII文字 <<< ":"も含むじゃねえか...
				int dlmi = line.indexOf(':');

				if(dlmi == -1) {
					String job = line.trim();

					jobs.put(job, _emptyDepends);
				}
				else {
					String job = line.substring(0, dlmi).trim();
					Set<String> sDepends = new TreeSet<String>(_compString);
					String[] depends = line.substring(dlmi + 1).split("[ ]");

					for(String depend : depends) {
						depend = depend.trim();

						if(depend.isEmpty() == false) {
							sDepends.add(depend);

							if(jobs.containsKey(depend) == false) {
								jobs.put(depend, _emptyDepends);
							}
						}
					}
					jobs.put(job, sDepends);
				}
			}
		}

		while(jobs.isEmpty() == false) {
			List<String> jobGrp = new ArrayList<String>();

			for(String job : jobs.keySet()) {
				if(jobs.get(job).isEmpty()) {
					jobGrp.add(job);
				}
			}
			for(String job : jobGrp) {
				jobs.remove(job);
			}
			for(String job : jobGrp) {
				for(Set<String> depends : jobs.values()) {
					depends.remove(job);
				}
			}
			Collections.sort(jobGrp, _compString);
			System.out.println(String.join(" ", jobGrp));
		}
	}
}
