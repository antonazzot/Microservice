package com.example.demo.songmeta.service;

import com.example.demo.songmeta.model.SongMetadata;

import java.util.Map;

public interface UserMetadataConvectorJson {
    String getJsonFromUserMetadata(Map<String, String> userMeta);

    Map<String, String> getUserMetadata(SongMetadata songMetadata);
}
