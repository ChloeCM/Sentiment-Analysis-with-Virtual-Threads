package ie.atu.sw;

import java.util.Map;

/**
 * Abstract class for processing tweets.
 * This class provides an abstract method for calculating the sentiment of a tweet.
 */
public abstract class TweetProcessor {

    /**
     * Calculates the sentiment score of a given tweet.
     *
     * @param tweet   The tweet text whose sentiment is to be calculated.
     * @param lexicon A map containing words and their associated sentiment scores.
     * @return The sentiment score of the tweet.
     */
    public abstract double calculateSentiment(String tweet, Map<String, Double> lexicon);
}
