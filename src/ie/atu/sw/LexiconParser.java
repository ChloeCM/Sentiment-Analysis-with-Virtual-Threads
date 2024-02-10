package ie.atu.sw;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Parses lexicon files to extract word sentiment scores.
 * This parser can handle both single files and directories containing multiple files.
 */
public class LexiconParser implements Parser {

    /**
     * Parses a single lexicon file.
     * Reads each line of the file, expecting a comma-separated format for word and sentiment score.
     * Complexity: O(n), where n is the number of lines in the file.
     *
     * @param filePath The path to the lexicon file.
     * @return A Map with words as keys and their sentiment scores as values.
     * @throws IOException If an I/O error occurs reading from the file.
     */
    @Override
    public Map<String, Double> parseFile(String filePath) throws IOException {
        Map<String, Double> lexicon = new HashMap<>();
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            lines.forEach(line -> {
                try {
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        String word = parts[0].trim();
                        double score = Double.parseDouble(parts[1].trim());
                        lexicon.put(word, score);
                    } else {
                        System.err.println("Unusual line format (ignored): " + line);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing score for line: " + line + ".\nError: " + e.getMessage());
                }
            });
        }
        return lexicon;
    }

    /**
     * Parses all lexicon files in a given directory.
     * Processes each file in the directory in parallel using virtual threads.
     * Complexity: O(m*n), where m is the number of files and n is the average number of lines per file.
     *
     * @param directoryPath The path to the directory containing lexicon files.
     * @return A ConcurrentHashMap with words as keys and their sentiment scores as values, aggregated from all files.
     * @throws IOException If an I/O error occurs reading from the files.
     */
    public ConcurrentHashMap<String, Double> parseLexiconDirectory(String directoryPath) throws IOException {
        ConcurrentHashMap<String, Double> combinedLexicon = new ConcurrentHashMap<>();
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            paths.filter(Files::isRegularFile)
                    .forEach(filePath -> executor.submit(() -> {
                        try {
                            combinedLexicon.putAll(parseFile(filePath.toString()));
                        } catch (IOException e) {
                            System.err.println("Failed to read file: " + filePath + ".\nError: " + e.getMessage());
                        }
                    }));
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Lexicon parsing interrupted: " + e.getMessage());
        }

        return combinedLexicon;
    }
}
