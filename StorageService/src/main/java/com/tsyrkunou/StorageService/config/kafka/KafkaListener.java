package com.tsyrkunou.StorageService.config.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsyrkunou.StorageService.dto.SongDTO;
import com.tsyrkunou.StorageService.dto.StorageSongDto;
import com.tsyrkunou.StorageService.services.PrimeStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.List;


@RequiredArgsConstructor
@Slf4j
@Component
public class KafkaListener {

    private final PrimeStorageService primeStorageService;

    @org.springframework.kafka.annotation.KafkaListener(topics = "storesongdto",
            groupId = "mygroup10")

    @SendTo("storeid")
    public String listener(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SongDTO songDTO = objectMapper.readValue(message, SongDTO.class);
        Integer result = primeStorageService.storeSong(songDTO);
        return objectMapper.writeValueAsString(result);
    }


    @org.springframework.kafka.annotation.KafkaListener(topics = "getextractsong",
            groupId = "mygroup1550")

    @SendTo("storagesongdto")
    public String listenerGetExtract(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Integer id = objectMapper.readValue(message, Integer.class);
        StorageSongDto result = primeStorageService.getSong(id);
        return objectMapper.writeValueAsString(result);
    }


    @org.springframework.kafka.annotation.KafkaListener(topics = "changestage",
            groupId = "mygroup1210")
    public void changeStage(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Integer id = objectMapper.readValue(message, Integer.class);
        primeStorageService.changeStage(id);
    }

    @org.springframework.kafka.annotation.KafkaListener(topics = "deletesong",
            groupId = "mygroup333")
    public void factoryList(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List list = objectMapper.readValue(message, List.class);
        list.forEach(System.out::println);
        primeStorageService.deleteById(list);
    }


}

