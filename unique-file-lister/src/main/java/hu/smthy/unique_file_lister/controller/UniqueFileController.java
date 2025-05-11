package hu.smthy.unique_file_lister.controller;

import hu.smthy.unique_file_lister.service.UniqueFileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;
import java.util.Collections;
import java.util.Map;

@RestController
public class UniqueFileController {

    private final UniqueFileService uniqueFileService;

    public UniqueFileController(UniqueFileService uniqueFileService) {
        this.uniqueFileService = uniqueFileService;
    }

    @GetMapping(path = "/getUnique/{directory}", produces = "application/json")
    public ResponseEntity<Map<String, Integer>> getUnique(@PathVariable String path,
                                          @RequestParam String extension,
                                          @RequestParam String username){

        Map<String, Integer> result;

        FileFilter extensionFilter = file -> file.getName().endsWith("." + extension);
        uniqueFileService.setFileFilter(extensionFilter);

        try{
            result = uniqueFileService.getUniqueFiles(path, username);
        }
        catch (FileNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyMap());
        }
        catch (NotDirectoryException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyMap());
        }
        catch(SecurityException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.emptyMap());
        }
        return ResponseEntity.ok(result);    }
}
