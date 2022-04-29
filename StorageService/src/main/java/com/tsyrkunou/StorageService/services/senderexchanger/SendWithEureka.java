package com.tsyrkunou.StorageService.services.senderexchanger;

import com.tsyrkunou.StorageService.dto.SongDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class SendWithEureka implements SenderChanger {
    private final RestTemplate restTemplate;
    private final CircuitBreakerFactory circuitBreakerFactory;

    @Override
    public SongDTO sendSongDTOForExtract(SongDTO songDTO) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");

        return circuitBreaker.run(() -> restTemplate.postForObject("http://PARSER/parser/parse", songDTO,
                SongDTO.class), throwable -> notExtractDto(songDTO));
    }

    @Override
    public void sendNotify(Integer extractedid) {

        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker1");
        circuitBreaker.run(() -> restTemplate.postForObject("http://RESOURCE/resources/uploadsong", extractedid, Void.class, extractedid),
                throwable -> extractButNotSave(extractedid));
    }

    @Override
    public SongDTO getExtractDto(Integer id) {

        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker2");
      return   circuitBreaker.run(() -> restTemplate.getForObject("http://METADATA/metadata/getextract/{id}",
                SongDTO.class, id),
                throwable -> notExtractDto(id));
    }

    private SongDTO notExtractDto (SongDTO songDTO) {
        log.info("Parser are not available or faild with download extract dto");
        return songDTO;
    }

    private SongDTO notExtractDto (Integer id) {
        log.info("Parser are not available or faild with download extract dto");
        return SongDTO.builder()
                .id(id)
                .build();
    }

    private Integer extractButNotSave (Integer id) {
        log.info(" Song's  with id " + id   +" was extract but fail with notificate about");
        //todo some code with cashing id in storage
        return id;
    }



}
