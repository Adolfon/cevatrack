package com.ceva.cevatrack.exception;

/**
 * NullObjectException.
 * 
 * @author mohammad.rasheed
 *
 */
public class NullObjectException extends Exception {

    /**
     * Default Constructor.
     */
    public NullObjectException() {
        /**
         * Zero Parameter Constructor
         */
    }

    public NullObjectException(String message) {
        super(message);
    }
}
