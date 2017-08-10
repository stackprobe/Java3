package violet.fx;

import charlotte.tools.DateTimeToSec;

public class FxTimeData {
	private int _fxTime;

	public static FxTimeData createByStamp(long stamp) {
		long sec = DateTimeToSec.toSec(stamp);
		int fxTime = secToFxTime(sec);

		return new FxTimeData(fxTime);
	}

	public static FxTimeData createBySec(long sec) {
		int fxTime = secToFxTime(sec);

		return new FxTimeData(fxTime);
	}

	public static FxTimeData createByFxTime(int fxTime) {
		return new FxTimeData(fxTime);
	}

	public FxTimeData(int fxTime) {
		if(fxTime < 0) {
			throw new IllegalArgumentException("" + fxTime);
		}
		_fxTime = fxTime;
	}

	public int getFxTime() {
		return _fxTime;
	}

	public long getSec() {
		long sec = fxTimeToSec(_fxTime);

		return sec;
	}

	public long getStamp() {
		long sec = fxTimeToSec(_fxTime);
		long stamp = DateTimeToSec.toDateTime(sec);

		return stamp;
	}

	public static FxTimeData now() {
		return createBySec(DateTimeToSec.Now.getSec() & ~1L);
	}

	private static final long START_TIME = 0 * 86400L + 7 * 3600L +  0 * 60L; // 0-th day 07:00:00
	private static final long END_TIME   = 5 * 86400L + 5 * 3600L + 50 * 60L; // 5-th day 05:50:00
	private static final long TIME_CYCLE = 7 * 86400L + 0 * 3600L +  0 * 60L; // 7 days

	private static final long TRADING_TIME = END_TIME - START_TIME;
	//private static final long INTERVAL_TIME = TIME_CYCLE - TRADING_TIME;

	private static final long EPOCH_ZERO_FXTIME = 43951122600L;

	private static long fxTimeToSec(int fxTime) {
		if(fxTime < 0) {
			throw new IllegalArgumentException("" + fxTime);
		}
		long tmp = (long)fxTime + EPOCH_ZERO_FXTIME;

		long count = tmp / TRADING_TIME;
		long rem   = tmp % TRADING_TIME;

		return count * TIME_CYCLE + rem + START_TIME;
	}

	private static int secToFxTime(long sec) {
		if(sec < 0) {
			throw new IllegalArgumentException("" + sec);
		}
		long count = sec / TIME_CYCLE;
		long rem   = sec % TIME_CYCLE;

		if(rem < START_TIME || END_TIME <= rem) {
			throw new IllegalArgumentException("" + sec);
		}
		long tmp = count * TRADING_TIME + rem - START_TIME;
		tmp -= EPOCH_ZERO_FXTIME;

		if(tmp < 0L || (long)Integer.MAX_VALUE < tmp) {
			throw new IllegalArgumentException("" + sec);
		}
		return (int)tmp;
	}
}
