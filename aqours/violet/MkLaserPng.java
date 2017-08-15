package aqours.violet;

import charlotte.tools.Bmp;
import charlotte.tools.IntTools;
import charlotte.tools.XYPoint;

public class MkLaserPng {
	public static void main(String[] args) {
		try {
			main2();
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
	}

	private static final int BMP_WH = 1000;
	private static final double C_O = 490.0;
	private static final double C_I = 440.0;
	private static final double A_O = 400.0;
	private static final double A_I = 300.0;

	private static void main2() throws Exception {
		Bmp bmp = new Bmp(140, 256);

		putLaser(bmp, 0, 0, 1.0, 0.0, 0.0);
		putLaser(bmp, 20, 0, 1.0, 1.0, 0.0);
		putLaser(bmp, 40, 0, 0.0, 1.0, 0.0);
		putLaser(bmp, 60, 0, 0.0, 1.0, 1.0);
		putLaser(bmp, 80, 0, 0.0, 0.0, 1.0);
		putLaser(bmp, 100, 0, 1.0, 0.0, 1.0);
		putLaser(bmp, 120, 0, 0.5, 0.5, 0.5);

		bmp.writeToFile("C:/Dev/DxLibTest/LaserTest/Resource/lazer.png");
	}

	private static void putLaser(Bmp dest, int l, int t, double cr, double cg, double cb) {
		Bmp bmp = new Bmp(BMP_WH, BMP_WH);

		for(int x = 0; x < BMP_WH; x++) {
			for(int y = 0; y < BMP_WH; y++) {
				double d = new XYPoint(x, y).getDistance(new XYPoint((BMP_WH - 1) / 2.0, (BMP_WH - 1) / 2.0));
				double a;
				double r;
				double g;
				double b;

				if(d < A_I) {
					a = 1.0;
					r = 1.0;
					g = 1.0;
					b = 1.0;
				}
				else if(d < A_O) {
					d -= A_O;
					d /= A_I - A_O;

					a = 1.0;
					r = cr * (1.0 - d) + 1.0 * d;
					g = cg * (1.0 - d) + 1.0 * d;
					b = cb * (1.0 - d) + 1.0 * d;
				}
				else if(d < C_I) {
					a = 1.0;
					r = cr;
					g = cg;
					b = cb;
				}
				else if(d < C_O) {
					d -= C_O;
					d /= C_I - C_O;

					a = d;
					r = cr;
					g = cg;
					b = cb;
				}
				else {
					a = 0.0;
					r = cr;
					g = cg;
					b = cb;
				}
				bmp.setDot(x, y, new Bmp.Dot(
						IntTools.toInt(a * 255.0),
						IntTools.toInt(r * 255.0),
						IntTools.toInt(g * 255.0),
						IntTools.toInt(b * 255.0)
						));
			}
		}
		bmp = bmp.expand(20, 256 + 20);
		bmp = bmp.cut(0, 10, 20, 256);
		dest.simplePaste(bmp, l, t);
	}
}
