package hu.smthy.unique_file_lister;

import hu.smthy.unique_file_lister.domain.UniqueFileAccess;

public class TestDataUtil {
    private TestDataUtil(){
    }

    public static UniqueFileAccess createTestUniqueFileAccess(){
        return UniqueFileAccess.builder()
                .id(1L)
                .username("root")
                .timestamp(System.currentTimeMillis())
                .directory("/")
                .extension(".txt")
                .build();
    }
}
