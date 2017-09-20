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
import charlotte.tools.XYPoint;

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

	//private static final int DEST_W = 4500;
	private static final int DEST_W = 4242; // TMIX
	private static final int DEST_H = 6000;

	private static final int OVERLAY_W = 10;
	private static final int OVERLAY_H = 10;

	public void perform() throws Exception {
		BmpTools.AsciiStringBmp asBmp = new BmpTools.AsciiStringBmp(
				new Color(0, true),
				Color.WHITE,
				//"Arail",
				//"Courier New",
				"Consolas",
				Font.BOLD,
				300,
				750,
				750,
				-1,
				-1,
				1,
				10,
				25
				);

		asBmp.setSpaceWidth(30);

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

		toBW(dest);

		// overlay
		{
			Bmp src = dest;

			dest = new Bmp(w + OVERLAY_W, h + OVERLAY_H, new Bmp.Dot(Color.BLACK));

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

		System.out.println("expand.1");
		dest = dest.expand(DEST_W, DEST_H);
		System.out.println("expand.2");

		toBW(dest);
		antiEdge(dest);

		output(dest, Color.BLACK);

		bwToWb(dest);
		dest = borderRadius(dest);

		output(dest, Color.BLUE);
	}

	private void output(Bmp dest, Color backColor) throws Exception {

		//*
		dest = bToTrans(dest, backColor);
		//*/

		String wFile = "C:/temp/Design.png";
		wFile = FileTools.toCreatable(wFile, 2);
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

	private Bmp bToTrans(Bmp src, Color backColor) {
		Bmp dest = new Bmp(src.getWidth(), src.getHeight());

		final Bmp.Dot bgDot = new Bmp.Dot(backColor);
		final Bmp.Dot transDot = new Bmp.Dot(new Color(0, true));

		for(int x = 0; x < DEST_W; x++) {
			for(int y = 0; y < DEST_H; y++) {
				Bmp.Dot dot = src.getDot(x, y);

				if(dot.equals(bgDot)) {
					dest.setDot(x, y, transDot);
				}
				else {
					dest.setDot(x, y, dot);
				}
			}
		}
		return dest;
	}

	private void bwToWb(Bmp bmp) {
		final Bmp.Dot dot1 = new Bmp.Dot(Color.BLACK);
		final Bmp.Dot dot2 = new Bmp.Dot(Color.WHITE);

		for(int x = 0; x < DEST_W; x++) {
			for(int y = 0; y < DEST_H; y++) {
				Bmp.Dot dot = bmp.getDot(x, y);

				if(dot.equals(dot1)) {
					bmp.setDot(x, y, dot2);
				}
				else {
					bmp.setDot(x, y, dot1);
				}
			}
		}
	}

	private void antiEdge(Bmp bmp) {
		System.out.println("antiEdge.1");

		boolean conLoop;
		do {
			conLoop = false;

			for(int x = 1; x < bmp.getWidth() - 1; x++) {
				for(int y = 1; y < bmp.getHeight() - 1; y++) {
					int black = 0;

					if(bmp.getR(x - 1, y) == 0) black++;
					if(bmp.getR(x + 1, y) == 0) black++;
					if(bmp.getR(x, y - 1) == 0) black++;
					if(bmp.getR(x, y + 1) == 0) black++;

					boolean currBlack = bmp.getR(x, y) == 0;

					if(black <= 1) { // ? 3 <= white
						if(currBlack) {
							bmp.setDot(x, y, new Bmp.Dot(Color.WHITE));
							conLoop = true;
						}
					}
					else if (3 <= black) {
						if(!currBlack) {
							bmp.setDot(x, y, new Bmp.Dot(Color.BLACK));
							conLoop = true;
						}
					}
				}
			}
		}
		while(conLoop);

		System.out.println("antiEdge.2");
	}

	private Bmp borderRadius(Bmp bmp) {
		final int R = 200; // rad
		final int M = R / 3; // margin

		final int W = bmp.getWidth();
		final int H = bmp.getHeight();

		{
			Bmp dest = new Bmp(W + M * 2, H + M * 2, new Bmp.Dot(Color.WHITE));
			dest.simplePaste(bmp, M, M);
			bmp = dest;
		}

		System.out.println("expand_br.1");
		bmp = bmp.expand(W, H);
		System.out.println("expand_br.2");
		toBW(bmp);
		antiEdge(bmp);

		borderRadius(bmp,     R,     R,     0,     0, R, R, R); // 左上
		borderRadius(bmp, W - R,     R, W - R,     0, W, R, R); // 右上
		borderRadius(bmp, W - R, H - R, W - R, H - R, W, H, R); // 右下
		borderRadius(bmp,     R, H - R,     0, H - R, R, H, R); // 左下

		return bmp;
	}

	private void borderRadius(Bmp bmp, int origX, int origY, int l, int t, int r, int b, int rad) {
		XYPoint orig = new XYPoint(origX - 0.5, origY - 0.5);

		for(int x = l; x < r; x++) {
			for(int y = t; y < b; y++) {
				XYPoint pt = new XYPoint(x, y);

				if(rad < pt.getDistance(orig)) {
					bmp.setDot(x, y, new Bmp.Dot(Color.BLUE));
				}
			}
		}
	}
}
