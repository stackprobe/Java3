package aqours.tshuts;

public class Design {
	public static void main(String[] args) {
		try {
			main2();
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
	}

	private static void main2() {
		new Design().perform("res/0001/front.txt");
		new Design().perform("res/0001/ushiro.txt");
	}

	private void perform(String resPath) {
		// TODO
	}
}
