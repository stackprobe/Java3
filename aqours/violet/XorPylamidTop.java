package aqours.violet;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import charlotte.tools.ArrayTools;
import charlotte.tools.IntTools;
import charlotte.tools.StringTools;

public class XorPylamidTop {
	public static void main(String[] args) {
		try {
			new XorPylamidTop().main2();
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
	}

	private void main2() throws Exception {
		List<String> lines = new ArrayList<String>();

		List<Integer> top = new ArrayList<Integer>();
		top.add(0);

		lines.add("1(1)0");

		for(int h = 2; h <= 65540; h++) {
			int size = top.size();

			for(int a = 1; a <= 2; a++) {
				for(int c = 0; c < size; c++) {
					top.add(top.get(c) + a);
				}
			}
			ArrayTools.sort(top, IntTools.comp);
			erasePairs(top);

			lines.add(h + "(" + top.size() + ")" + StringTools.join(":", StringTools.toStrings(IntTools.toInts(top))));
		}
		//FileTools.writeAllLines("C:/temp/XorPylamidTop.txt", lines, StringTools.CHARSET_SJIS); // メモリ不足で落ちる。
		try(OutputStreamWriter writer = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream("C:/temp/XorPylamidTop.txt")), StringTools.CHARSET_SJIS)) {
			for(String line : lines) {
				writer.write(line);
				writer.write("\r\n");
			}
		}
	}

	private void erasePairs(List<Integer> vals) {
		for(int index = 0; index + 1 < vals.size(); index++) {
			if(vals.get(index).intValue() == vals.get(index + 1).intValue()) {
				vals.set(index, null);
				index++;
				vals.set(index, null);
			}
		}
		ArrayTools.removeNull(vals);
	}
}
