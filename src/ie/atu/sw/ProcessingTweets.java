package ie.atu.sw;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Processes tweets and calculates their sentiment scores.
 * This class extends TweetProcessor and provides an implementation for calculating sentiment based on a given lexicon.
 */
public class ProcessingTweets extends TweetProcessor {

    /**
     * Calculates the sentiment score of a given tweet based on a lexicon.
     * The score is computed by summing the sentiment scores of individual words in the tweet.
     * Complexity: O(n*m), where n is the number of words in the tweet, and m is the time taken for lexicon lookup per word.
     *
     * @param tweet   The tweet text whose sentiment is to be calculated.
     * @param lexicon A map containing words and their associated sentiment scores.
     * @return The sentiment score of the tweet.
     */
    @Override
    public double calculateSentiment(String tweet, Map<String, Double> lexicon) {
        double sum = Stream.of(tweet.split("\\s+"))
                .mapToDouble(word -> lexicon.getOrDefault(word.toLowerCase(), 0.0))
                .sum();

        // Formatting the sum to one decimal place
        String formattedSum = String.format("%.1f", sum);
        return Double.parseDouble(formattedSum);
    }

}
