package me.mingshan.test.disruptor.benchmark;

/**
 * @author Walker Han
 * @date 2021/4/15 14:49
 */
public class Element {

  private int value;

  public Element(int value) {
    this.value = value;
  }

  public int get(){
    return value;
  }

  public void set(int value){
    this.value= value;
  }

}
