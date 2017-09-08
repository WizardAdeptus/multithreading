package info.multithreading.twoways;

public class MultiExt extends Thread {
	@Override
	public void run() {
		System.out.println("thread running...");
	}

	public static void main(String[] args) {
		MultiExt t1 = new MultiExt();
		t1.start();
	}
}
