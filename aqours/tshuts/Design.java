package aqours.tshuts;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import charlotte.tools.Bmp;
import charlotte.tools.BmpTools;
import charlotte.tools.FileTools;
import charlotte.tools.RunnableEx;
import charlotte.tools.StringTools;
import charlotte.tools.WorkDir;

public class Design {
	private List<String> _lines;

	public Design(String relPath) {
		try {
			try(WorkDir wd = new WorkDir()) {
				String file = wd.makeSubPath();
				FileTools.writeAllBytes(file, FileTools.readToEnd(Design.class.getResource(relPath)));
				_lines = FileTools.readAllLines(file, StringTools.CHARSET_ASCII);
			}
		}
		catch(Throwable e) {
			throw RunnableEx.re(e);
		}
	}

	public void perform() throws Exception {
		BmpTools.AsciiStringBmp asBmp = new BmpTools.AsciiStringBmp(
				new Color(0, true),
				Color.WHITE,
				"Consolas",
				//"Courier New",
				Font.BOLD,
				300,
				600,
				600,
				-1,
				-1,
				1,
				30
				);

		List<Bmp> bmps = new ArrayList<Bmp>();

		for(String line : _lines) {
			line = line.replace("\t", "    ");
			bmps.add(asBmp.getStringBmp(line));
		}
		int w = 0;
		int h = 0;

		for(Bmp bmp : bmps) {
			w = Math.max(w, bmp.getWidth());
			h += getYStep(bmp.getHeight());
		}
		Bmp dest = new Bmp(
				w,
				h,
				Color.BLACK
				//new Bmp.Dot(0, 0, 0, 0)
				);
		h = 0;

		for(Bmp bmp : bmps) {
			dest.paste(bmp, 0, h);
			h += getYStep(bmp.getHeight());
			//System.out.println("*" + bmp.getHeight()); // test
		}

		dest = dest.expand(4242, 6000);
//		dest = dest.expand(dest.getWidth() * 6000 / h, 6000);

		String wFile = "C:/temp/Design.png";
		wFile = FileTools.toCreatable(wFile);
		dest.writeToFile(wFile);
	}

	private int getYStep(int h) {
		return Math.max(150, h);
	}
}
