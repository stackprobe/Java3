package evergarden.violet.fx;

public class Price {
	private double _bid;
	private double _ask;

	public Price(double bid, double ask) {
		_bid = bid;
		_ask = ask;
	}

	public Price clone() {
		return new Price(_bid, _ask);
	}

	public double getBid() {
		return _bid;
	}

	public double getAsk() {
		return _ask;
	}

	public double getMid() {
		return (_bid + _ask) / 2.0;
	}

	public double getSpread() {
		return _bid - _ask;
	}

	@Override
	public String toString() {
		return "{" + _bid + ", " + _ask + "}";
	}
}
