package violet.fx;

import charlotte.tools.AutoTable;
import charlotte.tools.CsvData;

public class PriceDay {
	private Price[] _prices = new Price[43200];

	public static PriceDay createFile(String file) {
		try {
			PriceDay ret = new PriceDay();
			CsvData csv = new CsvData();
			csv.readFile(file);
			AutoTable<String> table = csv.getTable();

			for(int r = 0; r < table.getHeight(); r++) {
				int c = 0;
				String sStamp = table.get(c++, r);
				String sBid = table.get(c++, r);
				String sAsk = table.get(c++, r);

				long stamp = Long.parseLong(sStamp);
				double bid = Double.parseDouble(sBid);
				double ask = Double.parseDouble(sAsk);

				int index = stampToIndex(stamp);

				ret.set(index, new Price(bid, ask));
			}

			ret.completion();

			return ret;
		}
		catch(Exception e) {
			throw new RuntimeException("raw-data file(csv) format error", e);
		}
	}

	private static int stampToIndex(long stamp) {
		int time = (int)(stamp % 1000000L);
		return timeToIndex(time);
	}

	private static int timeToIndex(int time) {
		int s = time % 100;
		time /= 100;
		int m = time % 100;
		int h = time / 100;

		if(h < 0 || 23 < h)
			throw new IllegalArgumentException("bad_h");

		if(m < 0 || 59 < m)
			throw new IllegalArgumentException("bad_m");

		if(s < 0 || 59 < s)
			throw new IllegalArgumentException("bad_s");

		if(s % 2 == 1)
			throw new IllegalArgumentException("bad_s_odd");

		int index = h * 3600 + m * 60 + s;
		index /= 2;

		// 2bs
		if(index < 0 || 43200 <= index)
			throw null;

		return index;
	}

	public static PriceDay createDummy() {
		PriceDay ret = new PriceDay();

		for(int index = 0; index < 43200; index++) {
			ret.set(index, new Price(0.0, 0.0));
		}
		return ret;
	}

	private void set(int index, Price price) {
		_prices[index] = price;
	}

	public Price get(int index) {
		return _prices[index];
	}

	public Price getByTime(int time) {
		return get(timeToIndex(time));
	}

	private void completion() {
		removeGomi();

		completionLead();
		completionTrail();
		completionMid();
	}

	private void removeGomi() {
		for(int index = 0; index + 2 < _prices.length; index++) {
			Price b = _prices[index];
			Price c = _prices[index + 1];
			Price d = _prices[index + 2];

			if(b == null && c != null && d != null) {
				_prices[index + 1] = null;
				index++;
			}
		}
	}

	private void completionLead() {
		int rIndex;

		for(rIndex = 0; _prices[rIndex] == null; rIndex++)
			;

		for(int index = 0; index < rIndex; index++) {
			_prices[index] = _prices[rIndex].clone();
		}
	}

	private void completionTrail() {
		int rIndex;

		for(rIndex = _prices.length - 1; _prices[rIndex] == null; rIndex--)
			;

		for(int index = _prices.length - 1; rIndex < index; index--) {
			_prices[index] = _prices[rIndex].clone();
		}
	}

	private void completionMid() {
		for(int rIndex = 1; rIndex + 1 < _prices.length; rIndex++) {
			if(_prices[rIndex] == null) {
				int l = rIndex - 1;

				while(_prices[++rIndex] == null)
					;

				int r = rIndex;
				int d = r - l;

				for(int index = 1; index < d; index++) {
					_prices[l + index] = new Price(
							_prices[l].getBid() + (_prices[r].getBid() - _prices[l].getBid()) * ((double)index / d),
							_prices[l].getAsk() + (_prices[r].getAsk() - _prices[l].getAsk()) * ((double)index / d)
							);
				}
			}
		}
	}
}
