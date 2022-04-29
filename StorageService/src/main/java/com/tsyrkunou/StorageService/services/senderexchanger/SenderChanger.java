package com.tsyrkunou.StorageService.services.senderexchanger;

import com.tsyrkunou.StorageService.dto.SongDTO;
import org.springframework.stereotype.Service;

@Service
public interface SenderChanger {
    SongDTO sendSongDTOForExtract(SongDTO songDTO);

    void sendNotify(Integer extractedid);

    SongDTO getExtractDto(Integer id);
}
