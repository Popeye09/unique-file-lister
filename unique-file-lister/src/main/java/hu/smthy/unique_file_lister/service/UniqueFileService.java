package hu.smthy.unique_file_lister.service;

import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.Map;

/**
 * Interface for retrieving unique files from a directory.
 * Provides a method to get a map of unique file base names and their occurrence counts.
 */
public interface UniqueFileService {
    /**
     * Retrieves a map of unique file names and their occurrence counts within the specified directory.
     * @param path the path to the directory to traverse
     * @param username the username of the user initiating the request
     * @param extension optional file extension to filter files (e.g., "txt"). If null or empty, all files are considered
     * @return a {@link Map} where keys are file names and values are their occurrence counts
     * @throws FileNotFoundException if the specified path does not exist
     * @throws NotDirectoryException if the specified path is not a directory
     * @throws SecurityException if the directory cannot be read due to insufficient permissions
     */
    Map<String, Integer> getUniqueFiles(String path, String username, String extension)
            throws FileNotFoundException, NotDirectoryException, SecurityException;
}
