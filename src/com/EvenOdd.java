package com;

public class EvenOdd {
    private static final Object LOCK = new Object();
    private static int num = 1;
    public static void main(String[] args) {
        Runnable printOdd  = () -> runPrinter(false);
        Runnable printEven = () -> runPrinter(true);

        new Thread(printOdd , "Odd-Thread").start();
        new Thread(printEven, "Even-Thread").start();
    }
    private static void runPrinter(boolean even) {
        while (num <= 50) {
            synchronized (LOCK) {
                if ((num % 2 == 0) == even) {
                    System.out.printf("%s → %d%n", Thread.currentThread().getName(), num++);
                    LOCK.notifyAll();
                } else {
                    try { LOCK.wait(); } catch (InterruptedException ignored) {}
                }
            }
        }
    }
}
