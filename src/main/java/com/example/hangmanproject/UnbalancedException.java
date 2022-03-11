package com.example.hangmanproject;


/**
 * This Exception occurs when the 20% of the dictionary words do not have size >= 9
 */
public class UnbalancedException extends Exception {
    public UnbalancedException (String msg) {
        super(msg);
    }
}
