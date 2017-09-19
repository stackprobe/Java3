package aqours.codeiq.c3299;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

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

	private class State {
		public String[] towers = new String[3];

		public boolean isCompleted() {
			return towers[0].isEmpty() && (towers[1].isEmpty() || towers[2].isEmpty());
		}

		public String toLiteral() {
			return String.join(":", towers);
		}

		//public State back = null;
	}

	private List<State> currs = new ArrayList<State>();
	private List<State> nexts = new ArrayList<State>();

	private Set<String> knowns = new HashSet<String>();

	private void main2() {
		try(Scanner sc = new Scanner(System.in)) {
			String line = sc.nextLine();

			State s = new State();
			s.towers[0] = line;
			s.towers[1] = "";
			s.towers[2] = "";
			currs.add(s);
			knowns.add(s.toLiteral());

			int ans = getAns();

			System.out.println("" + ans);
		}
	}

	private int getAns() {
		for(int ans = 1; ; ans++) {
			for(State curr : currs) {
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(i != j) {
							if(tryPutNext(curr, i, j)) {
								return ans;
							}
						}
					}
				}
			}

			List<State> bkCurrs = currs;
			currs = nexts;
			nexts = bkCurrs;
			nexts.clear();
		}
	}

	/**
	 *
	 * @param curr
	 * @param i
	 * @param j
	 * @return is completed
	 */
	private boolean tryPutNext(State curr, int i, int j) {
		if(curr.towers[i].isEmpty()) {
			return false;
		}
		char disk = curr.towers[i].charAt(0);

		if(canPut(disk, curr.towers[j]) == false) {
			return false;
		}
		State next = new State();

		for(int c = 0; c < 3; c++) {
			next.towers[c] = curr.towers[c];
		}
		next.towers[i] = next.towers[i].substring(1);
		next.towers[j] = disk + next.towers[j];
		//next.back = curr;

		if(next.isCompleted()) {
			//printRoute(next);
			return true;
		}
		String nextLit = next.toLiteral();

		if(knowns.contains(nextLit)) {
			return false;
		}
		knowns.add(nextLit);
		nexts.add(next);
		return false;
	}

	private boolean canPut(char disk, String tower) {
		for(int i = 0; i < tower.length(); i++) {
			if(tower.charAt(i) < disk) {
				for(int j = 0; j < i; j++) {
					if(tower.charAt(i) < tower.charAt(j)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/*
	private void printRoute(State state) {
		do {
			System.out.println(state.toLiteral());
			state = state.back;
		}
		while(state != null);
	}
	*/
}

/**
 *
testvect

1234
6

123
4

1314
5

 *
 */
