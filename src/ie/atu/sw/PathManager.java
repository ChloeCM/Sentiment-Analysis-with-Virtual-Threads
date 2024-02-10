package ie.atu.sw;

/**
 * Interface for managing paths.
 * This interface provides methods to get file paths for tweets and lexicons, and to organise
 * input directories.
 */
public interface PathManager {

    /**
     * Gets the file path for tweets.
     *
     * @return The current set file path for tweets.
     */
    String getTweetFilePath();

    /**
     * Gets the file path for the lexicon.
     *
     * @return The current set file path for the lexicon.
     */
    String getLexiconFilePath();

    /**
     * Organizes the input directory based on the specified path type.
     *
     * @param pathType The type of path to organize ('tweet' or 'lexicon').
     */
    void organiseInputDirectory(String pathType);
}
