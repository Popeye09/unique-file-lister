package hu.smthy.unique_file_lister.service;

import hu.smthy.unique_file_lister.domain.UniqueFileAccess;

/**
 * Interface for managing the history of unique file access operations.
 * Provides methods to retrieve and save history records.
 */
public interface HistoryService {
    /**
     * Retrieves all unique file access records.
     * @return an iterable collection of {@link UniqueFileAccess} objects
     */
    Iterable<UniqueFileAccess> readAll();

    /**
     * Saves a unique file access record.
     * @param uniqueFileAccess the {@link UniqueFileAccess} object to save
     */
    void save(UniqueFileAccess uniqueFileAccess);
}