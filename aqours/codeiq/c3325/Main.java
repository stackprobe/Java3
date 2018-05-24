package aqours.codeiq.c3325;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		try {
			new Main().main2();
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
	}

	private double HCOMB_Y_STEP = Math.sqrt(3.0 / 4.0);

	private class Point {
		public double x;
		public double y;
	}

	private class Hcomb {
		private Point[][] _cells = new Point[][] {
			new Point[3],
			new Point[4],
			new Point[5],
			new Point[4],
			new Point[3],
		};

		public Hcomb() {
			for(int y = 0; y < 5; y++) {
				// TODO
			}
		}
	}

	private void main2() {
		try(Scanner sc = new Scanner(System.in)) {
			String line = sc.nextLine();
			String[] tokens = line.split("[ ]");

			// TODO

			System.out.println("" + 123);
		}
	}
}
