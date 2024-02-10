package ie.atu.sw;

import java.util.Map;

/**
 * Interface for outputting results in the Sentiment Analysis application.
 * Classes implementing this interface are responsible for writing the analysis results to an output destination.
 */
public interface Output {

    /**
     * Writes the results of sentiment analysis to the specified output directory.
     *
     * @param data                Map containing the data to be written, typically word sentiments and their scores.
     * @param outputDirectoryPath The path of the output directory where results will be stored.
     */
    void writeResults(Map<String, Double> data, String outputDirectoryPath);
}
