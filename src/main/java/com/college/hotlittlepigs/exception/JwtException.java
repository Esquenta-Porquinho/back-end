package com.college.hotlittlepigs.exception;

public class JwtException extends RuntimeException{
    
    private static final long serialVersionUID = 1L;

    public JwtException(String msg) {
        super(msg);
    }

}