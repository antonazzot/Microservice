package com.example.demo.songmeta.service;

import com.amazonaws.services.s3.model.ObjectMetadata;

public interface MetaDataConvertor {
    public String getMetadata (ObjectMetadata objectMetadata);
}
