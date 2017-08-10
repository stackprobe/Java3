package aqours.animalmap;

import java.util.Comparator;

import charlotte.tools.ObjectMap;
import charlotte.tools.StringTools;

public class Item {
	public String gcmName;
	public String id;
	public String layerName;
	public int status;

	public Item(ObjectMap src) {
		gcmName = src.getString("gcmName");
		id = src.getString("id");
		layerName = src.getString("layerName");
		status = Integer.parseInt(src.getString("status"));
	}

	@Override
	public String toString() {
		return "[gcmName=" + gcmName + ",id=" + id + ",layerName=" + layerName + ",status=" + status + "]";
	}

	public static Comparator<Item> comp = new Comparator<Item>() {
		@Override
		public int compare(Item a, Item b) {
			return StringTools.comp.compare(a.toString(), b.toString());
		}
	};
}
