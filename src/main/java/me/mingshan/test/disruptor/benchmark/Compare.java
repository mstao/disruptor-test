package me.mingshan.test.disruptor.benchmark;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * @author Walker Han
 * @date 2021/4/15 13:25
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 3)
@Measurement(iterations = 10, time = 5, timeUnit = TimeUnit.SECONDS)
@Threads(16)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class Compare {

  @Benchmark
  public void testArrayBlockQueue1() throws InterruptedException {
    int capacity = 10000000;
    ArrayBlockQueueTest.test(capacity);
  }

  @Benchmark
  public void testDisruptor1() throws InterruptedException {
    int capacity = 10000000;
    DisruptorTest.test(capacity);
  }

  @Benchmark
  public void testArrayBlockQueue2() throws InterruptedException {
    int capacity = 50000000;
    ArrayBlockQueueTest.test(capacity);
  }

  @Benchmark
  public void testDisruptor2() throws InterruptedException {
    int capacity = 50000000;
    DisruptorTest.test(capacity);
  }

  @Benchmark
  public void testArrayBlockQueue4() throws InterruptedException {
    int capacity = 100000000;
    ArrayBlockQueueTest.test(capacity);
  }

  @Benchmark
  public void testDisruptor4() throws InterruptedException {
    int capacity = 100000000;
    DisruptorTest.test(capacity);
  }
}
