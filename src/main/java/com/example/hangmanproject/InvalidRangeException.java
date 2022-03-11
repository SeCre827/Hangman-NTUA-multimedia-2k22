package com.example.hangmanproject;


/**
 * This Exception occurs when a dictionary contains less than 20 words
 */
public class InvalidRangeException extends Exception {
    public InvalidRangeException (String msg) {
        super(msg);
    }
}

