package hu.smthy.unique_file_lister.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Enable Mockito support
public class UniqueFileRecursiveTreeTraverseServiceTests {

    @Mock
    private FileReaderService fileReaderService;
    @Mock
    private HistoryService historyService;

    @InjectMocks
    private UniqueFileRecursiveTreeTraverseService underTest;

    @Test
    public void testGetUniqueFilesThrowsFileNotFoundException(){
        String invalidPath = "/invalidPath.txt";
        File rootDirectory = Path.of(invalidPath).toFile();

        when(fileReaderService.exists(rootDirectory)).thenReturn(false);

        assertThrows(FileNotFoundException.class, () -> {
            underTest.getUniqueFiles(invalidPath, "root");
        }, "Expected FileNotFoundException for non-existing path");
    }

    @Test
    public void testGetUniqueFileThrowsNotDirectoryException(){
        String invalidPath = "/invalidPath.txt";
        File rootDirectory = Path.of(invalidPath).toFile();

        when(fileReaderService.exists(rootDirectory)).thenReturn(true);
        when(fileReaderService.isDirectory(rootDirectory)).thenReturn(false);

        assertThrows(NotDirectoryException.class, () -> {
            underTest.getUniqueFiles(invalidPath, "root");
        }, "Expected NotDirectoryException for path not leading to a directory");
    }

    @Test
    public void testGetUniqueFileThrowsSecurityException(){
        String invalidPath = "/invalidPath.txt";
        File rootDirectory = Path.of(invalidPath).toFile();

        when(fileReaderService.exists(rootDirectory)).thenReturn(true);
        when(fileReaderService.isDirectory(rootDirectory)).thenReturn(true);
        when(fileReaderService.canRead(rootDirectory)).thenReturn(false);

        assertThrows(SecurityException.class, () -> {
            underTest.getUniqueFiles(invalidPath, "root");
        }, "Expected SecurityException for unreadable directory");
    }

    @Test
    public void testGetUniqueFileWithZeroDepth() throws Exception{
        String path = "/validPath";
        File rootDirectory = Path.of(path).toFile();

        when(fileReaderService.exists(rootDirectory)).thenReturn(true);
        when(fileReaderService.isDirectory(rootDirectory)).thenReturn(true);
        when(fileReaderService.canRead(rootDirectory)).thenReturn(true);

        File[] files = { mock(File.class), mock(File.class) };
        when(files[0].getName()).thenReturn("unique1.txt");
        when(files[1].getName()).thenReturn("unique2.txt");

        when(fileReaderService.listFiles(rootDirectory, null)).thenReturn(files);

        when(fileReaderService.isFile(files[0])).thenReturn(true);
        when(fileReaderService.isFile(files[1])).thenReturn(true);

        Map<String, Integer> result = underTest.getUniqueFiles(path, "root");

        assertAll(
                () -> assertEquals(1, result.get(files[0].getName())),
                () -> assertEquals(1, result.get(files[1].getName()))
        );
    }

    @Test
    public void testGetUniqueFilesCountsFilesCorrectly() throws Exception {
        String path = "/validPath";
        File root = Path.of(path).toFile();

        when(fileReaderService.exists(root)).thenReturn(true);
        when(fileReaderService.isDirectory(root)).thenReturn(true);
        when(fileReaderService.canRead(root)).thenReturn(true);

        File[] files = { mock(File.class), mock(File.class), mock(File.class)};
        when(files[0].getName()).thenReturn("unique.txt");
        when(files[2].getName()).thenReturn("duplicate.txt");

        when(fileReaderService.listFiles(root, null)).thenReturn(files);

        when(fileReaderService.isFile(files[0])).thenReturn(true);
        when(fileReaderService.isFile(files[1])).thenReturn(false);
        when(fileReaderService.isDirectory(same(files[1]))).thenReturn(true); // The time it took to figure this out is inhuman
        when(fileReaderService.isFile(files[2])).thenReturn(true);


        File[] filesInSubdirectory = { mock(File.class) };
        when(filesInSubdirectory[0].getName()).thenReturn("duplicate.txt");
        when(fileReaderService.isFile(filesInSubdirectory[0])).thenReturn(true);

        lenient().when(fileReaderService.listFiles(same(files[1]), isNull())).thenReturn(filesInSubdirectory);

        Map<String, Integer> result = underTest.getUniqueFiles(path, "root");

        assertAll(
                () -> assertEquals(1, result.get(files[0].getName())), // unique.txt
                () -> assertEquals(2, result.get(files[2].getName())) // duplicate.txt
        );
    }

}
