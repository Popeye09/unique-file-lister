package hu.smthy.unique_file_lister;

import hu.smthy.unique_file_lister.domain.UniqueFileAccess;

import java.nio.file.Path;

public class TestDataUtil {
    public static UniqueFileAccess createTestUniqueFileAccess(){
        return new UniqueFileAccess(
                1L,
                System.getProperty("user.name"),
                System.currentTimeMillis(),
                "/",
                ".txt"
        );
    }
}
