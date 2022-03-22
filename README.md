# Hangman-NTUA-multimedia-2k22
A Hangman Game project in Java for the multimedia course of NTUA

<p align = "center"> The project implements a complex variant of the Hangman game. The goal was to write this project with Java, using the OOP principles and doing an api call. </p>



## Details
* The user can choose two different IDs to create a dictionary from an API that is made to ```https://openlibrary.org/works/{OPEN-LIBRARY-ID}.json```. The API call returns a description from a book. Filtering that description we make a dictionary and then choose randomly a word that the player has to guess in order to win the game.
* The user can load Dictionaries (dictionaries that were created).
* There are 2 pre-made dictionaries with IDs 1 and 2. The dictionary with ID 1 had 50 words in it and the dictionary with ID 2 has 145 words in it.  
* To form the dictionaries all special chars and numbers are eliminated. Also, duplicates are eliminated.
* Also, the 20% of the words in the dictionary must have length >= 9, duplicates can not exist and the total dictionary length has to be >= 20. In any other case the dictionary is invalid.
* The hidden word will be chosen randomly from the active dictionary. 
* Every round the player can choose a position for one of the letters that are not found yet. When he clicks tha position automatically the probabilities for each letter to be the correct one are computed and presented in the right part of the screen.
* The player can make a guess by clicking one of the letters with its probability at its side. When the player clicks, if the guess is right the letter is shown and the pool of possible words is getting smaller and the player has to pick another position to guess the letter for. If the guess is wrong, the letter is out of the pool of possible letters and every word that had that letter in that position is out of the pool as well.The player has to choose a position to guess a letter and the probabilites are recalculated.
* For every correct guess the player gets points based on the probability of the letter he chose.
* For every wrong guess the player loses points. The player can't have less than 0 points.
* The player starts is game with 5 lives that are displayed at the top of the screen and from the hangman images as well.
* The game ends when the player either finds the hidden word, or runs out of tries. 

##Screenshots of the GUI
![6) GamePlay](https://user-images.githubusercontent.com/75163039/159472073-aa4b7610-a5f9-41f2-831f-6e0d2b915576.png)
![1)Start](https://user-images.githubusercontent.com/75163039/159472255-be80b1c9-bfd6-41d2-aa67-1e4398de2928.png)
![2)pick dictionary](https://user-images.githubusercontent.com/75163039/159472282-c31caa01-231f-4324-afd3-a090c4dabe49.png)
![5)Load premade dictionary](https://user-images.githubusercontent.com/75163039/159472305-ed36a533-7e7c-4a99-b2ce-cbf83c0e17cd.png)
![4)guess for letter](https://user-images.githubusercontent.com/75163039/159472221-e2814cf8-901e-441e-937b-5eec992c8d1b.png)
![9) Lost game dialog](https://user-images.githubusercontent.com/75163039/159472351-03ff1540-740a-460a-ae12-e6aad80e9a68.png)
![7) won dialog](https://user-images.githubusercontent.com/75163039/159472389-26b5dd9f-7f9d-4ff4-bb4b-eed07995dfaa.png)



### Required Jars
* [JSON Java](https://github.com/stleary/JSON-java)
* [Java FX 17](https://openjfx.io/openjfx-docs/)

[//]: # (* images notloading in Github)
