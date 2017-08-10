package evergarden.violet.fx;

public class MvAvgMgr {
	public static double get(FxTimeData ftd, int span, String pair) {
		return MvAvgCache.i().get(span, pair).get(ftd);
	}

	public static void clear() {
		MvAvgCache.i().clear();
	}
}
