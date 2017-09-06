package aqours.tshuts;

public class Main {
	public static void main(String[] args) {
		try {
			main2();

			System.out.println("OK!");
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	private static void main2() {
		String[] relPaths = new String[]
				{ "res/0001/front.txt", "res/0001/ushiro.txt" };

		for(String relPath : relPaths) {
			new Design(relPath).perform();
		}
	}
}
