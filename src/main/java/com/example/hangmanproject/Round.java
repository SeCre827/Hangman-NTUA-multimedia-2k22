package com.example.hangmanproject;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Round Class
 * A class to handle each round of the hangman game.
 * letterGuess: The letter the player guessed this round.
 * letterPosition: The position that the player chose the letter for.
 * possibleWords: the list of possible words for this round.
 * letterProbs: The probabilities for each letter for this round.
 * letterProbsSorted: The probabilities for each letter for this round sorted.
 */
public class Round {
    Character letterGuess;
    int letterPosition;
    List<String> possibleWords;
    Hashtable<Character, Double> letterProbs;
    Map<Character,Double> letterProbsSorted;

    // Constructor
    Round(){
        this.letterProbs = new Hashtable<>();
        this.possibleWords = new ArrayList<>();
    }

    /**
     * reset method
     * resets the round class
     */
    public void reset() {
        this.possibleWords.clear();
        System.out.println("Round reset");
        this.letterProbs = new Hashtable<>();
        this.possibleWords = new ArrayList<>();
    }
    //Setters
    /**
     * setLetterPosition method
     * Sets new letter position
     *  @param letterPosition The guess for the letter
     */
    public  void setLetterPosition( int letterPosition){
        this.letterPosition = letterPosition;
    }

    /**
     * setLetterGuess method
     * Sets new letter Guess
     *  @param letterGuess The guess for the letter
     */
    public  void setLetterGuess( Character letterGuess){
        this.letterGuess = letterGuess;
    }

    /**
     * setInitialPossibleWords method
     * Sets the initial possible words from the dictionary we have based on the word that it was chosen
     *  @param words The list of existing words
     *  @param word The word that it was picked automatically*
     */
    public void setInitialPossibleWords( List<String> words, String word){
        for (String s : words) {
            if (s.length() == word.length()) possibleWords.add(s);
        }
    }


    /**
     * computeProbabilities method
     * Compute the probabilities for the initial Set of words
     */
    public  void computeProbabilities( ){
        HashMap<Character, Integer> hm = new HashMap<>();
        for (String s : this.possibleWords) {
            Integer l = hm.get(s.charAt(this.letterPosition));
            hm.put(s.charAt(this.letterPosition), (l == null) ? 1 : l + 1);
        }
        // Find probabilities for each letter
        double  sum = (double) hm.values().stream().mapToInt(o -> o).sum();
        for (Map.Entry<Character,Integer> entry : hm.entrySet())               // loop over every letter and get the letter and the value
            this.letterProbs.put(entry.getKey(),entry.getValue()/sum);              // letter has value/total value probabilities
        hm.clear();
        this.letterProbsSorted = sortByValue(this.letterProbs);
    }


    /**
     * computeSortedProbabilities method
     * Compute the probabilities for the updated subset of words
     */
    public   void computeSortedProbabilities() {
        this.letterProbsSorted.clear();
        this.letterProbs.clear();
        HashMap<Character, Integer> hm = new HashMap<>();
        for (String s : this.possibleWords) {
            Integer l = hm.get(s.charAt(this.letterPosition));
            hm.put(s.charAt(this.letterPosition), (l == null) ? 1 : l + 1);
        }

        // Find probabilities for each letter
        double  sum = (double) hm.values().stream().mapToInt(o -> o).sum();
        for (Map.Entry<Character,Integer> entry : hm.entrySet())               // loop over every letter and get the letter and the value
            this.letterProbs.put(entry.getKey(),entry.getValue()/sum);              // letter has value/total value probabilities
        hm.clear();
//        assert this.letterProbs != null;
        this.letterProbsSorted  =sortByValue (this.letterProbs);
    }

    /**
     * sortByValue method
     * Helper Function that sorts a Hashtable by descending value
     * @param hm The hashtable that we want to sort
     * @return The hashtable sorted by descending value
     */
    public static HashMap<Character, Double>
    sortByValue(Hashtable<Character, Double> hm)
    {
        HashMap<Character, Double> temp
                = hm.entrySet()
                .stream()
                .sorted((i1, i2)
                        -> i2.getValue().compareTo(
                        i1.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        return temp;
    }




}
