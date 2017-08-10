package violet.fx;

/**
 * Entrance
 *
 */
public class FxPrice {
	private static FxPrice _i = null;

	public static FxPrice i() {
		if(_i == null) {
			_i = new FxPrice();
		}
		return _i;
	}

	private FxPrice() {
		reload();
	}

	public void reload() {
		PriceMgr.reload();
		MvAvgMgr.clear();
	}

	public Price getPrice(FxTimeData ftd, String pair) {
		return PriceMgr.getPrice(ftd.getStamp(), pair);
	}

	public double getMvAvgValue(FxTimeData ftd, int span, String pair) {
		return MvAvgMgr.get(ftd, span, pair);
	}
}
