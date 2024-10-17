package com.liujiaming.core.servlet.upload;


import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
@Service
public class MinIOFileServiceImpl implements FileService {
    @Override
    public UploadEntity uploadFile(InputStream inputStream, UploadEntity entity) {
        return null;
    }

    @Override
    public void deleteFile(UploadEntity entity) {

    }

    @Override
    public void deleteFileByUrl(String url) {

    }

    @Override
    public void renameFile(UploadEntity entity, String fileName) {

    }

    @Override
    public InputStream downFile(UploadEntity entity) {
        return null;
    }

    @Override
    public void downFileByUrl(String url, File file) {

    }
}
