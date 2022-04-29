package com.tsyrkunou.StorageService.services;

import com.tsyrkunou.StorageService.dto.SongDTO;
import com.tsyrkunou.StorageService.dto.StorageSongDto;
import com.tsyrkunou.StorageService.model.StorageSong;
import com.tsyrkunou.StorageService.model.StorageType;
import com.tsyrkunou.StorageService.repository.StorageRepository;
import com.tsyrkunou.StorageService.services.senderexchanger.ChangeSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Proxy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrimeStorageService {

    private final StorageRepository storageRepository;
    private final ConverterService converterService;
    private final ChangeSender changeSender;


    public Integer storeSong(SongDTO songDTO) {
        StorageSong storageSong = converterService.convertSongDTO(songDTO);
        StorageSong saveStorage = storageRepository.save(storageSong);
        songDTO.setId(storageSong.getId());
        SongDTO returnedDto = changeSender.sendAndRecive(songDTO);
        Integer extractedid = returnedDto.getId();
        changeSender.sendNotificate(extractedid);
        return extractedid;
    }

    public StorageSongDto getSong(Integer id) {
        Optional<StorageSong> byId = storageRepository.findById(id);
        StorageSong storageSong;
        SongDTO extractSong = changeSender.getExtractSong(id);
        StorageSongDto storageSongDto = null;
        if (byId.isPresent()) {
            storageSong = byId.get();
            storageSongDto = StorageSongDto.builder()
                    .id(id)
                    .userMetadata(extractSong.getUserMetadata())
                    .bucketName(storageSong.getBucketName())
                    .fileName(storageSong.getFileName())
                    .storageType(storageSong.getStorageType().name())
                    .filePath(storageSong.getFilePath())
                    .build();
        }
        return storageSongDto;
    }

    public void changeStage(Integer id) {
        StorageSong storageSong = storageRepository.getById(id);
        System.out.println(storageSong.getStorageDate());
        storageSong.setStorageType(StorageType.PERMANENT);
        storageRepository.save(storageSong);
    }

    public void deleteById(List<Integer> asList) {
        storageRepository.deleteAllById(asList);
    }
}
