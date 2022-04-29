package com.tsyrkunou.StorageService.services;

import com.tsyrkunou.StorageService.dto.SongDTO;
import com.tsyrkunou.StorageService.model.StorageSong;
import com.tsyrkunou.StorageService.model.StorageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Date;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ConverterService {

    private final RandomBucketService randomBucketService;

    public StorageSong convertSongDTO (SongDTO songDTO) {
        File file = songDTO.getFile();

        return StorageSong.builder()
                .fileName(file.getName())
                .filePath(file.getPath())
                .bucketName(randomBucketService.getBucket())
                .storageType(StorageType.STAG)
                .storageDate(Date.valueOf(LocalDate.now()))
                .build();
    }
}
