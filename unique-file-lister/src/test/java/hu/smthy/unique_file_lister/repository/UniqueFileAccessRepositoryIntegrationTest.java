package hu.smthy.unique_file_lister.repository;

import hu.smthy.unique_file_lister.TestDataUtil;
import hu.smthy.unique_file_lister.domain.UniqueFileAccessData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UniqueFileAccessRepositoryIntegrationTest {
    private final UniqueFileAccessRepository underTest;

    @Autowired
    public UniqueFileAccessRepositoryIntegrationTest(UniqueFileAccessRepository underTest){
        this.underTest = underTest;
    }

    @BeforeEach
    public void emptyRepository(){
        underTest.deleteAll();
    }

    @Test
    public void testUniqueFileAccessCreateAndRecall(){
        UniqueFileAccessData uniqueFileAccessData = TestDataUtil.createTestUniqueFileAccess();
        underTest.save(uniqueFileAccessData);
        Optional<UniqueFileAccessData> result = underTest.findById(uniqueFileAccessData.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(uniqueFileAccessData);
    }

    @Test
    public void testUniqueFileAccessReadAll(){
        UniqueFileAccessData uniqueFileAccessData1 = TestDataUtil.createRandomUniqueFileAccess();
        underTest.save(uniqueFileAccessData1);
        UniqueFileAccessData uniqueFileAccessData2 = TestDataUtil.createRandomUniqueFileAccess();
        underTest.save(uniqueFileAccessData2);
        UniqueFileAccessData uniqueFileAccessData3 = TestDataUtil.createRandomUniqueFileAccess();
        underTest.save(uniqueFileAccessData3);

        Iterable<UniqueFileAccessData> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(uniqueFileAccessData1, uniqueFileAccessData2, uniqueFileAccessData3);
    }
}
