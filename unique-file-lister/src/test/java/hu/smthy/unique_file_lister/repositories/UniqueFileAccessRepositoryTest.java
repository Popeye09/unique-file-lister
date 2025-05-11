package hu.smthy.unique_file_lister.repositories;

import hu.smthy.unique_file_lister.TestDataUtil;
import hu.smthy.unique_file_lister.domain.UniqueFileAccess;
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
public class UniqueFileAccessRepositoryTest {
    public UniqueFileAccessRepository underTest;

    @Autowired
    public UniqueFileAccessRepositoryTest(UniqueFileAccessRepository underTest){
        this.underTest = underTest;
    }

    @Test
    public void TestUniqueFileAccessCreateAndRecall(){
        UniqueFileAccess uniqueFileAccess = TestDataUtil.createTestUniqueFileAccess();
        underTest.save(uniqueFileAccess);
        Optional<UniqueFileAccess> result = underTest.findById(uniqueFileAccess.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(uniqueFileAccess);
    }
}
