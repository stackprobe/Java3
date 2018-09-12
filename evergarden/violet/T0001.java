package evergarden.violet;

import javax.swing.JOptionPane;

public class T0001 {
	public static void main(String[] args) {
		System.out.println("3 is prime: " + isPrime(3));
	}

	private static boolean isPrime(int n) {
		return JOptionPane.showConfirmDialog(
				null,
				n + " is prime ?",
				"Q",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE
				)
				== JOptionPane.YES_OPTION;
	}
}
