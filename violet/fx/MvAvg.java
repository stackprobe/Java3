package violet.fx;

public class MvAvg {
	private int _span;
	private String _pair;
	private int _currFxTime;
	private double _currTotal;

	public MvAvg(FxTimeData ftd, int span, String pair) {
		if(span < 2 || span % 2 == 1) {
			throw new IllegalArgumentException();
		}
		_span = span;
		_pair = pair;
		_currFxTime = ftd.getFxTime();

		if(_currFxTime < span || _currFxTime % 2 == 1) {
			throw new IllegalArgumentException("" + _currFxTime);
		}
		reload();
	}

	private void reload() {
		_currTotal = 0.0;

		for(int count = 0; count < _span; count += 2) {
			_currTotal += getPriceValue(_currFxTime - count);
		}
	}

	private double getPriceValue(int fxTime) {
		return FxPrice.i().getPrice(FxTimeData.createByFxTime(fxTime), _pair).getMid();
	}

	public double get(FxTimeData ftd) {
		int fxTime = ftd.getFxTime();

		if(fxTime < _span || fxTime % 2 == 1) {
			throw new IllegalArgumentException();
		}
		if(fxTime <= _currFxTime - _span || _currFxTime + _span <= fxTime) {
			_currFxTime = fxTime;
			reload();
		}
		else {
			while(fxTime < _currFxTime) {
				_currTotal -= getPriceValue(_currFxTime);
				_currTotal += getPriceValue(_currFxTime - _span);
				_currFxTime -= 2;
			}
			while(_currFxTime < fxTime) {
				_currFxTime += 2;
				_currTotal -= getPriceValue(_currFxTime - _span);
				_currTotal += getPriceValue(_currFxTime);
			}
		}
		return _currTotal / (_span / 2);
	}
}
