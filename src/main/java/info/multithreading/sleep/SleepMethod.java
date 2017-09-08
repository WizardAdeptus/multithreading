package info.multithreading.sleep;

public class SleepMethod extends Thread {
	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(500 );
			} catch (InterruptedException e) {
				System.out.println(e);
			}
			System.out.println(i);
		}
	}

	public static void main(String[] args) {
		SleepMethod t1 = new SleepMethod();
		SleepMethod t2 = new SleepMethod();

		t1.start();
		t2.start();
	}
}
