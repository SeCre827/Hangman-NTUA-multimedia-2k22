package com.example.hangmanproject;


/**
 * This Exception occurs when a word exists twice (or more than 2) in the dictionary
 */
public class InvalidCountExeception extends Exception{
    public InvalidCountExeception (String msg) {
        super(msg);
    }
}
