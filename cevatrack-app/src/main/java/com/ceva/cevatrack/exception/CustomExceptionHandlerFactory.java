package com.ceva.cevatrack.exception;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandlerFactory.class);
  private ExceptionHandlerFactory exceptionHandlerFactory;

  public CustomExceptionHandlerFactory() {}

  public CustomExceptionHandlerFactory(ExceptionHandlerFactory exceptionHandlerFactory) {
    this.exceptionHandlerFactory = exceptionHandlerFactory;
  }

  @Override
  public ExceptionHandler getExceptionHandler() {
    LOGGER.info(" -- Inside of the getExceptionHandler -- ");
    ExceptionHandler handler =
        new CustomExceptionHandler(exceptionHandlerFactory.getExceptionHandler());
    return handler;
  }
}

// https://webcache.googleusercontent.com/search?q=cache:ogm-vg0l4bMJ:https://wmarkito.wordpress.com/2012/04/05/adding-global-exception-handling-using-jsf-2-x-exceptionhandler/&cd=1&hl=en&ct=clnk&gl=es
