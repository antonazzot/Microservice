package com.example.demo.songmeta.repository;

import com.example.demo.songmeta.model.SongMetadata;
import org.springframework.data.repository.CrudRepository;


public interface SongMetadataRepository extends CrudRepository<SongMetadata, Integer> {
}
