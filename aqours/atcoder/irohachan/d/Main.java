package aqours.atcoder.irohachan.d;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		try {
			new Main().main2();

			System.out.println("OK!");
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	private int _w;
	private int _h;
	private int _a;
	private int _b;

	private boolean isInside(int x, int y) {
		return 0 <= x && x < _w && 0 <= y && y < _h && (_b <= x || y < _h - _a);
	}

	private class Cell {
		public int x;
		public int y;
		public int cnt;

		public boolean isOrigin() {
			return x == 0 && y == 0;
		}
	}

	private List<Cell> _cells = new ArrayList<Cell>();
	private List<Cell> _dest = new ArrayList<Cell>();

	private void main2() {
		try(Scanner sc = new Scanner(System.in)) {
			String line = sc.nextLine();
			String[] tokens = line.split("[ ]");
			int i = 0;

			// 順序は、H W A B
			_h = Integer.parseInt(tokens[i++]);
			_w = Integer.parseInt(tokens[i++]);
			_a = Integer.parseInt(tokens[i++]);
			_b = Integer.parseInt(tokens[i++]);
		}

		{
			Cell cell = new Cell();

			cell.x = _w - 1;
			cell.y = _h - 1;
			cell.cnt = 1;

			_cells.add(cell);
		}

		do {
			{
				Cell cell = _cells.get(0);

				tryAddToDest(cell.x - 1, cell.y, null, cell);
			}

			for(int i = 0; i < _cells.size(); i++) {
				Cell cell = _cells.get(i);

				if(i < _cells.size() - 1) {
					tryAddToDest(cell.x, cell.y - 1, cell, _cells.get(i + 1));
				}
				else {
					tryAddToDest(cell.x, cell.y - 1, cell, null);
				}
			}

			{
				List<Cell> tmp = _cells;
				_cells = _dest;
				_dest = tmp;
			}

			_dest.clear();
		}
		while(_cells.get(0).isOrigin() == false);

		System.out.println("" + _cells.get(0).cnt);
	}

	private void tryAddToDest(int x, int y, Cell under, Cell right) {
		if(isInside(x, y)) {
			Cell cell = new Cell();
			int cnt = 0;

			if(under != null) {
				cnt += under.cnt;
			}
			if(right != null) {
				cnt += right.cnt;
			}
			cnt %= 1000000007;

			cell.x = x;
			cell.y = y;
			cell.cnt = cnt;

			_dest.add(cell);
		}
	}
}

/*

入力例 1
Copy
2 3 1 1
出力例 1
Copy
2
2×3 マスありますが、左下の 1 マスには移動することができません。「右右下」、「右下右」という 2 つの移動の仕方があります。

入力例 2
Copy
10 7 3 4
出力例 2
Copy
3570
移動できないマスが 12 マスあります。

入力例 3
Copy
100000 100000 99999 99999
出力例 3
Copy
1
入力例 4
Copy
100000 100000 44444 55555
出力例 4
Copy
738162020

*/
