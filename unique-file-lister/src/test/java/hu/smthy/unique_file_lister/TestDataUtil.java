package hu.smthy.unique_file_lister;

import hu.smthy.unique_file_lister.domain.UniqueFileAccess;

import java.util.Random;

public class TestDataUtil {
    private static final Random random = new Random();
    private TestDataUtil(){
    }

    public static UniqueFileAccess createTestUniqueFileAccess(){
        return UniqueFileAccess.builder()
                .username("root")
                .timestamp(System.currentTimeMillis())
                .directory("/")
                .extension(".txt")
                .build();
    }

    public static UniqueFileAccess createRandomUniqueFileAccess(){
        return UniqueFileAccess.builder()
                .username("root")
                .timestamp(random.nextLong(1746971812))
                .directory("/")
                .extension(".txt")
                .build();
    }
}
