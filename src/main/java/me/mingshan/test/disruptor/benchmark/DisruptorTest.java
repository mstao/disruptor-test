package me.mingshan.test.disruptor.benchmark;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ThreadFactory;

/**
 * @author Walker Han
 * @date 2021/4/15 14:45
 */
public class DisruptorTest {
  public static void main(String[] args) throws InterruptedException {
    test(10000000);
  }

  public static void test(int capacity) throws InterruptedException {

    // 生产者的线程工厂
    ThreadFactory threadFactory = new ThreadFactory() {
      @Override
      public Thread newThread(Runnable r) {
        return new Thread(r, "simpleThread");
      }
    };

    // RingBuffer生产工厂,初始化RingBuffer的时候使用
    EventFactory<Element> factory = new EventFactory<Element>() {
      @Override
      public Element newInstance() {
        return new Element(1);
      }
    };

    // 处理Event的handler
    EventHandler<Element> handler = new EventHandler<Element>() {
      int count = 0;
      long startTime = System.currentTimeMillis();

      @Override
      public void onEvent(Element element, long sequence, boolean endOfBatch) {
        if (count == capacity) {
          long endTime = System.currentTimeMillis();
          System.out.println("耗时：" + (endTime - startTime));
        }
        count++;
      }

    };

    // 阻塞策略
    BlockingWaitStrategy strategy = new BlockingWaitStrategy();

    // 指定RingBuffer的大小
    int bufferSize = 1024 * 1024;

    // 创建disruptor，采用单生产者模式
    Disruptor<Element> disruptor = new Disruptor(factory, bufferSize, threadFactory, ProducerType.SINGLE, strategy);

    // 设置EventHandler
    disruptor.handleEventsWith(handler);

    // 启动disruptor的线程
    disruptor.start();

    RingBuffer<Element> ringBuffer = disruptor.getRingBuffer();

    for (int l = 0; l <= capacity; l++) {
      // 获取下一个可用位置的下标
      long sequence = ringBuffer.next();
      try {
        // 返回可用位置的元素
        Element event = ringBuffer.get(sequence);
        // 设置该位置元素的值
        event.set(l);
      } finally {
        ringBuffer.publish(sequence);
      }
    }
  }
}
