package com.earthwatch.metadata.area;

public class AreaNotFoundException extends Exception {
    public AreaNotFoundException(Throwable cause) {
        super(cause);
    }

    public AreaNotFoundException(String message) {
        super(message);
    }

}
