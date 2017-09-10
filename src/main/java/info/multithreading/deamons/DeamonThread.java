package info.multithreading.deamons;

public class DeamonThread extends Thread {

    public void run() {
        // checking for daemon thread
        if (Thread.currentThread().isDaemon()) {
            System.out.println("daemon thread work");
        } else {
            System.out.println("user thread work");
        }
    }

    public static void main(String[] args) {
        // creating thread
        DeamonThread t1 = new DeamonThread();
        DeamonThread t2 = new DeamonThread();
        DeamonThread t3 = new DeamonThread();

        // now t1 is daemon thread
        t1.setDaemon(true);

        // starting threads
        t1.start();
        t2.start();
        t3.start();
        // will throw IllegalThreadStateException here
        // t3.setDaemon(true);
    }
}
