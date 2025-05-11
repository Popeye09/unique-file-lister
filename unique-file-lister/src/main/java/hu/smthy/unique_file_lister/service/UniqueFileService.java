package hu.smthy.unique_file_lister.service;

import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.Map;

public interface UniqueFileService {
    Map<String, Integer> getUniqueFiles(Path path, String username)
            throws FileNotFoundException, NotDirectoryException, SecurityException;
}
