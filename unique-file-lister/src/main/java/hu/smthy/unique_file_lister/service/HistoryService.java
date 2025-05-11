package hu.smthy.unique_file_lister.service;

import hu.smthy.unique_file_lister.domain.UniqueFileAccess;

public interface HistoryService {
    Iterable<UniqueFileAccess> readAll();
    void save(UniqueFileAccess uniqueFileAccess);
}