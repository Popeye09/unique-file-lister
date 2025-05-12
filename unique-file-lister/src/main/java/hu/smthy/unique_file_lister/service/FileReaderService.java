package hu.smthy.unique_file_lister.service;

import java.io.File;
import java.io.FileFilter;

public interface FileReaderService {
    boolean exists(File file);
    boolean isDirectory(File file);
    boolean isFile(File file);
    boolean canRead(File file);
    File[] listFiles(File file) throws SecurityException;
}
