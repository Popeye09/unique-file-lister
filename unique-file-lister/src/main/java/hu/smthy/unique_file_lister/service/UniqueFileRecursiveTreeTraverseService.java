package hu.smthy.unique_file_lister.service;

import hu.smthy.unique_file_lister.domain.UniqueFileAccess;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.Map;
import java.util.HashMap;

/**
 * Service implementation for recursively traversing directories and counting unique files based on their names.
 * This service uses a recursive approach to search through directory trees and collect file name occurrences.
 */
@Service
public final class UniqueFileRecursiveTreeTraverseService implements UniqueFileService{

    private final FileReaderService fileReaderService;
    private final HistoryService historyService;

    /**
     * Constructor for UniqueFileRecursiveTreeTraverseService.
     *
     * @param fileReaderService Service for reading file system information.
     * @param historyService Service for recording history of successful file listing operations.
     */
    public UniqueFileRecursiveTreeTraverseService(final FileReaderService fileReaderService, HistoryService historyService) {
        this.fileReaderService = fileReaderService;
        this.historyService = historyService;
    }

    /**
     * Retrieves a map of unique file names and their occurrence counts within the specified directory.
     *
     * <p>This method recursively traverses the directory tree starting from the given path, counting occurrences of each file name.
     * If an extension is provided, only files with that extension are considered. The method also records the operation in the history service.
     *
     * <p>Notes:
     * <ul>
     * <li>The method checks if the provided path exists, is a directory, and is readable.</li>
     * <li>If the path does not exist, a FileNotFoundException is thrown.</li>
     * <li>If the path is not a directory, a NotDirectoryException is thrown.</li>
     * <li>If the directory cannot be read due to permissions, a SecurityException is thrown.</li>
     * <li>Symbolic links are followed, which may lead to infinite loops if there are recursive links.</li>
     * </ul>
     *
     * @param path The path to the directory to traverse.
     * @param username The username of the user initiating the request.
     * @param extension Optional file extension to filter files (e.g., "txt"). If null or empty, all files are considered.
     * @return A map where keys are file names and values are their occurrence counts.
     * @throws FileNotFoundException If the specified path does not exist.
     * @throws NotDirectoryException If the specified path is not a directory.
     * @throws SecurityException If the directory cannot be read due to insufficient permissions.
     */
    public Map<String, Integer> getUniqueFiles(String path, String username, String extension) throws FileNotFoundException, NotDirectoryException, SecurityException{
        File file = Path.of(path).toFile();

        if(!fileReaderService.exists(file)) {
            throw new FileNotFoundException("Path must lead to an existing directory");
        }
        if(!fileReaderService.isDirectory(file)){
            throw new NotDirectoryException("Path must lead to a directory");
        }
        if(!fileReaderService.canRead(file)){
            throw new SecurityException(
                    "Unable to read directory contents: " + file.getName() + " has no read permission for " + System.getProperty("user.name"));
        }

        Map<String, Integer> map = new HashMap<>();

        FileFilter fileFilter;

        if(extension != null && !extension.isEmpty()) {
            fileFilter = f -> f.getName().endsWith("." + extension);
        }
        else{
            fileFilter = f -> true;
        }

        recursiveTreeSearch(file, map, fileFilter);

        historyService.save(UniqueFileAccess.builder()
                .directory(path)
                .username(username)
                .extension(extension)
                .timestamp(System.currentTimeMillis())
                .build()
        );

        return map;
    }

    /**
     * Recursively searches the directory tree starting from the given root, updating the map with file name occurrences.
     *
     * <p>This method is called by getUniqueFiles and traverses the directory tree, counting files that match the provided file filter.
     *
     * @param root The current directory or file to process.
     * @param map The map to update with file name occurrences.
     * @param fileFilter The filter to apply when selecting files.
     */
    private void recursiveTreeSearch(File root, Map<String, Integer> map, FileFilter fileFilter){
        File[] files = fileReaderService.listFiles(root);

        if(files == null){return;}

        for (File file : files) {
            if (fileReaderService.isFile(file) && fileFilter.accept(file)) {
                map.put(file.getName(), map.getOrDefault(file.getName(), 0) + 1);
            }
            else if (fileReaderService.isDirectory(file)) {
                recursiveTreeSearch(file, map, fileFilter);
            }
        }
    }
}
