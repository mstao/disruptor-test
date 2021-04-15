package me.mingshan.test.disruptor.benchmark;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * 本机测试
 *
 * @author Walker Han
 * @date 2021/4/15 13:23
 */
public class LocalTest {
  public static void main(String[] args) throws RunnerException {
    Options options = new OptionsBuilder().include(Compare.class.getSimpleName())
        .output("D:/Benchmark.log").forks(2).build();
    new Runner(options).run();
  }
}
