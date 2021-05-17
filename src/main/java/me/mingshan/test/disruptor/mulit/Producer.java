package me.mingshan.test.disruptor.mulit;

import com.lmax.disruptor.RingBuffer;
import me.mingshan.test.disruptor.Message;

public class Producer {
  private RingBuffer<Message> ringBuffer;

  public Producer(RingBuffer<Message> ringBuffer) {
    this.ringBuffer = ringBuffer;
  }

  public void sendData(Message message) {
    // 获取下一个可用位置的下标
    long sequence = ringBuffer.next();
    try {
      // 返回可用位置的元素
      Message event = ringBuffer.get(sequence);
      // 设置该位置元素的值
      event.setId(message.getId());
      event.setName(message.getName());
    } finally {
      ringBuffer.publish(sequence);
    }
  }
}
