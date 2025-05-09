package hu.smthy.unique_file_lister.service;

import java.util.Map;

public interface UniqueFileService {
    public Map<String, Integer> uniqueFiles(String path);
}
