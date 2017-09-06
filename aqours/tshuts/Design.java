package aqours.tshuts;

import java.util.List;

import charlotte.tools.FileTools;
import charlotte.tools.RunnableEx;
import charlotte.tools.StringTools;
import charlotte.tools.WorkDir;

public class Design {
	private List<String> _lines;

	public Design(String relPath) {
		try {
			try(WorkDir wd = new WorkDir()) {
				String file = wd.makeSubPath();
				FileTools.writeAllBytes(file, FileTools.readToEnd(Design.class.getResource(relPath)));
				_lines = FileTools.readAllLines(file, StringTools.CHARSET_ASCII);
			}
		}
		catch(Throwable e) {
			throw RunnableEx.re(e);
		}
	}

	public void perform() {
		// TODO
	}
}
