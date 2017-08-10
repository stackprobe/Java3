package evergarden.violet.fx;

import charlotte.tools.FileTools;

public class PriceDayTools {
	public static String getFile(String dir, int date, String pair) {
		return FileTools.combine(dir, date + "_" + pair + ".csv");
	}
}
