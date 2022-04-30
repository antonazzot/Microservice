package com.example.demo.songmeta.service;

import com.example.demo.exception.MyCustomAppException;
import com.example.demo.songmeta.model.SongDTO;
import com.example.demo.songmeta.model.SongMetadata;
import com.example.demo.songmeta.repository.SongMetadataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SongMetadataService {

    private final SongMetadataRepository songMetadataRepository;
    private final MetaDataConvertor metaDataConvertorJson;
    private final UserMetadataConvectorJson userMetadataConvectorJsonImp;


    public void deleteById(List<Integer> id) {
        try {
            songMetadataRepository.
                    deleteAllById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Delete entity not exist");
        }
    }

    public Optional<String> getSongsById(Integer id) {
        return Optional.of(songMetadataRepository.findById(id).orElseThrow(() -> new MyCustomAppException("Song with id: " + id + " does not exist")).getMetadata());
    }

    public Optional<SongMetadata> getSongMetaById(Integer id) {
        return songMetadataRepository.findById(id);
    }

    public void saveSongMetadataFromKafka(SongDTO songDTO) {
        SongMetadata songMetadata = SongMetadata.builder()
                .id(songDTO.getId())
                .metadata(userMetadataConvectorJsonImp.getJsonFromUserMetadata(songDTO.getUserMetadata())).build();
        songMetadataRepository.save(songMetadata);
    }

    public SongDTO getSong(Integer id) {
        SongMetadata songMetadata = songMetadataRepository.findById(id).orElseThrow();
        Map<String, String> userMetadata = userMetadataConvectorJsonImp.getUserMetadata(songMetadata);
        return SongDTO.builder()
                .id(id)
                .userMetadata(userMetadata)
                .build();
    }
}
