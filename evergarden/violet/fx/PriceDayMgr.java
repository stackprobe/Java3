package evergarden.violet.fx;

public class PriceDayMgr {
	private static PriceDay _dummyPriceDay = PriceDay.createDummy();

	public static PriceDay get(int date, String pair) {
		{
			FixedPriceDay fpd = new FixedPriceDay(date, pair);

			if(fpd.exists()) {
				return fpd.get();
			}
		}

		{
			SnapshotPriceDay spd = new SnapshotPriceDay(date, pair);

			if(spd.exists()) {
				return spd.get();
			}
		}

		return _dummyPriceDay;
	}
}
