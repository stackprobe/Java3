package aqours.violet;

import java.util.ArrayList;
import java.util.List;

import charlotte.tools.ArrayTools;
import charlotte.tools.CsvData;
import charlotte.tools.FileTools;
import charlotte.tools.StringTools;
import charlotte.tools.XNode;

public class Config20180912 {
	public static void main(String[] args) {
		try {
			new Config20180912().main2();

			System.out.println("OK!");
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
	}

	private List<String> _keyOrder;

	private void main2() throws Exception {
		_keyOrder = FileTools.readAllLines("C:/wb/20180912_config/app_config_key_order.txt", StringTools.CHARSET_SJIS);

		List<String[]> kvs1 = getKvs("C:/wb/20180912_config/xxxxdev01_e_axxxxxa_dev_Axxxxxxxxs.exe.config");
		List<String[]> kvs2 = getKvs("C:/wb/20180912_config/xxxxdev01_e_axxxxxa_rtn_Axxxxxxxxs.exe.config");

		CsvData.Stream writer = new CsvData.Stream("C:/temp/config_20180912.csv");
		writer.writeOpen();
		try {
			for(int index = 0; index < _keyOrder.size(); index++) {
				writer.writeCell(_keyOrder.get(index));
				writer.writeDelimiter();
				writer.writeCell(kvs1.get(index)[0]);
				writer.writeDelimiter();
				writer.writeCell(kvs1.get(index)[1]);
				writer.writeDelimiter();
				writer.writeCell(kvs2.get(index)[0]);
				writer.writeDelimiter();
				writer.writeCell(kvs2.get(index)[1]);
				writer.writeReturn();
			}
		}
		finally {
			writer.writeClose();
			writer = null;
		}
	}

	private List<String[]> getKvs(String file) throws Exception {
		List<String[]> dest = new ArrayList<String[]>();

		for(int i = 0; i < _keyOrder.size(); i++) {
			dest.add(new String[] { "", "" });
		}

		XNode root = XNode.load(file);

		for(XNode settingNode : root.getNodes("applicationSettings/Intage.Alterna.AltProcess.Properties.Settings/setting")) {
			String name = settingNode.getNodeValue("name");
			String value = settingNode.getNodeValue("value");

			int i = ArrayTools.indexOf(_keyOrder, name, StringTools.comp);

			if(i == -1) {
				throw new Exception("name: " + name);
			}
			dest.get(i)[0] = "○";
			//dest.get(i)[0] = name;
			dest.get(i)[1] = value;
		}
		return dest;
	}
}
