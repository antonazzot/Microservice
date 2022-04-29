package com.tsyrkunou.StorageService.services.senderexchanger;

import com.tsyrkunou.StorageService.dto.SongDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChangeSender {
    @Value("${sendBy}")
    String sendBy;

    private final   SenderChanger sendWithEureka;
    private final   SenderChanger sendWithKafka;
    private Map<String, SenderChanger> exchangerMap;
    @PostConstruct
    private void setUpExchange () {
     this.exchangerMap = Map.of("eureka", sendWithEureka, "kafka", sendWithKafka);
    }

    public SongDTO sendAndRecive (SongDTO songDTO) {
        return exchangerMap.get(sendBy).sendSongDTOForExtract(songDTO);
    }

    public void sendNotificate(Integer extractedid) {
        exchangerMap.get(sendBy).sendNotify(extractedid);
    }

    public SongDTO getExtractSong(Integer id) {
       return exchangerMap.get(sendBy).getExtractDto(id);
    }
}
