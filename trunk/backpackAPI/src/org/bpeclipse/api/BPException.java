package org.bpeclipse.api;

public class BPException extends Exception {

    public BPException() {
        super();
    }

    public BPException(String message, Throwable cause) {
        super(message, cause);
    }

    public BPException(String message) {
        super(message);
    }

    public BPException(Throwable cause) {
        super(cause);
    }

}
