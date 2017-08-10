package evergarden.violet.fx;

import charlotte.tools.FileTools;
import charlotte.tools.WorkDir;

public class SnapshotPriceDay {
	private int _date;
	private String _pair;

	public SnapshotPriceDay(int date, String pair) {
		_date = date;
		_pair = pair;
	}

	public String getFile() {
		return PriceDayTools.getFile(_destDir.getDir(), _date, _pair);
	}

	public boolean exists() {
		return FileTools.exists(getFile());
	}

	public PriceDay get() {
		return PriceDay.createFile(getFile());
	}

	private static WorkDir _destDir = new WorkDir("{942cd339-3642-4687-a991-3c6bf3964ea3}");

	public static void executeSnapshot() {
		try {
			Runtime.getRuntime().exec("C:/Factory/SubTools/nrun.exe /s mimiko Fx/snapshot").waitFor();

			for(String file : FileTools.ls(_destDir.getDir())) {
				FileTools.del(file);
			}
			for(String rFile : FileTools.ls("S:/Fx/Snapshot")) {
				String lFile = FileTools.getLocal(rFile);
				String wFile = FileTools.combine(_destDir.getDir(), lFile);

				FileTools.moveFile(rFile, wFile);
			}
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
	}
}
