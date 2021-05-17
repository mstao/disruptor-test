package me.mingshan.test.disruptor.feature;

import com.lmax.disruptor.EventHandler;
import me.mingshan.test.disruptor.Message;

public class MyEventHandler implements EventHandler<Message> {
  private String name;

  public MyEventHandler(String name) {
    this.name = name;
  }

  @Override
  public void onEvent(Message event, long sequence, boolean endOfBatch) throws Exception {
    System.out.println("name : " + name + ", event : " + event);
  }
}
