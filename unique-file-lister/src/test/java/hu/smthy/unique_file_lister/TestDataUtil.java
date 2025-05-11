package hu.smthy.unique_file_lister;

import hu.smthy.unique_file_lister.domain.UniqueFileAccessData;

import java.util.Random;

public class TestDataUtil {
    private static final Random random = new Random();
    private TestDataUtil(){
    }

    public static UniqueFileAccessData createTestUniqueFileAccess(){
        return UniqueFileAccessData.builder()
                .username("root")
                .timestamp(System.currentTimeMillis())
                .directory("/")
                .extension(".txt")
                .build();
    }

    public static UniqueFileAccessData createRandomUniqueFileAccess(){
        return UniqueFileAccessData.builder()
                .username("root")
                .timestamp(random.nextLong(1746971812))
                .directory("/")
                .extension(".txt")
                .build();
    }
}
