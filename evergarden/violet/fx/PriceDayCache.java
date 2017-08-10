package evergarden.violet.fx;

import java.util.Map;

import charlotte.tools.MapTools;
import charlotte.tools.QueueData;

public class PriceDayCache {
	private static PriceDayCache _i = null;

	public static PriceDayCache i() {
		if(_i == null) {
			_i = new PriceDayCache();
		}
		return _i;
	}

	private PriceDayCache() {
		// noop
	}

	private static final int CACHE_SIZE = 100;

	private static Map<String, PriceDay> _cache = MapTools.<PriceDay>create();
	private static QueueData<String> _keys = new QueueData<String>();

	private static String getKey(int date, String pair) {
		return date + "_" + pair;
	}

	public PriceDay get(int date, String pair) {
		PriceDay ret = _cache.get(getKey(date, pair));

		if(ret == null) {
			ret = PriceDayMgr.get(date, pair);

			if(CACHE_SIZE <= _keys.size()) {
				_cache.remove(_keys.poll());
			}
			_cache.put(getKey(date, pair), ret);
			_keys.add(getKey(date, pair));
		}
		return ret;
	}

	public void clear() {
		_cache.clear();
		_keys.clear();
	}
}
