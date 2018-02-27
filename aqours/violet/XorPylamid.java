package aqours.violet;

import java.util.List;

import charlotte.tools.ArrayTools;
import charlotte.tools.CsvData;
import charlotte.tools.StringTools;

public class XorPylamid {
	public static void main(String[] args) {
		try {
			new XorPylamid().main2();
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
	}

	private void main2() throws Exception {
		for(int h = 1; h <= 50; h++) {
			makeCsvFile(h, "C:/temp/XorPylamid_" + StringTools.zPad(h, 2) + ".csv");
		}
	}

	private void makeCsvFile(int h, String wFile) throws Exception {
		int w = h * 2 - 1;

		String[][] table = new String[h][];

		for(int r = 0; r < h; r++) {
			table[r] = new String[w];
		}
		for(int c = 0; c < w; c++) {
			table[0][c] = StringTools.zPad(c, 2);
		}
		for(int r = 0; r + 1 < h; r++) {
			for(int c = 0; c + 2 < w; c++) {
				if(table[r][c] != null &&
						table[r][c + 1] != null &&
						table[r][c + 2] != null
						) {
					String cell = table[r][c] + ":" + table[r][c + 1] + ":" + table[r][c + 2];
					List<String> vals = ArrayTools.<String>toList(cell.split("[:]"));
					//Merging.distinct(vals, StringTools.comp);
					ArrayTools.sort(vals, StringTools.comp);
					erasePairs(vals);
					cell = StringTools.join(":", vals);
					table[r + 1][c + 1] = cell;
				}
			}
		}
		for(int r = 0; r < h; r++) {
			for(int c = 0; c < w; c++) {
				if(table[r][c] == null) {
					table[r][c] = "";
				}
			}
		}

		CsvData.Stream writer = new CsvData.Stream(wFile);
		writer.writeOpen();
		try {
			writer.writeRows(table);
		}
		finally {
			writer.writeClose();
			writer = null;
		}
	}

	private void erasePairs(List<String> vals) {
		for(int index = 0; index + 1 < vals.size(); index++) {
			if(vals.get(index).equals(vals.get(index + 1))) {
				vals.set(index, null);
				index++;
				vals.set(index, null);
			}
		}
		ArrayTools.removeNull(vals);
	}
}
