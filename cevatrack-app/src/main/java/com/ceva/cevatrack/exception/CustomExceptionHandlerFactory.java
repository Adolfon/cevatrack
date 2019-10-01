package com.ceva.cevatrack.exception;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandlerFactory.class);
    private ExceptionHandlerFactory exceptionHandlerFactory;

    public CustomExceptionHandlerFactory() {
    }

    public CustomExceptionHandlerFactory(ExceptionHandlerFactory exceptionHandlerFactory) {
        this.exceptionHandlerFactory = exceptionHandlerFactory;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        LOGGER.info(" -- Inside of the getExceptionHandler -- ");
        return new CustomExceptionHandler(exceptionHandlerFactory.getExceptionHandler());
    }
}