package com.tsurkunou.ResourceProcessor.processor.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsurkunou.ResourceProcessor.processor.processorservice.ProcessorService;
import com.tsurkunou.ResourceProcessor.processor.processorservice.SongDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Slf4j
@Component
public class KafkaListener {
    private final ProcessorService processorService;

    @org.springframework.kafka.annotation.KafkaListener(topics = "uploadsong",
            groupId = "mygroup1")

    @SendTo("uploadmeta")
    public String listener(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SongDTO songDTO = objectMapper.readValue(message, SongDTO.class);

        SongDTO songDTOReturn = processorService.extractMetadataAndSave(songDTO);

        return objectMapper.writeValueAsString(songDTOReturn);
    }


    @org.springframework.kafka.annotation.KafkaListener(topics = "extractmeta",
            groupId = "mygroup111")
    @SendTo("uploadmeta")
    public String listenerSongDto(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SongDTO songDTO = objectMapper.readValue(message, SongDTO.class);
        SongDTO songDTOReturn = processorService.extractMetadataAndSave(songDTO);
        return objectMapper.writeValueAsString(songDTOReturn);
    }
}

