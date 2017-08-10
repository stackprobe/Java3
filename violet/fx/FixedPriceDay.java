package violet.fx;

import charlotte.tools.FileTools;

public class FixedPriceDay {
	private int _date;
	private String _pair;

	public FixedPriceDay(int date, String pair) {
		_date = date;
		_pair = pair;
	}

	public String getFile() {
		return PriceDayTools.getFile("C:/var/Fx", _date, _pair);
	}

	public boolean exists() {
		return FileTools.exists(getFile());
	}

	public PriceDay get() {
		return PriceDay.createFile(getFile());
	}

	public static void executeCollect() {
		try {
			Runtime.getRuntime().exec(
					//////////////////////////////////////////////////// $_git:secret
					).waitFor();
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
	}
}
