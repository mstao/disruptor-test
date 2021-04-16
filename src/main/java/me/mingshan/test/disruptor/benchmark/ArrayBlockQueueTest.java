package me.mingshan.test.disruptor.benchmark;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author Walker Han
 * @date 2021/4/15 13:16
 */
public class ArrayBlockQueueTest {
  public static void main(String[] args) throws InterruptedException {
    test(10000000);
  }

  public static void test(int capacity) throws InterruptedException {
    ArrayBlockingQueue<Element> queue = new ArrayBlockingQueue<>(capacity);
    long startTime = System.currentTimeMillis();

    new Thread(() -> {
      for (int i = 1 ; i <= capacity; i++) {
        try {
          queue.put(new Element(i));
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      queue.poll();
    }).start();

    new Thread(() -> {
      int i = 1;
      while (i < capacity) {
        try {
          queue.take();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        i++;
      }

      long endTime = System.currentTimeMillis();
      System.out.println("耗时：" + (endTime - startTime));
    }).start();
  }
}
