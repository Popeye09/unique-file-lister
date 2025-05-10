package hu.smthy.unique_file_lister.service;

import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.Map;

public interface UniqueFileService {
    public Map<String, Integer> uniqueFiles(Path path) throws FileNotFoundException, NotDirectoryException, SecurityException;
}
