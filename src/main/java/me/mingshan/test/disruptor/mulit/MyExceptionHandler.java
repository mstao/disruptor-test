package me.mingshan.test.disruptor.mulit;

import com.lmax.disruptor.ExceptionHandler;
import me.mingshan.test.disruptor.Message;

public class MyExceptionHandler implements ExceptionHandler<Message> {
  @Override
  public void handleEventException(Throwable ex, long sequence, Message event) {

  }

  @Override
  public void handleOnStartException(Throwable ex) {

  }

  @Override
  public void handleOnShutdownException(Throwable ex) {

  }
}
