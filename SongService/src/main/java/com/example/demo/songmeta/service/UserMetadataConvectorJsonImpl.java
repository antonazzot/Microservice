package com.example.demo.songmeta.service;

import com.example.demo.songmeta.model.SongMetadata;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class UserMetadataConvectorJsonImpl implements UserMetadataConvectorJson {
    @Override
    public String getJsonFromUserMetadata(Map<String, String> userMeta) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(userMeta);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return userMeta.toString();
    }

    @Override
    public Map<String, String> getUserMetadata(SongMetadata songMetadata) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> result = null;
        try {
            result = objectMapper.readValue(songMetadata.getMetadata(), Map.class);
        } catch (JsonProcessingException e) {
            log.error("Exception in parse string meta to map");
        }
        return result;
    }
}
