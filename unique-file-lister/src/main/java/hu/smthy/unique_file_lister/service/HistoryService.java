package hu.smthy.unique_file_lister.service;

import hu.smthy.unique_file_lister.domain.UniqueFileAccess;

import java.util.List;

public interface HistoryService {
    List<UniqueFileAccess> readAll();
}
