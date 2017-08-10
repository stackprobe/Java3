package violet.simu;

import java.io.OutputStreamWriter;

import charlotte.tools.FileTools;
import charlotte.tools.StringTools;
import violet.fx.FxPair;
import violet.fx.FxTimeData;

public class Simu01 {
	public static void main(String[] args) {
		try {
			new Simu01().main2();
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	private void main2() throws Exception {
		FileTools.del(REPORT_FILE);

		_startTime = FxTimeData.createByStamp(20150901000000L);
		_endTime   = FxTimeData.createByStamp(20160802000000L);

		_fxPair = new FxPair("USDJPY");

		for(int
				centerSpan = 2;
				centerSpan <= 2097152;
				centerSpan *= 2
				) {
			for(int
					bidSpan = 2;
					bidSpan < centerSpan / 2;
					bidSpan *= 2
					) {
				for(int
						trackSpan = 2;
						trackSpan < bidSpan && trackSpan <= 64;
						trackSpan *= 2
						) {
					for(
							_bidPermilDiff = 1;
							_bidPermilDiff <= 4;
							_bidPermilDiff++
							) {
						_centerMa = _fxPair.getMvAvg(centerSpan);
						_bidMa    = _fxPair.getMvAvg(bidSpan);
						_trackMa  = _fxPair.getMvAvg(trackSpan);

						go();
					}
				}
			}
		}
	}

	private static final String REPORT_FILE = "C:/temp/Simu01.csv";

	private FxTimeData _startTime;
	private FxTimeData _endTime;

	private FxPair _fxPair;
	private FxPair.MvAvg _centerMa;
	private FxPair.MvAvg _bidMa;
	private FxPair.MvAvg _trackMa;
	private int _bidPermilDiff;

	private double _bidRate;

	private double _winPriceValueRate;
	private double _winRate;
	private long _winCount;
	private long _loseCount;

	private void go() throws Exception {
		_bidRate = (_bidPermilDiff + 1000) / 1000.0;

		//System.out.println("_bidRate: " + _bidRate);

		_winPriceValueRate = 1.0;
		_winRate = 1.0;
		_winCount = 0L;
		_loseCount = 0L;

		int mode = 0;
		double lastBidPriceValue = -1.0;
		double lastBidTrackMa = -1.0;

		for(int fxTime = _startTime.getFxTime(); fxTime <= _endTime.getFxTime(); fxTime += 2) {
			FxTimeData ftd = FxTimeData.createByFxTime(fxTime);

			//System.out.println(ftd.getStamp());

			double centerMa = _centerMa.getValue(ftd);
			double bidMa    = _bidMa.getValue(ftd);
			double trackMa  = _trackMa.getValue(ftd);

			switch(mode) {
			case 0:
				if(_bidRate < centerMa / trackMa && trackMa < bidMa) {
					mode = 1;
				}
				break;
			case 1:
				if(bidMa < trackMa) {
					lastBidPriceValue = _fxPair.getPrice(ftd).getMid();
					lastBidTrackMa = trackMa;
					mode = 2;
				}
				break;
			case 2:
			{
				boolean doAsk = false;

				{
					double a = centerMa / lastBidTrackMa;
					double v = 1.0 / a;
					double lossCurPriceValue = lastBidTrackMa * v;

					if(centerMa < trackMa) {
						doAsk = true;
					}
					if(trackMa < lossCurPriceValue) {
						doAsk = true;
					}
				}

				if(doAsk) {
					double askPriceValue = _fxPair.getPrice(ftd).getMid();
					double a = askPriceValue / lastBidPriceValue;

					_winPriceValueRate *= a;

					if(1.0 < a) {
						_winCount++;
					}
					else {
						_loseCount++;
					}
					mode = 0;
				}
				break;
			}
			}
		}

		if(_winCount == 0L) {
			_winRate = 0.0;
		}
		else {
			_winRate = (double)_winCount / (_winCount + _loseCount);
		}

		String report =
				_centerMa.getSpan() +
				"," +
				_bidMa.getSpan() +
				"," +
				_trackMa.getSpan() +
				"," +
				_bidRate +
				"," +
				_winPriceValueRate +
				"," +
				_winRate +
				"," +
				_winCount +
				"," +
				_loseCount;

		System.out.println(report);

		{
			OutputStreamWriter writer = null;
			try {
				writer = FileTools.writeOpenTextFile(REPORT_FILE, StringTools.CHARSET_SJIS, true);
				FileTools.writeLine(writer, report);
			}
			finally {
				FileTools.close(writer);
			}
		}
	}
}
