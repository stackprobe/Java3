package violet_test.fx;

import java.util.ArrayList;
import java.util.List;

import charlotte.tools.FileTools;
import charlotte.tools.StringTools;
import violet.fx.PriceDay;

public class PriceDayTest {
	public static void main(String[] args) {
		try {
			test01();

			System.out.println("OK!");
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
	}

	private static void test01() throws Exception {
		PriceDay pd = PriceDay.createFile("C:/var/PriceDayTest.csv");
		List<String> dest = new ArrayList<String>();

		for(int index = 0; index < 43200; index++) {
			dest.add(index + ": " + pd.get(index));
		}
		FileTools.writeAllLines("C:/temp/PriceDayTestOut.txt", dest, StringTools.CHARSET_SJIS);
	}
}
