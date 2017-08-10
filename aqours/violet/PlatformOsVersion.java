package aqours.violet;

import java.util.ArrayList;
import java.util.List;

public class PlatformOsVersion {
	public static void main(String[] args) {
		try {
			new PlatformOsVersion().main2();
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
	}

	private void main2() {
		List<Object> root = getTree();
		printTree(root, "");
	}

	private Object[][] devices = new Object[][] {
		new Object[] { "pc", "windows", 7 },
		new Object[] { "pc", "windows", 10 },
		new Object[] { "pc", "macos", "el_capitan" },
		new Object[] { "pc", "macos", "sierra" },
		new Object[] { "pc", "ubuntu", 16.10 },
		new Object[] { "smartphone", "ios", 8 },
		new Object[] { "smartphone", "ios", 9 },
		new Object[] { "smartphone", "android", 6.0 },
		new Object[] { "smartphone", "android", 7.0 },
		new Object[] { "smartphone", "android", 7.1 },
	};

	private List<Object> getTree() {
		List<Object> root = new ArrayList<Object>();

		for(Object[] device: devices) {
			List<Object> node = root;

			for(int i = 0; i < device.length - 1; i++) {
				int c;

				for(c = 0; c < node.size(); c += 2) {
					if(node.get(c).equals(device[i])) {
						break;
					}
				}
				if(c == node.size()) {
					node.add(device[i]);
					node.add(new ArrayList<Object>());
				}
				node = (List<Object>)node.get(c + 1);
			}
			node.add(device);
		}
		return root;
	}

	private void printTree(List<Object> root, String indent) {
		for(int i = 0; i < root.size(); i++) {
			if(root.get(i) instanceof String) {
				System.out.println(indent + root.get(i) + " {");
				printTree((List<Object>)root.get(++i), indent + "  ");
				System.out.println(indent + "}");
			}
			else {
				System.out.print(indent + "[");

				for(Object key: (Object[])root.get(i)) {
					System.out.print(key + ", ");
				}
				System.out.println("]");
			}
		}
	}
}
