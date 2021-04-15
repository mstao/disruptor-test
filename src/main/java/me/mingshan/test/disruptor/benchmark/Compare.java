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
  public void test1() throws InterruptedException {
    int capacity = 10000000;
    ArrayBlockQueueTest.test(capacity);
  }
}
