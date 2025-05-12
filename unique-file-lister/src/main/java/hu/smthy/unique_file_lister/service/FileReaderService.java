package hu.smthy.unique_file_lister.service;

import java.io.File;
import java.io.FileFilter;

/**
 * Interface for reading file system information.
 * Provides methods to check file existence, directory status, file status, readability, and listing files.
 */
public interface FileReaderService {
    /**
     * Checks if the specified file exists.
     * @param file the {@link File} to check
     * @return true if the file exists, false otherwise
     */
    boolean exists(File file);

    /**
     * Checks if the specified file is a directory.
     * @param file the {@link File} to check
     * @return true if the file is a directory, false otherwise
     */
    boolean isDirectory(File file);

    /**
     * Checks if the specified file is a regular file.
     * @param file the {@link File} to check
     * @return true if the file is a regular file, false otherwise
     */
    boolean isFile(File file);

    /**
     * Checks if the specified file can be read.
     * @param file the {@link File} to check
     * @return true if the file can be read, false otherwise
     */
    boolean canRead(File file);

    /**
     * Lists the files in the specified directory.
     * @param file the directory to list files from
     * @return an array of files in the directory, or null if the directory is empty or cannot be read
     * @throws SecurityException if a security manager denies access to the directory
     */
    File[] listFiles(File file) throws SecurityException;
}
