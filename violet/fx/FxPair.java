package violet.fx;

public class FxPair {
	private String _pair;

	public FxPair(String pair) {
		_pair = pair;
	}

	public Price getPrice(FxTimeData ftd) {
		return FxPrice.i().getPrice(ftd, _pair);
	}

	public double getMvAvgValue(FxTimeData ftd, int span) {
		return FxPrice.i().getMvAvgValue(ftd, span, _pair);
	}

	public MvAvg getMvAvg(int span) {
		return new MvAvg(span);
	}

	public class MvAvg {
		private int _span;

		public MvAvg(int span) {
			_span = span;
		}

		public int getSpan() {
			return _span;
		}

		public double getValue(FxTimeData ftd) {
			return FxPrice.i().getMvAvgValue(ftd, _span, _pair);
		}
	}
}
