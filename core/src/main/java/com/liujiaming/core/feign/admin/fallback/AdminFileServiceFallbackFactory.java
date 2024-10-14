package com.liujiaming.core.feign.admin.fallback;

import com.liujiaming.core.common.Result;
import com.liujiaming.core.feign.admin.service.AdminFileService;
import com.liujiaming.core.servlet.upload.FileEntity;
import com.liujiaming.core.servlet.upload.UploadEntity;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
@Slf4j
@Component
public class AdminFileServiceFallbackFactory implements FallbackFactory<AdminFileService> {
    @Override
    public AdminFileService create(Throwable cause) {
        return new AdminFileService() {
            @Override
            public Result<List<FileEntity>> queryFileList(String batchId) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<FileEntity> queryById(Long fileId) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<List<FileEntity>> queryByIds(Collection<Long> fileIds) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<FileEntity> queryOne(String batchId) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result delete(Object fileId) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result delete(List<String> batchId) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<Integer> queryNum(List<String> batchId) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<List<FileEntity>> queryFileList(List<String> batchIdList) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<String> copyJxcImg(String batchId) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public void saveBatchFileEntity(List<String> adminFileIdList, String batchId) {
                log.error(cause.getMessage());

            }

            @Override
            public Result<UploadEntity> upload(MultipartFile file, String batchId, String type) {
                log.error(cause.getMessage());
                return Result.ok();
            }
        };
    }
}
