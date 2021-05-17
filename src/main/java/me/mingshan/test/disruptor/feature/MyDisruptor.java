package me.mingshan.test.disruptor.feature;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import me.mingshan.test.disruptor.Message;

import java.util.concurrent.*;

public class MyDisruptor {

  public static void main(String[] args) {
    test();
  }

  private static void test() {
    Disruptor<Message> disruptor = startDisruptor();
    publish(new Message("1", "ZZZZ"), disruptor);
  }

  public static Disruptor<Message> startDisruptor() {
    EventFactory<Message> eventFactory = new EventFactory<Message>() {
      @Override
      public Message newInstance() {
        return new Message();
      }
    };

    int bufferSize = 1024;

    // 生产者的线程工厂
    ThreadFactory threadFactory = new ThreadFactory() {
      @Override
      public Thread newThread(Runnable r) {
        return new Thread(r, "simpleThread");
      }
    };

    Disruptor<Message> disruptor = new Disruptor<>(eventFactory,
        bufferSize,
        threadFactory,
        ProducerType.SINGLE,
        new SleepingWaitStrategy());

//    EventHandler<Message> handle1 = new MyEventHandler("1");
//    EventHandler<Message> handle2 = new MyEventHandler("2");
//    EventHandler<Message> handle3 = new MyEventHandler("3");
//    // 菱形操作
//    EventHandlerGroup<Message> handlerGroup = disruptor.handleEventsWith(handle1, handle2);
//    handlerGroup.then(handle3);

    // 六边形
    //    H1  H2
    // P         H5
    //    H3  H4

    EventHandler<Message> handle1 = new MyEventHandler("1");
    EventHandler<Message> handle2 = new MyEventHandler("2");
    EventHandler<Message> handle3 = new MyEventHandler("3");
    EventHandler<Message> handle4 = new MyEventHandler("4");
    EventHandler<Message> handle5 = new MyEventHandler("5");

    disruptor.handleEventsWith(handle1, handle3);
    disruptor.after(handle1).then(handle2);
    disruptor.after(handle3).then(handle4);
    disruptor.after(handle2, handle4).then(handle5);

    disruptor.start();

    return disruptor;
  }

  public static void publish(Message message, Disruptor<Message> disruptor) {
    disruptor.publishEvent(new MyEventTranslator(message));
  }

}
