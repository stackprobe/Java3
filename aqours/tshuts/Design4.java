package aqours.tshuts;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import charlotte.tools.AutoTable;
import charlotte.tools.Bmp;
import charlotte.tools.BmpTools;
import charlotte.tools.Canvas;
import charlotte.tools.FileTools;
import charlotte.tools.RunnableEx;
import charlotte.tools.StringTools;
import charlotte.tools.WorkDir;
import charlotte.tools.XYPoint;
import charlotte.tools.Xorshift;

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
	//private static final int DEST_W = 4242; // TMIX
	private static final int DEST_W = 4250;
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

		// ----

		bwToWb(dest);
		Bmp dest_2 = borderRadius(dest);

		output(dest_2, Color.BLUE);

		// ----

		dest_2 = borderRandomTiles(dest);

		output(dest_2, Color.BLACK);
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

		System.out.println("br.1");

		{
			Bmp dest = new Bmp(W + M * 2, H + M * 2, new Bmp.Dot(Color.WHITE));
			dest.simplePaste(bmp, M, M);
			bmp = dest;
		}

		bmp = bmp.expand(W, H);
		toBW(bmp);
		antiEdge(bmp);

		borderRadius(bmp,     R,     R,     0,     0, R, R, R); // 左上
		borderRadius(bmp, W - R,     R, W - R,     0, W, R, R); // 右上
		borderRadius(bmp, W - R, H - R, W - R, H - R, W, H, R); // 右下
		borderRadius(bmp,     R, H - R,     0, H - R, R, H, R); // 左下

		System.out.println("br.2");

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

	private Bmp borderRandomTiles(Bmp bmp) {
		final int T = 25; // tile w/h size
		final int M = 100; // margin

		final int W = bmp.getWidth();
		final int H = bmp.getHeight();

		System.out.println("brt.1");

		if(W % T != 0) throw null;
		if(H % T != 0) throw null;

		{
			Bmp dest = new Bmp(W + M * 2, H + M * 2, new Bmp.Dot(Color.WHITE));
			dest.simplePaste(bmp, M, M);
			bmp = dest;
		}

		bmp = bmp.expand(W, H);
		toBW(bmp);
		antiEdge(bmp);

		TileTable tt = new TileTable(bmp, W, H, T);
		tt.tiling();

		System.out.println("brt.2");

		return bmp;
	}

	private class TileTable {
		private Bmp _bmp;
		private int _w;
		private int _h;
		private int _t;

		private int _tw;
		private int _th;
		private AutoTable<Integer> _levels;

		private final Color TILE_COLOR = Color.BLUE;

		public TileTable(Bmp bmp, int w, int h, int t) {
			_bmp = bmp;
			_w = w;
			_h = h;
			_t = t;

			_tw = w / t;
			_th = h / t;
			_levels = new AutoTable<Integer>(_tw, _th, 0);

			for(int tx = 0; tx < _tw; tx++) {
				for(int ty = 0; ty < _th; ty++) {
					if(hasBlack(tx, ty)) {
						_levels.set(tx, ty, 6);
					}
				}
			}
			propagate(6);
			propagate(5);
			propagate(4);
			propagate(3);
			propagate(2);
			propagate(1);
		}

		private boolean hasBlack(int tx, int ty) {
			for(int x = 0; x < _t; x++) {
				for(int y = 0; y < _t; y++) {
					if(_bmp.getR(tx * _t + x, ty * _t + y) < 128) {
						return true;
					}
				}
			}
			return false;
		}

		private void propagate(int target) {
			int dest = target - 1;

			for(int tx = 0; tx < _tw; tx++) {
				for(int ty = 0; ty < _th; ty++) {
					if(_levels.get(tx, ty) == target) {
						propagate(dest, tx - 1, ty    );
						propagate(dest, tx + 1, ty    );
						propagate(dest, tx,     ty - 1);
						propagate(dest, tx,     ty + 1);
					}
				}
			}
		}

		private void propagate(int dest, int tx, int ty) {
			if(
					tx < 0 || _tw <= tx ||
					ty < 0 || _th <= ty ||
					dest < _levels.get(tx, ty)
					) {
				return;
			}
			_levels.set(tx, ty, dest);
		}

		public void tiling() {
			for(int tx = 0; tx < _tw; tx++) {
				for(int ty = 0; ty < _th; ty++) {
					tiling(tx, ty);
				}
			}
			for(int x = 0; x < _w; x++) {
				tryFillExteriorTiles(x, 0);
				tryFillExteriorTiles(x, _h - 1);
			}
			for(int y = 0; y < _h; y++) {
				tryFillExteriorTiles(0, y);
				tryFillExteriorTiles(_w - 1, y);
			}
			for(int x = 0; x < _w; x++) {
				for(int y = 0; y < _h; y++) {
					if(_bmp.getDot(x, y).equals(new Bmp.Dot(TILE_COLOR))) {
						_bmp.setDot(x, y, new Bmp.Dot(Color.WHITE));
					}
				}
			}
		}

		private Xorshift _xSft = new Xorshift(1, 0, 0, 0, 100);

		private void tiling(int tx, int ty) {
			int level = _levels.get(tx, ty);
			int pct = 0;

			switch(level) {
			//case 6:
			//case 5:
			//case 4:
			case 3: pct = 25; break;
			case 2: pct = 50; break;
			case 1: pct = 75; break;
			case 0: pct = 100; break;
			/*
			case 3: pct = 7; break;
			case 2: pct = 22; break;
			case 1: pct = 66; break;
			case 0: pct = 100; break;
			*/
			}

			int r = _xSft.nextInt(100);

			if(r < pct) {
				drawTile(tx, ty, TILE_COLOR);
			}
		}

		private void drawTile(int tx, int ty, Color color) {
			new Canvas(_bmp).fillRect(tx * _t, ty * _t, _t, _t, color);
		}

		private void tryFillExteriorTiles(int x, int y) {
			if(_bmp.getDot(x, y).equals(new Bmp.Dot(TILE_COLOR))) {
				new Canvas(_bmp).fillSameColor(x, y, Color.BLACK);
			}
		}
	}
}
