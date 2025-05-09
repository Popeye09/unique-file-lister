package hu.smthy.unique_file_lister.service;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.HashMap;

@Component
public class UniqueFileRecursiveTreeSearchService {

    Map<String, Integer> uniqueFiles(String path){
        Map<String, Integer> map = new HashMap<>();

        recursiveTreeSearch(path, map);
        
        return map;
    }

    Map<String, Integer> recursiveTreeSearch(String path, Map<String, Integer> map){

        // TODO: implement recursive tree search

        return map;
    }
}
