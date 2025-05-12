package hu.smthy.unique_file_lister.service;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class SimpleFileReaderService implements FileReaderService{

    public boolean exists(File file) {
        return file.exists();
    }

    public boolean isDirectory(File file) {
        return file.isDirectory();
    }

    public boolean isFile(File file){
        return file.isFile();
    }

    public boolean canRead(File file) {
        return file.canRead();
    }

    public File[] listFiles(File file) throws SecurityException {
        return file.listFiles();
    }
}
