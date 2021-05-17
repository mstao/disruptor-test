package me.mingshan.test.disruptor.mulit;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;
import me.mingshan.test.disruptor.Message;

import java.util.concurrent.*;

public class Main {
  public static void main(String[] args) {
    // 创建ringbuffer

    EventFactory<Message> eventFactory = new EventFactory<Message>() {
      @Override
      public Message newInstance() {
        return new Message();
      }
    };

    int bufferSize = 1024;
    RingBuffer<Message> ringBuffer = RingBuffer.create(ProducerType.MULTI,
        eventFactory,
        bufferSize,
        new SleepingWaitStrategy());

    // 创建SequenceBarrier
    SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

    // 创建多个消费者
    Consumer[] consumers = new Consumer[10];

    for (int i = 0; i < 10; i++) {
      Consumer consumer = new Consumer(i + "");
      consumers[i] = consumer;
    }

    // 构建多消费者工作池
    WorkerPool<Message> workerPool = new WorkerPool<>(ringBuffer,
        sequenceBarrier,
        new MyExceptionHandler(),
        consumers);

    // 设置多个消费者的sequence序号，用于单独统计消费进度，并且设置到ringbuffer中
    ringBuffer.addGatingSequences(workerPool.getWorkerSequences());

    // 生产者的线程工厂
    ThreadFactory threadFactory = new ThreadFactory() {
      @Override
      public Thread newThread(Runnable r) {
        return new Thread(r, "simpleThread");
      }
    };

    ExecutorService threadPoolExecutor = new ThreadPoolExecutor(2,
        10,
        1,
        TimeUnit.SECONDS,
        new ArrayBlockingQueue<>(10),
        threadFactory,
        new ThreadPoolExecutor.DiscardOldestPolicy());

    // 启动workpool
    workerPool.start(threadPoolExecutor);

    CountDownLatch latch = new CountDownLatch(1);

    for (int i = 0; i < 10; i++) {
      int finalI = i;
      new Thread(new Runnable() {
        @Override
        public void run() {
          try {
            latch.await();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

          Producer producer = new Producer(ringBuffer);
          producer.sendData(new Message(finalI + "", finalI + ""));
        }
      }).start();
    }

    System.out.println("线程创建完毕，开始生产数据---");
    latch.countDown();
  }
}
