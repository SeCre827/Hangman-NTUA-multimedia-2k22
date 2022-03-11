package com.example.hangmanproject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Player Class
 * A class to handler the information of each player
 * life: The number of remaining lives a player has
 * points: Total number of points a player has
 * totalGuesses: The number of  total guesses a player has done
 * letterGuesses: The letters a player have guessed
 * correct_Guesses: The number of correct Guesses and the correct characters the player has guesses
 */


public class Player {
    int lifes;
    int points;
    int totalGuesses;
    List<Character> letterGuesses;
    Hashtable<Integer, Character> correct_Guesses;

    //Constructors
    Player(){
        System.out.println("Running Player Constructor");
        this.lifes = 6;
        this.points = 0;
        this.totalGuesses= 0;
        this.letterGuesses = new ArrayList<>();
        this.correct_Guesses = new Hashtable<>();
    }


    /**
     * reset method
     * resets the player class
     */
    public void reset (){
        this.lifes = 6;
        this.points = 0;
        this.totalGuesses= 0;
        this.letterGuesses = new ArrayList<>();
        this.correct_Guesses = new Hashtable<>();
    }

    //    Setters
    /**
     * addToLetterGuesses method
     * add letter to guesses
     *  @param letterGuess The guess for the letter
     */
    public  void addToLetterGuesses( Character letterGuess){
        this.letterGuesses.add(letterGuess);
    }

    /**
     * addToCorrectGuesses method
     * add letter to correct guesses
     * @param letterPosition The position of the letter
     * @param letterGuess The guess fot the letter
     */
    public  void addToCorrectGuesses(int letterPosition, Character letterGuess){
        this.correct_Guesses.put(letterPosition, letterGuess);
    }

    /**
     * setPoints method
     * set the points to the given number
     * @param points The number of total points
     */
    public void setPoints(int points){
        this.points = points;
    }


    /**
     * setLifes method
     * set lifes to the given number
     * @param lifes The number of total lifes
     */
    public void setLifes(int lifes){
        this.lifes = lifes;
    }

    /**
     * setTotalGuesses method
     * set totalGuesses to the given number
     * @param totalGuesses The number of total guesses
     */
    public void setTotalGuesses(int totalGuesses){
        this.totalGuesses = totalGuesses;
    }
}
