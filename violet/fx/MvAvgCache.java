package violet.fx;

import java.util.Map;

import charlotte.tools.MapTools;
import charlotte.tools.QueueData;

public class MvAvgCache {
	private static MvAvgCache _i = null;

	public static MvAvgCache i() {
		if(_i == null) {
			_i = new MvAvgCache();
		}
		return _i;
	}

	private MvAvgCache() {
		// noop
	}

	private static final int CACHE_SIZE = 20;

	private static Map<String, MvAvg> _cache = MapTools.<MvAvg>create();
	private static QueueData<String> _keys = new QueueData<String>();

	private static String getKey(int span, String pair) {
		return span + "_" + pair;
	}

	public MvAvg get(int span, String pair) {
		MvAvg ret = _cache.get(getKey(span, pair));

		if(ret == null) {
			ret = new MvAvg(FxTimeData.now(), span, pair);

			if(CACHE_SIZE <= _keys.size()) {
				_cache.remove(_keys.poll());
			}
			_cache.put(getKey(span, pair), ret);
			_keys.add(getKey(span, pair));
		}
		return ret;
	}

	public void clear() {
		_cache.clear();
		_keys.clear();
	}
}
