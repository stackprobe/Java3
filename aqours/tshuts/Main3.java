package aqours.tshuts;

public class Main3 {
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

	public static void main2() throws Exception {
		String[] relPaths = new String[]
				{ "res/0003/NSend.txt", "res/0003/NRecv.txt" };

		for(String relPath : relPaths) {
			new Design3(relPath).perform();
		}
	}
}
