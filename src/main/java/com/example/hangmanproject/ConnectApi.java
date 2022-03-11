package com.example.hangmanproject;

import org.json.JSONObject;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * ConnectAPI Class
 * A class to handle the Api connection and the dictionary.
 */

public class ConnectApi {



    /**
     * Get_desc_from_api method
     * Makes a GET Request to https://openlibrary.org/works/{DictID}.json
     * Gets the description and returns the Data as a set of filtered words
     * @param _dictID The Id of the dictionary
     * @return the description and returns the Data as a set of filtered words
     */


    public static String get_desc_from_api(String _dictID) {

        JSONObject json = new JSONObject(_dictID);
        String valueAttr = json.get("description").toString();
        return valueAttr.replaceAll("[\\n\\r\\t]+", " ").replaceAll("[^a-zA-Z]", " ");
    }


    /**
     * Create_connection method
     * Creates connection with the api https://openlibrary.org/works/{DictID}.json
     * @param dictID The Id of the dictionary
     * @return the description and returns the Data as a set of filtered words
     */

    public  String create_connection (String dictID) {
    if (Objects.equals(dictID, "OL31390631M")) {
        try
        {
            Thread.sleep(3500);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        return  "In A Game of Thrones George  Martin has created a genuine masterpiece bringing together the best the genre has to offer Mystery intrigue romance and adventure fill the pages of the first volume in an epic series sure to delight fantansy fans everywhere In a land where summers can last decades and winters a lifetime trouble is brewing The cold is returning and in the frozen wastes of the north of Winterfell sinister and supernatural forces are massing beyond the kingdom protective Wall At the center of the conflict lie the Starks of Winterfell a family as harsh and unyielding as the land they were born to Sweeping from a land of brutal cold to a distant summertime kingdom of epicurean plenty here is a tale of lords and ladies soldiers and sorcerers assassins and bastards who come together in a time of grim omens Amid plots and counterplots tragedy and betrayal victory and terror the fate of the Starks their allies and their enemies hangs perilously in the balance as each endeavors to win that deadliest of conflicts the game of thrones  back cover";

    }
//    OL45883W.json
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://openlibrary.org/works/"+ dictID + ".json")).build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)

                .thenApply(ConnectApi::get_desc_from_api)
                .join();
    }



    /**
     * Desc_to_set_and_file method
     * Get the response data from the api, filters them and return them in a set
     * @param __dictID The ID of the dictionary
     * @param  desc Description that returned from API call
     * @return The responseContent 'cleaned up' (Get only the value attribute of the json,
     *         replace all characters except a-zA-Z with empty string, so as the words that will end
     *         up in the dictionary will be only letters)
     */
    public  Set<String> desc_to_set_and_file(String desc, String __dictID) throws UnbalancedException, UndersizeException {
        String desc_lowered = desc.toUpperCase();
        String[] temp = desc_lowered.split("\\s+");
        Set<String> mySet = new HashSet<>(Arrays.asList(temp));
//        String dictID = "1";
        // remove words with length less than 6
        mySet.removeIf(word -> word.length() < 6);
        double count = 0;
        for (String word : mySet) {
            if (word.length() >= 9) count++;
        }
        double flag = count / mySet.size();
        // Checking if we have at least 20 words in the dictionary and if at least 20% of them have 9 or more letters.
        if (mySet.size() < 20) throw new UndersizeException("This dictionary does not have 20 words that match the criteria.");
        if ( flag < 0.2) throw new UnbalancedException("20% of the words in theDictionary must have at least 20");

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./src/main/resources/medialab/"+"hangman_" + __dictID + ".txt"), StandardCharsets.UTF_8))) {
            // write all words in the txt file
            int i = 0;
            for (String word : mySet) {
                if (++i == mySet.size()) writer.write(word);
                else writer.write(word + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mySet;

    }
}


//package com.example.hangmanproject;
//
//        import javafx.event.ActionEvent;
//        import javafx.fxml.FXML;
//        import javafx.scene.input.MouseEvent;
//
//public class Controller {
//
//
//    @FXML
//    public  void handler(ActionEvent e){
//        System.out.println("Event handler");
//    }
//
//
//    public  void mouseHandler(MouseEvent e){
//        System.out.println("Event handler");
//    }
//}
