package com;

import java.time.LocalTime;

public class Clock {
 public static void main(String[] args) {
     Thread clock = new Thread(() -> {
         while (true) {
             System.out.print("\r" + LocalTime.now().withNano(0));
             try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
         }
     });
     clock.setDaemon(true);
     clock.start();

     try { Thread.sleep(30000); } catch (InterruptedException ignored) {}
 }
}
