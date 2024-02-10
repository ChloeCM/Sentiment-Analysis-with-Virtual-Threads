package ie.atu.sw;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Manages the process of sentiment analysis including parsing lexicons, processing tweets, and outputting results.
 */
public class SentimentAnalysisManager {
    private LexiconParser lexiconParser;
    private ProcessingTweets processingTweets;
    private OutputFolder outputFolder;

    /**
     * Constructor for SentimentAnalysisManager.
     * Initialises the components necessary for sentiment analysis.
     * Complexity: O(1) - constant time complexity for initialising objects.
     */
    public SentimentAnalysisManager() {
        this.lexiconParser = new LexiconParser();
        this.processingTweets = new ProcessingTweets();
        this.outputFolder = new OutputFolder();
    }

    /**
     * Performs sentiment analysis on a set of tweets using a specified lexicon and outputs the results.
     * Complexity: O(n*m), where n is the number of tweets, m is the average length of a tweet.
     *
     * @param lexiconPath    The path to the lexicon file or directory.
     * @param tweetPath      The path to the tweet file or directory.
     * @param outputFilePath The path to the output file.
     * @throws IOException If there is an issue in reading files or writing output.
     */
    public void performAnalysis(String lexiconPath, String tweetPath, String outputFilePath) throws IOException {
        // Check if the lexiconPath is valid
        Path lexiconPathObj = Paths.get(lexiconPath);
        if (!Files.exists(lexiconPathObj)) {
            throw new IOException("Lexicon path does not exist: " + lexiconPath);
        }

        // Determine if lexiconPath is a file or a directory
        Map<String, Double> lexicon;
        if (Files.isDirectory(lexiconPathObj)) {
            lexicon = lexiconParser.parseLexiconDirectory(lexiconPath);
        } else {
            lexicon = lexiconParser.parseFile(lexiconPath);
        }

        // Check if the tweetPath is valid
        Path tweetPathObj = Paths.get(tweetPath);
        if (!Files.exists(tweetPathObj)) {
            throw new IOException("Tweet path does not exist: " + tweetPath);
        }

        // Process tweets
        if (Files.isDirectory(tweetPathObj)) {
            processTweetDirectory(tweetPath, lexicon, outputFilePath);
        } else {
            List<String> tweets = loadTweets(tweetPath);
            String sourceIdentifier = Paths.get(tweetPath).getFileName().toString();
            processAndOutputTweets(tweets, lexicon, outputFilePath, sourceIdentifier);
        }
    }

    /**
     * Processes tweets from a directory and writes the sentiment results to an output file.
     * Uses virtual threads for efficient processing of multiple files.
     * Complexity: O(n*m), where n is the number of tweet files in the directory, and m is the average number of tweets per file.
     *
     * @param tweetDirectoryPath The path to the directory containing tweet files.
     * @param lexicon            The lexicon map used for sentiment analysis.
     * @param outputFilePath     The path to the output file.
     * @throws IOException If there is an issue in reading tweet files or writing output.
     */
    private void processTweetDirectory(String tweetDirectoryPath, Map<String, Double> lexicon, String outputFilePath) throws IOException {
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        Files.walk(Paths.get(tweetDirectoryPath))
                .filter(Files::isRegularFile)
                .forEach(filePath -> executor.submit(() -> {
                    try {
                        List<String> tweets = loadTweets(filePath.toString());
                        String sourceIdentifier = filePath.getFileName().toString();
                        processAndOutputTweets(tweets, lexicon, outputFilePath, sourceIdentifier);
                    } catch (IOException e) {
                        System.err.println("Error processing tweets from file: " + filePath + ". Error: " + e.getMessage());
                    }
                }));

        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.HOURS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            executor.shutdownNow();
        }
    }


    /**
     * Processes a list of tweets and outputs their sentiment scores.
     * Each tweet is processed to calculate its sentiment score, and results are written to an output file.
     * Complexity: O(n*m), where n is the number of tweets and m is the average length of a tweet.
     *
     * @param tweets           List of tweets to be processed.
     * @param lexicon          Map containing the lexicon for sentiment analysis.
     * @param outputFilePath   Path where the output should be written.
     * @param sourceIdentifier An identifier for the source of tweets (e.g., filename).
     * @throws IOException If an I/O error occurs during output writing.
     */
    private void processAndOutputTweets(List<String> tweets, Map<String, Double> lexicon, String outputFilePath, String sourceIdentifier) throws IOException {
        ConcurrentHashMap<String, Double> tweetSentiments = new ConcurrentHashMap<>();

        int lineNum = 1;
        for (String tweet : tweets) {
            // Create a unique key for each tweet by combining it with the source identifier and line number
            String uniqueKey = sourceIdentifier + "_" + lineNum + ": " + tweet;
            double sentimentScore = processingTweets.calculateSentiment(tweet, lexicon);
            tweetSentiments.put(uniqueKey, sentimentScore);
            lineNum++; // Increment the line number for the next tweet
        }

        tweetSentiments.forEach((key, sentiment) -> {
            String formattedOutput = OutputFolder.formatTweetSentiment(key, sentiment);
            System.out.println(formattedOutput);
        });

        outputFolder.writeResults(tweetSentiments, outputFilePath);
    }

    /**
     * Loads tweets from a file path into a list.
     * Reads each line from the file assuming each line represents a tweet.
     * Complexity: O(n), where n is the number of lines (tweets) in the file.
     *
     * @param tweetFilePath Path to the file containing tweets.
     * @return A list of tweets.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    private List<String> loadTweets(String tweetFilePath) throws IOException {
        return Files.lines(Paths.get(tweetFilePath)).collect(Collectors.toList());
    }

}
