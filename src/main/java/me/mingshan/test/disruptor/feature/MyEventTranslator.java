package me.mingshan.test.disruptor.feature;


import com.lmax.disruptor.EventTranslator;
import me.mingshan.test.disruptor.Message;

public class MyEventTranslator implements EventTranslator<Message> {
  private Message message;

  public MyEventTranslator(Message message) {
    this.message = message;
  }

  @Override
  public void translateTo(Message event, long sequence) {

    event.setId(message.getId());
    event.setName(message.getName());
  }
}
