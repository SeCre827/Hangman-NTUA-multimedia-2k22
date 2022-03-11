package com.example.hangmanproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class HangmanApplication extends Application {
    String dictId;
    Alert a = new Alert(Alert.AlertType.NONE);
    Game game = new Game();
    Image eikona0 = new Image("hangman0.png");
    Image eikona1 = new Image("hangman1.png");
    Image eikona2 = new Image("hangman2.png");
    Image eikona3 = new Image("hangman3.png");
    Image eikona4 = new Image("hangman4.png");
    Image eikona5 = new Image("hangman5.png");
    Image eikona6 = new Image("hangman6.png");




    @FXML
    public TextField availableWordsNum;
    public TextField pointsNum;
    public TextField dictionaryBox;
    public TextField correctGuessesNum;
    public TextField livesLeftNum;
    public Text textInfo;
    public GridPane probabilitesContainer;
    public GridPane lettersContainer;
    public ImageView imageView;



    /**
     * LetterProb class
     * Extends TextField
     * letter Holds the letter
     * probability holds the propability of the letter
     */
    public static class letterProb extends TextField {
        public char letter;
        public double probability;

        public letterProb(char letter, double probability){
            super();
            this.letter= letter;
            this.probability= probability;
//            setText(String.valueOf(this.letter));
            setText(letter + ": " + this.probability);
            setEditable(false);


        }
    }

    /**
     * Number class
     * Extends TextField
     * letter Holds the letter
     * position holds the propability of the letter
     */
    public static class Number extends TextField {
        public  char letter;
        public int position;


        public Number(char letter, int position){
            super();
            this.letter = letter;
            this.position = position;
            setEditable(false);
            setText("-");
        }
    }

        public void setProbabilitesContainer() {
        probabilitesContainer.getChildren().clear();
        int i = 0;
        for(Character key : game.round.letterProbsSorted.keySet()) {
            letterProb n = new letterProb(key, Math.floor(game.round.letterProbsSorted.get(key) * 100) / 100 );
            n.setOnMouseClicked(this::probabilitiesHandler);
            probabilitesContainer.add(n,0, i);
            i++;
        }
    }

    /**
     * setLettersContainer Method
     * method to set the letters inside the Letter Container
     */
    public void setLettersContainer() {
        lettersContainer.getChildren().clear();
        for (int i = 0; i<game.word.length(); i++){
            Number n = new Number(game.word.charAt(i),i);
            if (game.flags[i] == 1 ) {
                n.setText(String.valueOf(game.word.charAt(i)));
                n.setMouseTransparent(true);
                n.setFocusTraversable(false);
            }
            n.setOnMouseClicked(this::letterHandler);
            lettersContainer.add(n, i, 0);
        }

    }

    /**
     * probabilitiesHandler Method
     * Handler for the probabilities letters
     * This method handles the game
     * Implements the guess. Checks if the game ends
     * Handles the images. Update the letters and the new probablities.
     * @param e The event object
     */
    private void probabilitiesHandler(MouseEvent e) {
        letterProb l = (letterProb)  e.getSource(); // exw access letter
        game.round.setLetterGuess(l.letter);
        game.player.addToLetterGuesses(l.letter);
        game.makeAGuess(game.round.letterGuess,game.round.letterPosition, game.player.points,game.player.lifes,game.word,game.round.possibleWords,game.player.correct_Guesses,game.round.letterProbsSorted,game.flags);
        game.player.setTotalGuesses(game.player.totalGuesses+1);
        pointsNum.setText(String.valueOf(game.player.points));
        livesLeftNum.setText(String.valueOf(game.player.lifes));
        textInfo.setText("Pick a position to guess a letter for!");
        if(game.player.correct_Guesses.size()!= 0) {
            correctGuessesNum.setText(String.valueOf((float)game.player.correct_Guesses.size()/game.player.totalGuesses));
        }
        else        correctGuessesNum.setText(String.valueOf(game.player.correct_Guesses.size()));
//        availableWordsNum.setText(String.valueOf(game.round.possibleWords.size()));
        if (game.player.correct_Guesses.size()==game.word.length() && game.player.lifes > 0) {
            textInfo.setText("YOU WIN!");
            a.setAlertType(Alert.AlertType.CONFIRMATION);
            a.setTitle("Game ended");
            a.setContentText("YOU WON!");
                // show the dialog
                a.show();
            }
            if (game.player.lifes<=0) {
                textInfo.setText("YOU LOST! Start another round!");
                a.setAlertType(Alert.AlertType.CONFIRMATION);
                a.setTitle("Game ended");
                a.setContentText("YOU LOSE! \n Start another round by clicking application -> Start.");

                // show the dialog
                a.show();
            }
            game.round.letterProbs.clear();
            game.round.letterProbsSorted.clear();

        setLettersContainer();
            setProbabilitesContainer();
            imageHandler();
    }
    /**
     * letterHandler Method
     * Handler for the letters
     * Changes the letter which the probabilities are computed
     * Handles the images. Update the letters and the new probablities
     * @param e The event object
     */
    private void letterHandler(MouseEvent e) {
        Number n = (Number)  e.getSource(); // exw access sto number
        textInfo.setText("Guessing for letter in position: " + n.position);
        game.round.setLetterPosition(n.position);
        game.round.computeSortedProbabilities();
        setProbabilitesContainer();

    }

    /**
     * imageHandler Method
     * Handles the images based on the lives the player has
     */
    private void imageHandler() {
        switch (game.player.lifes) {
            case 6:
                imageView.setImage(eikona0);
                break;
            case 5:
                imageView.setImage(eikona1);
                break;
            case 4:
                imageView.setImage(eikona2);
                break;
            case 3:
                imageView.setImage(eikona3);
                break;
            case 2:
                imageView.setImage(eikona4);
                break;
            case 1:
                imageView.setImage(eikona5);
                break;
            case 0:
                imageView.setImage(eikona6);
                break;
        }

    }


    /**
     * startGame Method
     * Starts the game.
     * Sets up starting values
     * Reset previous game
     * @param e The event object
     */
    public void startGame(ActionEvent e) throws UndersizeException, UnbalancedException, FileNotFoundException {
            game.reset();
            if (dictId == null)
        {
            // set alert type
            a.setAlertType(Alert.AlertType.WARNING);
            a.setTitle("No dictionary picked");
            a.setContentText("You have to choose a dictionary first!");
            // show the dialog
            a.show();
        }
            else {
                game.connectApi(dictId);
                game.wordsHandler(dictId);
                game.setStartingPossibleWords();
                game.round.computeProbabilities();
                availableWordsNum.setText(String.valueOf(game.words.size()));
                pointsNum.setText("0");
                livesLeftNum.setText("5");
                correctGuessesNum.setText("0");
                game.startGame();
                setLettersContainer();
                imageView.setImage(eikona0);
                textInfo.setText("New game started!");

            }
    }

    /**
     * showSolution Method
     * Shows the solution
     * Ends this game and starts a new one
     * @param e The event object
     */
    public  void showSolution(ActionEvent e) throws UndersizeException, FileNotFoundException, UnbalancedException {
        a.setAlertType(Alert.AlertType.INFORMATION);
        textInfo.setText("YOU LOSE!");
        a.setTitle("Show solution");
        a.setContentText("You lost! The word is: " + game.word);
        // show the dialog
        a.show();
        startGame(new ActionEvent());

    }

    /**
     * dictionary1Button Method
     * Handler that sets the dictionaryID to OL45883W
     * @param e The event object
     */
    public  void dictionary1Button(ActionEvent e){
        dictionaryBox.setText("OL45883W");
        dictId = "OL45883W";
    }

    /**
     * dictionary2Button Method
     * Handler that sets the dictionaryID to OL31390631M
     * @param e The event object
     */
    public  void dictionary2Button(ActionEvent e){
        dictionaryBox.setText("OL31390631M");
        dictId = "OL31390631M";
//        OL31390631M
    }

    /**
     * exitGame Method
     * Handler that exits the game
     * @param e The event object
     */
    public  void    exitGame
            (ActionEvent e) {
        System.exit(0);
    }

    /**
     * dictionaryInfo Method
     * Handler that pops up a windows that shows what percentage of the total words are
     * made of 6 letter, made between 7 and nine letters, made of 10 letters or more
     * @param e The event object
     */
    public  void    dictionaryInfo
            (ActionEvent e) {
        float six = 0;
        float sevenToNine= 0;
        float moreThanTen = 0;
        for (String s : game.startingWords){
            if (s.length() == 6) {
                six++;
            }
            if (s.length() >= 7 && s.length() < 10) {
                sevenToNine++;
            }
            if (s.length() > 10) {
                moreThanTen++;
            }
        }
        if(six > 0 ){ six = (float) (Math.floor(six / game.startingWords.size() * 100) / 100);}
        else six = 0;

        if(sevenToNine > 0 ){ sevenToNine = (float) (Math.floor(sevenToNine / game.startingWords.size() * 100) / 100);}
        else sevenToNine = 0;

        if(moreThanTen > 0 ){ moreThanTen = (float) (Math.floor(moreThanTen / game.startingWords.size() * 100) / 100);}
        else moreThanTen = 0;
        a.setAlertType(Alert.AlertType.INFORMATION);
        a.setTitle("Dictionary Words Length Frequency");
        a.setContentText("Percentage of words with 6 letters: " + six +"%" + "\n"+ "Percentage of words between 7 and 9 letters: " + sevenToNine +"%"+ "\n" + " Percentage of words with more than 10 letters: " + moreThanTen +"%");
        // show the dialog
        a.show();

    }

    /**
     * method that loads the specified txt (by giving the dictionary_id) as the current dictionary
     * @throws IOException Exception occurs when trying to open the txt that has the dictionary inside
     */
    public void loadAction() throws IOException, UndersizeException, UnbalancedException {
        TextInputDialog lDialog = new TextInputDialog("Dictionary_ID");
        lDialog.setTitle("Load Dictionary");
        lDialog.setHeaderText("Enter your the Dictionary_ID you want to load:");
        lDialog.showAndWait();
        dictId = lDialog.getEditor().getText();
//        if (new File("medialab/hangman_" + dictId + ".txt").isFile()) {
        if (new File( "./src/main/resources/medialab/"+"hangman_" + dictId + ".txt").isFile()) {

                startGame(new ActionEvent());
        }
        else if (new File("medialab/hangman_" + dictId + ".txt").isFile()) {

            startGame(new ActionEvent());
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Dictionary 404");
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.setContentText("This Dictionary does not exist.\nTry an other dictionary, or create a new one.");
            alert.showAndWait();
        }
    }



    /**
     * start Method
     * Sets the bacic Stage
     * @param stage The stage
     */
    @Override
    public void start(Stage stage) throws Exception {
//       Stage stage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hangman.fxml")));



        Scene scene = new Scene(root, 1200,840 , Color.AZURE);
        Image logo = new Image("logo.png");
        stage.getIcons().add(logo);
        stage.setTitle("MediaLab Hangman");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) throws FileNotFoundException, UndersizeException, UnbalancedException {

        launch(args);  // Ksekinaei to jfx
        System.exit(1);
    }
}



