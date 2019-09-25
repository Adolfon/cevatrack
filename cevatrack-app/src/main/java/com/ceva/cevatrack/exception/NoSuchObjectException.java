package com.ceva.cevatrack.exception;

/**
 * NoSuchObjectException.
 * 
 * @author mohammad.rasheed
 *
 */
public class NoSuchObjectException extends Exception {

    /**
     * Default Constructor.
     */
    public NoSuchObjectException() {
        /**
         * Zero Parameter Constructor
         */
    }

    public NoSuchObjectException(String message) {
        super(message);
    }
}
