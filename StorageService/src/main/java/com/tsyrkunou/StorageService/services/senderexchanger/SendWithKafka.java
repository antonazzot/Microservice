package com.tsyrkunou.StorageService.services.senderexchanger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsyrkunou.StorageService.config.kafka.KafakaSender;
import com.tsyrkunou.StorageService.dto.SongDTO;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RequiredArgsConstructor
public class SendWithKafka implements SenderChanger {
    private final KafakaSender kafakaSender;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public SongDTO sendSongDTOForExtract(SongDTO songDTO) {
        String asString = null;
        String stringResult = null;
        SongDTO result = null;
        try {
             asString = objectMapper.writeValueAsString(songDTO);
             stringResult =  kafakaSender.sendCommonMessageWithStringCallback("extractmeta", asString);
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
        try {
             asString = objectMapper.writeValueAsString(extractedid);
            kafakaSender.sendCommonMessageWithStringCallback(topic, asString);
        } catch (JsonProcessingException | TimeoutException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public SongDTO getExtractDto(Integer id) {
        return null;
    }
}
