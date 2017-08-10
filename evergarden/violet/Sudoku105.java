package evergarden.violet;

import java.awt.Color;

import charlotte.tools.AutoTable;
import charlotte.tools.Bmp;
import charlotte.tools.Canvas;
import charlotte.tools.CsvData;
import charlotte.tools.FileTools;

public class Sudoku105 {
	public static void main(String[] args) {
		try {
			main2();
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	private static int CELL_WH = 26;
	private static Bmp _bmp;
	private static Canvas _canvas;

	private static void main2() throws Exception {
		_bmp = new Bmp(3100, 1840, Color.WHITE);
		_canvas = new Canvas(_bmp);

		for(int x = 0; x < 10; x++) {
			for(int y = 0; y < 6; y++) {
				drawFrame((1 + x * 12) * CELL_WH, (1 + y * 12) * CELL_WH);
			}
		}
		for(int x = 0; x < 9; x++) {
			for(int y = 0; y < 5; y++) {
				drawFrame((7 + x * 12) * CELL_WH, (7 + y * 12) * CELL_WH);
			}
		}
		CsvData csv = new CsvData();
		csv.readFile("C:/Dev/Main/Sudoku/SampleData/自作/0014/Input.csv");
		AutoTable<String> table = csv.getTable();

		Bmp digitsBmp = Bmp.getBmp(FileTools.readToEnd(Sudoku105.class.getResource("res/digits.bmp")));

		for(int x = 0; x < table.getWidth(); x++) {
			for(int y = 0; y < table.getHeight(); y++) {
				String cell = table.get(x, y);

				if("".equals(cell)) {
					continue;
				}
				int bangou = Integer.parseInt(cell);

				_bmp.paste(
						digitsBmp.cut(bangou * 20, 0, 20, 20),
						(1 + x) * CELL_WH + 3,
						(1 + y) * CELL_WH + 3
						);
			}
		}

		_bmp.writeToFile("C:/temp/Sudoku105.png");
	}

	private static void drawFrame(int l, int t) {
		for(int c = 1; c < 9; c++) {
			_canvas.drawLine(
					l,
					t + c * CELL_WH,
					l + 9 * CELL_WH,
					t + c * CELL_WH,
					Color.BLACK
					);
			_canvas.drawLine(
					l + c * CELL_WH,
					t,
					l + c * CELL_WH,
					t + 9 * CELL_WH,
					Color.BLACK
					);
		}
		for(int c = 3; c < 9; c += 3) {
			for(int bx = -1; bx <= 1; bx++) {
				for(int by = -1; by <= 1; by++) {
					_canvas.drawLine(
							l + bx,
							t + by + c * CELL_WH,
							l + bx + 9 * CELL_WH,
							t + by + c * CELL_WH,
							Color.BLACK
							);
					_canvas.drawLine(
							l + bx + c * CELL_WH,
							t + by,
							l + bx + c * CELL_WH,
							t + by + 9 * CELL_WH,
							Color.BLACK
							);
				}
			}
		}
		for(int c = 0; c <= 9; c += 9) {
			for(int bx = -2; bx <= 2; bx++) {
				for(int by = -2; by <= 2; by++) {
					_canvas.drawLine(
							l + bx,
							t + by + c * CELL_WH,
							l + bx + 9 * CELL_WH,
							t + by + c * CELL_WH,
							Color.BLACK
							);
					_canvas.drawLine(
							l + bx + c * CELL_WH,
							t + by,
							l + bx + c * CELL_WH,
							t + by + 9 * CELL_WH,
							Color.BLACK
							);
				}
			}
		}
	}
}
