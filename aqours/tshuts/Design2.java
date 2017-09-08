package aqours.tshuts;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import charlotte.tools.Bmp;
import charlotte.tools.BmpTools;
import charlotte.tools.Canvas;
import charlotte.tools.FileTools;
import charlotte.tools.RunnableEx;
import charlotte.tools.StringTools;
import charlotte.tools.WorkDir;

public class Design2 {
	private List<String> _lines;

	public Design2(String relPath) {
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

	private static final int DEST_W = 4242;
	private static final int DEST_H = 6000;

	public void perform() throws Exception {
		BmpTools.AsciiStringBmp asBmp = new BmpTools.AsciiStringBmp(
				new Color(0, true),
				Color.WHITE,
				//"Impact",
				//"Consolas",
				//"Courier New",
				"Verdana",
				//"Arial",
				//Font.BOLD | Font.ITALIC,
				Font.BOLD,
				//Font.PLAIN,
				300,
				750,
				750,
				-1,
				-1,
				1,
				20
				);

		List<Bmp> bmps = new ArrayList<Bmp>();

		for(String line : _lines) {
			line = line.replace("\t", "    ");
			bmps.add(asBmp.getStringBmp(line));
		}
		int w = 0;
		int h = 0;

		int line_index = 0;
		for(Bmp bmp : bmps) {
			w = Math.max(w, bmp.getWidth());
			h += getYStep(bmp.getHeight(), line_index);
			line_index++;
		}

		Bmp dest = new Bmp(
				w,
				h,
				Color.BLACK
				//new Bmp.Dot(0, 0, 0, 0)
				);
		h = 0;

		line_index = 0;
		for(Bmp bmp : bmps) {
			dest.paste(bmp, 0, h);
			h += getYStep(bmp.getHeight(), line_index);
			line_index++;
			//System.out.println("*" + bmp.getHeight()); // test
		}

		dest = dest.expand(DEST_W, DEST_H);
//		dest = dest.expand(dest.getWidth() * DEST_H / h, DEST_H);

		toBW(dest);

		putColorDiagonal(dest, new Color(0xffffc0), 800, true);
		putColorDiagonal(dest, new Color(0xffff80), 600, true);
		putColorDiagonal(dest, new Color(0xffff40), 400, true);
		putColorDiagonal(dest, new Color(0xffff00), 200, true);

		bToTrans(dest);

		String wFile = "C:/temp/Design.png";
		wFile = FileTools.toCreatable(wFile);
		dest.writeToFile(wFile);
	}

	private int getYStep(int h, int line_index) {
		if(line_index + 1 < _lines.size() &&
				_lines.get(line_index).equals(_lines.get(line_index + 1)) == false &&
				_lines.get(line_index).trim().equals("}") &&
				_lines.get(line_index + 1).trim().equals("}")
				) {
			return 100;
		}
		return Math.max(150, h);
	}

	private void toBW(Bmp bmp) {
		for(int x = 0; x < DEST_W; x++) {
			for(int y = 0; y < DEST_H; y++) {
				Bmp.Dot dot;

				if(bmp.getR(x, y) < 128) {
					dot = new Bmp.Dot(Color.BLACK);
				}
				else {
					dot = new Bmp.Dot(Color.WHITE);
				}
				bmp.setDot(x, y, dot);
			}
		}
	}

	private void bToTrans(Bmp bmp) {
		final Bmp.Dot bgDot = new Bmp.Dot(Color.BLACK);
		final Bmp.Dot transDot = new Bmp.Dot(new Color(0, true));

		for(int x = 0; x < DEST_W; x++) {
			for(int y = 0; y < DEST_H; y++) {
				Bmp.Dot dot = bmp.getDot(x, y);

				if(dot.equals(bgDot)) {
					bmp.setDot(x, y, transDot);
				}
			}
		}
	}

	private void putColorDiagonal(Bmp bmp, Color color, int span, boolean backFlag) {
		System.out.println("PCD.1");

		Bmp.Dot dotNew = new Bmp.Dot(color);

		for(int y = 0; y < DEST_H; y++) {
			int x = (y * DEST_W) / DEST_H;

			if(backFlag) {
				x = DEST_W - x;
			}
			int l = x - span;
			int r = x + span;

			l = Math.max(0, l);
			r = Math.min(r, DEST_W);

			for(x = l; x < r; x++) {
				Bmp.Dot dot = bmp.getDot(x, y);

				// 黒(背景) -> noop
				if(dot.equals(new Bmp.Dot(Color.BLACK)) == false) {
					new Canvas(bmp).fillSameColor(x, y, dotNew);
				}
			}
		}
		System.out.println("PCD.2");
	}
}
