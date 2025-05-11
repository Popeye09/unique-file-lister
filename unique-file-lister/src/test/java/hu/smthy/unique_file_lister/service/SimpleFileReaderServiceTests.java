package hu.smthy.unique_file_lister.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.nio.file.Path;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class SimpleFileReaderServiceTests {

    private final SimpleFileReaderService underTest;

    @Autowired
    public SimpleFileReaderServiceTests(SimpleFileReaderService underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testReadingRoot(){
        File[] files = underTest.listFiles(Path.of("/").toFile(), null);

        assertThat(files)
                .isNotNull()
                .isNotEmpty()
                .extracting(File::getAbsolutePath)
                .contains(
                        "/bin",
                        "/usr",
                        "/etc",
                        "/lib"
                )
                .doesNotContain("/definitelyDoesNotExist");
    }
}
