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

public class Design4 {
	private List<String> _lines;

	public Design4(String relPath) {
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

	private static final int DEST_W = 4500;
	//private static final int DEST_W = 4242; // TMIX
	private static final int DEST_H = 6000;

	private static final int OVERLAY_W = 5;
	private static final int OVERLAY_H = 5;

	public void perform() throws Exception {
		BmpTools.AsciiStringBmp asBmp = new BmpTools.AsciiStringBmp(
				new Color(0, true),
				Color.WHITE,
				"Arail",
				//"Courier New",
				//"Consolas",
				Font.BOLD,
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
				new Bmp.Dot(0, 0, 0, 0)
				);
		h = 0;

		line_index = 0;
		for(Bmp bmp : bmps) {
			dest.paste(bmp, 0, h);
			h += getYStep(bmp.getHeight(), line_index);
			line_index++;
			//System.out.println("*" + bmp.getHeight()); // test
			System.out.println("paste row " + line_index);
		}

		System.out.println("expand.1");
		dest = dest.expand(DEST_W - (OVERLAY_W - 1), DEST_H - (OVERLAY_H - 1));
		System.out.println("expand.2");

		toBW(dest);

		// overlay
		{
			Bmp src = dest;

			dest = new Bmp(DEST_W, DEST_H, new Bmp.Dot(Color.BLACK));

			System.out.println("overlay.1");

			for(int x = src.getWidth() - 1; 0 <= x; x--) {
				for(int y = src.getHeight() - 1; 0 <= y; y--) {
					if(src.getR(x, y) == 255) { // ? 白
						for(int olx = 0; olx < OVERLAY_W; olx++) {
							for(int oly = 0; oly < OVERLAY_H; oly++) {
								dest.setDot(x + olx, y + oly, new Bmp.Dot(Color.WHITE));
							}
						}
					}
				}
			}
			// osoi...
			/*
			for(int olx = 0; olx < OVERLAY_W; olx++) {
				for(int oly = 0; oly < OVERLAY_H; oly++) {
					dest.paste(src, olx, oly);
					System.out.println("overlay ok " + olx + ", " + oly);
				}
			}
			*/

			System.out.println("overlay.2");
		}

		//bToTrans(dest);

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
}
