package me.mingshan.test.disruptor.benchmark;

public class DeadlockDemo1 {

  private static Object object1 = new Object();
  private static Object object2 = new Object();

  public static void main(String[] args) {
    new Thread(() -> {

      synchronized (object1) {
        System.out.println("t1 get lock1 ");


        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        synchronized (object2) {
          System.out.println("t1 get lock2");
        }
      }

    }).start();


    new Thread(() -> {

      synchronized (object2) {
        System.out.println("t2 get lock2");

        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        synchronized (object1) {
          System.out.println("t2 get lock1");
        }
      }

    }).start();
  }

}
