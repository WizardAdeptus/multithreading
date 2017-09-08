package info.multithreading.twoways;

public class MultiImpl implements Runnable {

	@Override
	public void run() {
		System.out.println("thread running...");
	}

	public static void main(String[] args) {
		MultiImpl m1 = new MultiImpl();
		Thread t1 = new Thread(m1);
		t1.start();
	}
}
