package ie.atu.sw;

import java.io.IOException;
import java.util.Map;

/**
 * Interface for parsing files.
 */
public interface Parser {

    /**
     * Parses a file and extracts data into a map.
     *
     * @param filePath The path to the file to be parsed.
     * @return A Map with key-value pairs representing the parsed data.
     * @throws IOException If an I/O error occurs during file parsing.
     */
    Map<String, Double> parseFile(String filePath) throws IOException;

}
