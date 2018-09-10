package aqours.violet;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import charlotte.tools.FileTools;
import charlotte.tools.HugeQueue;
import charlotte.tools.StringTools;
import charlotte.tools.XNode;
import charlotte.tools.XStruct;

public class TokyoRoad {
	public static void main(String[] args) {
		try {
			new TokyoRoad().main2();

			System.out.println("OK!");
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	private void main2() throws Exception {
		for(String file : FileTools.lss("C:/wb/東京都地図")) {
			System.out.println("file: " + file);

			if(FileTools.isFile(file) &&
					file.toLowerCase().endsWith(".xml") &&
					FileTools.getFileSize(file) < 200000000 // < 200 MB
					) {
				System.out.println("*0");
				XStruct xSt = new XStruct();
				System.out.println("*1");
				xSt.add(XNode.load(file));
				System.out.println("*2");
				HugeQueue lines = xSt.toLines();
				System.out.println("*3");

				String wFile = FileTools.combine("C:/temp", FileTools.getLocal(file) + ".XStruct.txt");

				System.out.println("wFile: " + wFile);

				try(OutputStreamWriter writer = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(wFile)), StringTools.CHARSET_SJIS)) {
					while(1L <= lines.size()) {
						writer.write(lines.pollString() + "\r\n");
					}
				}

				System.gc();
			}
		}
	}
}
