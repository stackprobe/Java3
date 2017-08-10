package aqours.animalmap;

import java.util.ArrayList;
import java.util.List;

import charlotte.tools.ArrayTools;
import charlotte.tools.FileTools;
import charlotte.tools.HTTPClient;
import charlotte.tools.HTTPServer;
import charlotte.tools.JsonTools;
import charlotte.tools.JsonWord;
import charlotte.tools.MathTools;
import charlotte.tools.ObjectList;
import charlotte.tools.ObjectMap;
import charlotte.tools.StringTools;

public class Client {
	//public static int serverPort = 8080;
	public static int serverPort = 8081;

	public static HTTPClient createHTTPClient(String url) {
		HTTPClient hc = new HTTPClient(url);

		hc.setConnectTimeoutMillis(20000);
		hc.setReadTimeoutMillis(3600000);

		return hc;
	}

	public static List<Item> getList() throws Exception {
		List<Item> dest = new ArrayList<Item>();

		for(int page = 1; ; page++) {
			HTTPClient hc = createHTTPClient(
					"http://localhost:" + serverPort + "/Contribution/Contribution?cmd=GetContributionList&mapId=62&id=&name=&status=&dateStart=&dateEnd=&_search=false&nd=1469690469234&rows=30&page=" +
					page +
					"&sidx=id&sord=asc"
					);
			hc.get();
			ObjectMap root = (ObjectMap)JsonTools.decode(hc.getResBody());

			if(root.getString("page").equals("" + page) == false) {
				break;
			}
			for(Object rowObj : root.getList("rows").asList()) {
				ObjectMap row = (ObjectMap)rowObj;

				dest.add(new Item(row));
			}
		}
		return dest;
	}

	public static void appry(List<Item> items) throws Exception {
		ObjectList root = new ObjectList();

		for(Item item : items) {
			ObjectMap row = new ObjectMap();

			row.add("mapId", "62");
			row.add("gcmName", item.gcmName);
			row.add("layerName", item.layerName);
			row.add("id", item.id);
			row.add("status", new JsonWord("" + item.status));

			root.add(row);
		}
		String body = "mapId=62&appryInfoList=" + JsonTools.encode(root);
		byte[] bBody = body.getBytes(StringTools.CHARSET_UTF8);

		HTTPClient hc = createHTTPClient(
				"http://localhost:" + serverPort + "/Contribution/Contribution?cmd=Appry"
				);
		hc.setHeaderField("Content-Type", "application/x-www-form-urlencoded");
		hc.post(bBody);

		ObjectMap resRoot = (ObjectMap)JsonTools.decode(hc.getResBody());

		if(resRoot.getString("status").equals("OK") == false) {
			throw new Exception(resRoot.getString("msg"));
		}
	}

	public static List<Item> getList2() throws Exception {
		List<Item> dest = new ArrayList<Item>();

		for(int page = 1; ; page++) {
			HTTPClient hc = createHTTPClient(
					"http://localhost:" + serverPort + "/Contribution/Contribution?cmd=GetContributionList&mapId=62&id=&name=&status=1%2C2&dateStart=&dateEnd=&_search=false&nd=1469690503646&rows=30&page=" +
					page +
					"&sidx=id&sord=asc"
					);
			hc.get();
			ObjectMap root = (ObjectMap)JsonTools.decode(hc.getResBody());

			if(root.getString("page").equals("" + page) == false) {
				break;
			}
			for(Object rowObj : root.getList("rows").asList()) {
				ObjectMap row = (ObjectMap)rowObj;

				dest.add(new Item(row));
			}
		}
		return dest;
	}

	public static void updateStatus(List<Item> items) throws Exception {
		ObjectList root = new ObjectList();

		for(Item item : items) {
			ObjectMap row = new ObjectMap();

			row.add("mapId", "62");
			row.add("gcmName", item.gcmName);
			row.add("layerName", item.layerName);
			row.add("id", item.id);
			row.add("status", new JsonWord("" + item.status));

			root.add(row);
		}
		String body = "mapId=62-" + (Integer.parseInt(getMapIdSerial("62")) + 1) + "&approveInfoList=" + JsonTools.encode(root);
		body = HTTPServer.encodeUrl(body, StringTools.CHARSET_UTF8);
		byte[] bBody = body.getBytes(StringTools.CHARSET_UTF8);

		{
			HTTPClient hc = createHTTPClient(
					"http://localhost:" + serverPort + "/Contribution/Contribution?cmd=UpdateStatus"
					);
			hc.setHeaderField("Content-Type", "application/x-www-form-urlencoded");
			hc.post(bBody);

			ObjectMap resRoot = (ObjectMap)JsonTools.decode(hc.getResBody());

			if(resRoot.getString("status").equals("OK") == false) {
				throw new Exception(resRoot.getString("msg"));
			}
		}

		{
			HTTPClient hc = createHTTPClient(
					"http://localhost:" + serverPort + "/Contribution/Contribution?cmd=Copymap"
					);
			hc.setHeaderField("Content-Type", "application/x-www-form-urlencoded");
			hc.post(bBody);

			ObjectMap resRoot = (ObjectMap)JsonTools.decode(hc.getResBody());

			if(resRoot.getString("status").equals("OK") == false) {
				throw new Exception(resRoot.getString("msg"));
			}
		}
	}

	private static String getMapIdSerial(String mapId) throws Exception {
		HTTPClient hc = createHTTPClient(
				"http://localhost:" + serverPort + "/webgis/LayerInfo/Map/List"
				);
		hc.get();
		ObjectMap root = (ObjectMap)JsonTools.decode(hc.getResBody());
		ObjectMap rows = (ObjectMap)root.get("ret");

		for(String key : rows.keySet()) {
			if(key.startsWith(mapId + "-")) {
				return key.substring(key.indexOf('-') + 1);
			}
		}

		throw null; // ne-yo!
	}

	public static void main(String[] args) {
		try {
			//test01();
			test02();
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
	}

	private static void test01() throws Exception {
		for(Item item : getList()) {
			System.out.println("item: " + item);
		}
	}

	private static void test02() throws Exception {
		List<Item> masterItems = getList();

		for(int testCnt = 1; ; testCnt++) {
			System.out.println("testCnt: " + testCnt);

			List<Item> items = getList();

			ArrayTools.sort(items, Item.comp);
			ArrayTools.sort(masterItems, Item.comp);

			if(ArrayTools.isSame(items, masterItems, Item.comp) == false) {
				writeLines("C:/temp/1.txt", items);
				writeLines("C:/temp/2.txt", masterItems);
				throw null;
			}
			List<Item> sItems = new ArrayList<Item>();
			List<Item> ssItems = new ArrayList<Item>();

			for(Item item : masterItems) {
				switch(item.status) {
				case 0:
				case 3:
				case 4:
					sItems.add(item);
					break;

				case 1:
				case 2:
					ssItems.add(item);
					break;

				default:
					throw null;
				}
			}
			//sItems = MathTools.randomSubList(sItems);
			sItems = MathTools.randomSubList(sItems, MathTools.random(3)); // FIXME

			for(Item item : sItems) {
				switch(item.status) {
				case 0:
					item.status = MathTools.random(1, 2);
					break;

				case 3:
					item.status = 2;
					break;

				case 4:
					item.status = 1;
					break;

				default:
					throw null;
				}
				ssItems.add(item);
			}
			appry(sItems);

			items = getList2();

			ArrayTools.sort(items, Item.comp);
			ArrayTools.sort(ssItems, Item.comp);

			if(ArrayTools.isSame(items, ssItems, Item.comp) == false) {
				writeLines("C:/temp/1.txt", items);
				writeLines("C:/temp/2.txt", ssItems);
				throw null;
			}
			sItems.clear();

			for(Item item : ssItems) {
				switch(MathTools.random(3)) {
				case 0:
					switch(item.status) {
					case 1: item.status = 3; break;
					case 2: item.status = 4; break;

					default:
						throw null;
					}
					sItems.add(item);
					break;

				case 1:
					item.status = 0;
					sItems.add(item);
					break;

				case 2:
					break;

				default:
					throw null;
				}
			}
			updateStatus(sItems);
		}
	}

	private static void writeLines(String file, List<Item> items) throws Exception {
		List<String> lines = new ArrayList<String>();

		for(Item item : items) {
			lines.add(item.toString());
		}
		FileTools.writeAllLines(file, lines, StringTools.CHARSET_UTF8);
	}
}
