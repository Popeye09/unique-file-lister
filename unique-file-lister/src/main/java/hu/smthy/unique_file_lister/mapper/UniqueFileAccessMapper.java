package hu.smthy.unique_file_lister.mapper;

import org.modelmapper.ModelMapper;
import hu.smthy.unique_file_lister.domain.UniqueFileAccess;
import hu.smthy.unique_file_lister.domain.UniqueFileAccessData;
import org.springframework.stereotype.Component;

/**
 * Component implementation for mapping between {@link UniqueFileAccess} service level and {@link UniqueFileAccessData} repository objects.
 */
@Component
public class UniqueFileAccessMapper implements Mapper<UniqueFileAccessData, UniqueFileAccess> {

    private final ModelMapper modelMapper;

    public UniqueFileAccessMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Maps a {@link UniqueFileAccessData} object to {@link UniqueFileAccess}
     * @param uniqueFileAccessData object to map from
     * @return mapped {@link UniqueFileAccess} object
     */
    public UniqueFileAccess mapTo(UniqueFileAccessData uniqueFileAccessData) {
        return modelMapper.map(uniqueFileAccessData, UniqueFileAccess.class);
    }

    /**
     * Maps a {@link UniqueFileAccess} object to {@link UniqueFileAccessData}
     * @param uniqueFileAccess object to map from
     * @return mapped {@link UniqueFileAccessData} object
     */
    public UniqueFileAccessData mapFrom(UniqueFileAccess uniqueFileAccess) {
        return modelMapper.map(uniqueFileAccess, UniqueFileAccessData.class);
    }
}
