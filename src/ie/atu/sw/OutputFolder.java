package ie.atu.sw;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Handles the output of sentiment analysis results to a file.
 * This class implements the Output interface, writing results to a specified file.
 */
public class OutputFolder implements Output {

    /**
     * Writes the sentiment analysis results to a specified output file.
     * Complexity: O(n), where n is the number of entries in the tweetSentiment map.
     *
     * @param tweetSentiment      A map containing tweet sentiments with their corresponding scores.
     * @param outputDirectoryPath The directory path where the output file will be written.
     */
    @Override
    public void writeResults(Map<String, Double> tweetSentiment, String outputDirectoryPath) {
        String outputFilePath = outputDirectoryPath + File.separator + "outputFile.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            for (Map.Entry<String, Double> entry : tweetSentiment.entrySet()) {
                String formattedOutput = formatTweetSentiment(entry.getKey(), entry.getValue());
                writer.write(formattedOutput + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to output file: " + e.getMessage());
        }
    }

    /**
     * Formats a tweet sentiment and its score into a readable string.
     * Complexity: O(1) - constant time complexity as it's a formatting operation.
     *
     * @param tweet The tweet text.
     * @param score The sentiment score of the tweet.
     * @return A formatted string representing the tweet and its sentiment score.
     */
    public static String formatTweetSentiment(String tweet, Double score) {
        String sentiment;
        if (score > 0) {
            sentiment = "Positive";
        } else if (score < 0) {
            sentiment = "Negative";
        } else {
            sentiment = "Neutral";
        }
        return "\n" +
                " Tweet: \"" + tweet + "\"\n Sentiment Score: " + score + " (" + sentiment + ")" +
                "\n________________________________________________________________________________";
    }

}
