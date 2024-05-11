package com.aues.loanms.exception;

public class LoanNotFoundException extends RuntimeException {

    public LoanNotFoundException(String message) {
        super(message);
    }

    // Optionally, you can add a constructor that includes the cause of the exception
    public LoanNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

