package aqours.tshuts;

public class AllRun {
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
		Main.main2();
		Main2.main2();
		Main3.main2();
	}
}
