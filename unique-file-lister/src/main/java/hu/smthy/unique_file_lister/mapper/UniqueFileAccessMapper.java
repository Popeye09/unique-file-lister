package hu.smthy.unique_file_lister.mapper;

import org.modelmapper.ModelMapper;
import hu.smthy.unique_file_lister.domain.UniqueFileAccess;
import hu.smthy.unique_file_lister.domain.UniqueFileAccessData;
import org.springframework.stereotype.Component;

@Component
public class UniqueFileAccessMapper implements Mapper<UniqueFileAccessData, UniqueFileAccess> {

    private final ModelMapper modelMapper;

    public UniqueFileAccessMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UniqueFileAccess mapTo(UniqueFileAccessData uniqueFileAccessData) {
        return modelMapper.map(uniqueFileAccessData, UniqueFileAccess.class);
    }

    public UniqueFileAccessData mapFrom(UniqueFileAccess uniqueFileAccess) {
        return modelMapper.map(uniqueFileAccess, UniqueFileAccessData.class);
    }
}
