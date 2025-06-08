package com;

class QA {
 private String question;
 private boolean hasQuestion = false;

 public synchronized void ask(String q) {
     while (hasQuestion) {
         try { wait(); } catch (InterruptedException ignored) {}
     }
     question = q;
     hasQuestion = true;
     System.out.println("Student asks: " + q);
     notifyAll();
 }
 public synchronized void answer() {
     while (!hasQuestion) {
         try { wait(); } catch (InterruptedException ignored) {}
     }
     System.out.println("Teacher answers: " + question);
     hasQuestion = false;
     notifyAll();
 }
}
public class QASession {
 public static void main(String[] args) {
     QA qa = new QA();
     Thread teacher = new Thread(() -> {
         for (int i = 0; i < 3; i++) qa.answer();
     }, "Teacher");

     Thread student = new Thread(() -> {
         String[] qs = { "What is OOP?", "Explain polymorphism.", "What is a thread?" };
         for (String q : qs) qa.ask(q);
     }, "Student");

     teacher.start();
     student.start();
 }
}
