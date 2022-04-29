package com.tsyrkunou.StorageService.services.senderexchanger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsyrkunou.StorageService.config.kafka.KafakaSender;
import com.tsyrkunou.StorageService.dto.SongDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Slf4j
@RequiredArgsConstructor
public class SendWithKafka implements SenderChanger {
    private final KafakaSender kafakaSender;
    private final CircuitBreakerFactory circuitBreakerFactory;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public SongDTO sendSongDTOForExtract(SongDTO songDTO) {
        String asString = null;
        String stringResult = null;
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker3");
        return circuitBreaker.run(() -> getSongDTO(songDTO), Throwable -> exceptionWithSend(songDTO));
    }

    private SongDTO exceptionWithSend(SongDTO songDTO) {
        log.error("Exception in send SongDto");
        return songDTO;
    }

    private SongDTO getSongDTO(SongDTO songDTO) {
        String asString;
        String stringResult;
        SongDTO result = null;
        try {
            asString = objectMapper.writeValueAsString(songDTO);
            stringResult = kafakaSender.sendCommonMessageWithStringCallback("extractmeta", asString);
            result = objectMapper.readValue(stringResult, SongDTO.class);
        } catch (JsonProcessingException | ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void sendNotify(Integer extractedid) {
        String topic = "notification";
        String asString = null;
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker3");
        circuitBreaker.run(() -> extracted(extractedid, topic));
    }

    private String extracted(Integer extractedid, String topic) {
        String asString = null;
        try {
            asString = objectMapper.writeValueAsString(extractedid);
            kafakaSender.sendCommonMessageWithStringCallback(topic, asString);
        } catch (JsonProcessingException | TimeoutException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return asString;
    }

    @Override
    public SongDTO getExtractDto(Integer id) {
        return null;
    }
}
