package aqours.tshuts;

public class Main2 {
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

	private static void main2() throws Exception {
		String[] relPaths = new String[]
				{ "res/0002/MSend.txt", "res/0002/MRecv.txt" };

		for(String relPath : relPaths) {
			new Design2(relPath).perform();
		}
	}
}
