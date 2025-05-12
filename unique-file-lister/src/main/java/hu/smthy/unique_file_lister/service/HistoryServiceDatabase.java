package hu.smthy.unique_file_lister.service;

import hu.smthy.unique_file_lister.domain.UniqueFileAccess;
import hu.smthy.unique_file_lister.domain.UniqueFileAccessData;
import hu.smthy.unique_file_lister.mapper.Mapper;
import hu.smthy.unique_file_lister.repository.UniqueFileAccessRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Service implementation for managing the history of unique file access operations.
 * This class interacts with the database to save and retrieve history records.
 */
@Service
public class HistoryServiceDatabase implements HistoryService{

    private final UniqueFileAccessRepository uniqueFileAccessRepository;
    private final Mapper<UniqueFileAccessData, UniqueFileAccess> uniqueFileAccessMapper;

    /**
     * Constructs a new HistoryServiceDatabase with the specified repository and mapper.
     *
     * @param uniqueFileAccessRepository the repository for accessing unique file access data
     * @param uniqueFileAccessMapper the mapper for converting between domain and data objects
     */
    public HistoryServiceDatabase(UniqueFileAccessRepository uniqueFileAccessRepository, Mapper<UniqueFileAccessData, UniqueFileAccess> uniqueFileAccessMapper) {
        this.uniqueFileAccessRepository = uniqueFileAccessRepository;
        this.uniqueFileAccessMapper = uniqueFileAccessMapper;
    }

    /**
     * Retrieves all unique file access records from the database.
     *
     * @return an iterable collection of {@link UniqueFileAccess} objects
     */
    public Iterable<UniqueFileAccess> readAll() {
        Iterable<UniqueFileAccessData> uniqueFileAccessDataIterable = uniqueFileAccessRepository.findAll();
        ArrayList<UniqueFileAccess> uniqueFileAccessList = new ArrayList<>();

        uniqueFileAccessDataIterable.forEach(
                uniqueFileAccessData -> uniqueFileAccessList
                        .add(uniqueFileAccessMapper
                                .mapTo(uniqueFileAccessData)));

        return uniqueFileAccessList;
    }

    /**
     * Saves a unique file access record to the database.
     *
     * @param uniqueFileAccess the {@link UniqueFileAccess} object to save
     */
    public void save(UniqueFileAccess uniqueFileAccess) {
        uniqueFileAccessRepository.save(uniqueFileAccessMapper.mapFrom(uniqueFileAccess));
    }
}
