package com.siw.uniroma3.it.siw_lavendetta.utils;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileUploader {
    public static void saveFileToLocalDirectory(MultipartFile file, String directoryPath, String fileName) throws IOException {
        Path targetLocation = Path.of(directoryPath, fileName);

        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
    }
}