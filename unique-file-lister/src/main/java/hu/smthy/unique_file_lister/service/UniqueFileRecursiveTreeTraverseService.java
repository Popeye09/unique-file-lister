package hu.smthy.unique_file_lister.service;

import hu.smthy.unique_file_lister.domain.UniqueFileAccess;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.Map;
import java.util.HashMap;

@Service
public final class UniqueFileRecursiveTreeTraverseService implements UniqueFileService{

    private final FileReaderService fileReaderService;
    private final HistoryService historyService;
    private FileFilter fileFilter = file -> true;

    @Autowired
    public UniqueFileRecursiveTreeTraverseService(final FileReaderService fileReaderService, HistoryService historyService) {
        this.fileReaderService = fileReaderService;
        this.historyService = historyService;
    }

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

        if(extension != null && !extension.isEmpty()) {
            fileFilter = f -> f.getName().endsWith("." + extension);
        }

        recursiveTreeSearch(file, map);

        historyService.save(UniqueFileAccess.builder()
                .directory(path)
                .username(username)
                .extension(extension)
                .timestamp(System.currentTimeMillis())
                .build()
        );

        return map;
    }

    void recursiveTreeSearch(File root, Map<String, Integer> map){
        File[] files = fileReaderService.listFiles(root);

        if(files == null){
            return;
        }

        for (File file : files) {
            if (fileReaderService.isFile(file) && fileFilter.accept(file)) {
                map.put(file.getName(), map.getOrDefault(file.getName(), 0) + 1);
            }
            else if (fileReaderService.isDirectory(file)) {
                recursiveTreeSearch(file, map);
            }
        }
    }
}
