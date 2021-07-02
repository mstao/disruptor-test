package me.mingshan.test.disruptor;

import org.openjdk.jol.info.ClassLayout;

public class JOLDemo {
  private static Object  o;
  public static void main(String[] args) {
    o = new Object();
    synchronized (o){
      System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
  }
}
