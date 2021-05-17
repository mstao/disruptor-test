package me.mingshan.test.disruptor.mulit;

import com.lmax.disruptor.WorkHandler;
import me.mingshan.test.disruptor.Message;

public class Consumer implements WorkHandler<Message> {
  private String id;

  public Consumer(String id) {
    this.id = id;
  }

  @Override
  public void onEvent(Message event) throws Exception {
    System.out.println("id : " + id + ", event : " + event);
  }
}
