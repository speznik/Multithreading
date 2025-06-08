package com;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Account {
    private int balance = 1000;

    public synchronized boolean withdraw(int amt, String atm) {
        if (balance >= amt) {
            balance -= amt;
            System.out.printf("%s dispensed ₹%d | Left = ₹%d%n", atm, amt, balance);
            return true;
        }
        System.out.printf("%s Insufficient funds (need ₹%d, have ₹%d)%n",
                          atm, amt, balance);
        return false;
    }
}

record ATM(Account acct, int amount, String name) implements Runnable {
    @Override public void run() {
        while (acct.withdraw(amount, name)) {
            try { Thread.sleep(200); } catch (InterruptedException ignored) {}
        }
        System.out.println(name + " shutting down – no cash left.");
    }
}

public class BankSimulation {
    public static void main(String[] args) throws InterruptedException {
        Account shared = new Account();
        ExecutorService pool = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 3; i++)
            pool.submit(new ATM(shared, 100, "ATM-" + i));

        pool.shutdown();
    }
}
