package hu.smthy.unique_file_lister.service;

import hu.smthy.unique_file_lister.domain.UniqueFileAccess;
import hu.smthy.unique_file_lister.domain.UniqueFileAccessData;
import hu.smthy.unique_file_lister.mapper.Mapper;
import hu.smthy.unique_file_lister.repository.UniqueFileAccessRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class HistoryServiceDatabase implements HistoryService{

    private final UniqueFileAccessRepository uniqueFileAccessRepository;
    private final Mapper<UniqueFileAccessData, UniqueFileAccess> uniqueFileAccessMapper;

    public HistoryServiceDatabase(UniqueFileAccessRepository uniqueFileAccessRepository, Mapper<UniqueFileAccessData, UniqueFileAccess> uniqueFileAccessMapper) {
        this.uniqueFileAccessRepository = uniqueFileAccessRepository;
        this.uniqueFileAccessMapper = uniqueFileAccessMapper;
    }

    public Iterable<UniqueFileAccess> readAll() {
        Iterable<UniqueFileAccessData> uniqueFileAccessDataIterable = uniqueFileAccessRepository.findAll();
        ArrayList<UniqueFileAccess> uniqueFileAccessList = new ArrayList<>();

        uniqueFileAccessDataIterable.forEach(
                uniqueFileAccessData -> uniqueFileAccessList
                        .add(uniqueFileAccessMapper
                                .mapTo(uniqueFileAccessData)));

        return uniqueFileAccessList;
    }

    public void save(UniqueFileAccess uniqueFileAccess) {
        uniqueFileAccessRepository.save(uniqueFileAccessMapper.mapFrom(uniqueFileAccess));
    }
}
