package com.example.hangmanproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Game Class
 * A parent class to handle the whole hangman game
 * words: The list of words in the dictionary
 * word: The word that is chosen from the dictionary
 * round: The round object
 * player: The player object
 */
public class Game {
    List<String> words;
    List<String> startingWords;
    String word;
    int[] flags; // Array to know which chars i have found
    public Round round;
    public Player player;


    /**
     * Game constructor
     */
    Game(){
        System.out.println("Running Game Constructor");
        this.words = new ArrayList<>();
        this.startingWords = new ArrayList<>();
        this.round = new Round();
        this.player = new Player();
    }

    /**
     * reset method
     * resets the game class
     */
    public void reset() {
        this.player.reset();
        this.round.reset();
        this.words.clear();
        this.startingWords.clear();
    }
    /**
     * connectApi method
     * Send a get request to tha api.
     *  @param dictId The dictID for the API call
     */
    public void connectApi(String dictId) throws UndersizeException, UnbalancedException {
        ConnectApi connect = new ConnectApi();
        String desc = connect.create_connection(dictId);  //        System.out.println(desc);
        connect.desc_to_set_and_file(desc, dictId);
    }

    /**
     * wordsHandler method
     * Puts all the words from the description into an array and picks a random word to be the main word of the game.
     *  @param dictId The dictID for the API call
     */
    public void wordsHandler(String dictId) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("C:\\Users\\lefte\\Desktop\\Hangman\\src\\main\\resources\\medialab\\hangman_" + dictId +".txt"));
        // Put all the words into an array
        while( scanner.hasNext()) {
            this.words.add(scanner.nextLine());
        }
        this.startingWords.addAll(words);
        Random rand = new Random();     //pick a random word
        this.word = words.get(rand.nextInt(words.size()));
        System.out.println(this.word);
        this.flags = new int[word.length()];
        Arrays.fill(this.flags,0);
  }

    /**
     * setStartingPossibleWords method
     * Set the starting possible words.
     */
    public void setStartingPossibleWords() {
        round.setInitialPossibleWords(this.words, this.word);
    }



    /**
     * startGame method
     * Starts the game. Until the word is found or the player run out of lives the players guesses to find the word.
     */
    public void startGame(){
            // Compute Probabilites
            this.round.computeSortedProbabilities();


    }




    // Implementing A guess:
    // Updating the subset of possible words after each guess,
    // Updates Player lifes and points, total number of guess, number of correct guesses

    /**
     * makeAGuess method
     * Implementing A guess for each round of the game.
     * Updates the subset of possible words after each guess
     * Updates Player lives and points, total number of guess, number of correct guesses
     * @param letterGuess The letter the player guessed
     * @param letterPosition The position the player picked to guess the letter
     * @param points Total points the player has
     * @param lifes Number of lives the player has
     * @param word The word the player is trying to guess
     * @param possibleWords The list of possible words that fit the guesses that have happened this far
     * @param letterProbsSorted The probabilities for each word to be the right one in descending order.
     * @param correct_Guesses A map of the correct guesses*
     * @param flags Helper flags to keep track of which words are already found
     */
    public void makeAGuess(Character letterGuess,int letterPosition, int points,int lifes, String word,  List<String> possibleWords,Hashtable<Integer, Character> correct_Guesses,Map<Character,Double> letterProbsSorted,int[] flags ){
        if (letterGuess == word.charAt(letterPosition)) {
            this.player.addToCorrectGuesses(letterPosition, letterGuess);
            this.flags[letterPosition] = 1;
            this.player.setPoints(givePoints(points,letterProbsSorted, letterGuess,true));
            //update the substet of words
            possibleWords.removeIf(s -> s.charAt(letterPosition) != word.charAt(letterPosition));
        }
        else {
            this.player.setPoints(givePoints(points,letterProbsSorted, letterGuess,false));
            //update the subset of words
            possibleWords.removeIf(s -> s.charAt(letterPosition) == letterGuess);
            this.player.setLifes(lifes-1);
        }
    }


    /**
     * givePoints method
     * Giving points based on the prediction the player made.
     * If the word that the player guessed is correct:
     *      Get 5 points if it has a probability higher than 0.6
     *      Get 10 points if it has a probability between 0.4 and 0.6
     *      Get 15 points if it has a probability between 0.4 and 0.25
     *      Get 30 points if it has a probability lower than 0.4
     *  If the guess is wrong and the player has at least 15 points decrease his points by 15
     *  If the guess is wrong and the player has less than 0 points set his points to 0
     * @param pts The points the player has
     * @param probs The probabilities of the letters the player could choose from
     * @param index THe index that shows which letter the player chose
     * @param flag If flag == 1 the guess is correct. If flag==0 the guess is wrong

     */
    private static int givePoints (int pts,Map<Character,Double> probs ,Character index,boolean flag ) {
        if (flag){
            double value = probs.get(index);
            if (value >= 0.6 ) pts += 5;
            else if (value < 0.6 && value >=0.4 ) pts += 10;
            else if (value < 0.4 && value >=0.25) pts += 15;
            else pts += 30;
            return pts;
        }
        else
        {if (pts <15)  return pts =0;
        else  return pts -= 15;}
    }





}
