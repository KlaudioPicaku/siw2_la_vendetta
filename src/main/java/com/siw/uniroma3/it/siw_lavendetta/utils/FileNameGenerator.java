package com.siw.uniroma3.it.siw_lavendetta.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class FileNameGenerator {
    public static String generateFileName(Object model, MultipartFile file) {
        String className = model.getClass().getSimpleName().toLowerCase();
        String extension = getFileExtension(file.getOriginalFilename());

        return className + "-" + UUID.randomUUID().toString() + extension;
    }

    private static String getFileExtension(String fileName) {
        int extensionIndex = fileName.lastIndexOf('.');
        return extensionIndex >= 0 ? fileName.substring(extensionIndex) : "";
    }
}
