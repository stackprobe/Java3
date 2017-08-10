package violet.fx;

public class PriceMgr {
	public static void reload() {
		FixedPriceDay.executeCollect();
		SnapshotPriceDay.executeSnapshot();

		PriceDayCache.i().clear();
	}

	public static Price getPrice(long stamp, String pair) {
		int date = (int)(stamp / 1000000L);
		int time = (int)(stamp % 1000000L);

		return PriceDayCache.i().get(date, pair).getByTime(time);
	}
}
