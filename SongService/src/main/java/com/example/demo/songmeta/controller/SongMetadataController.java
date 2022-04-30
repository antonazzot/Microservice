package com.example.demo.songmeta.controller;

import com.example.demo.songmeta.model.SongDTO;
import com.example.demo.songmeta.service.SongMetadataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/metadata")
public class SongMetadataController {

    private final SongMetadataService songMetadataService;

    @PostMapping("/save")
    @ResponseBody
    public Integer saveMetadata(@RequestBody SongDTO songDTO) {
        songMetadataService.saveSongMetadataFromKafka(songDTO);
        log.info("receive dto ={}", songDTO.toString());
        return 1;
    }

    @DeleteMapping("/delete/{deleteid}")
    @ResponseBody
    public void deleteMetadata(@PathVariable Integer[] deleteid) {
        songMetadataService.deleteById(Arrays.asList(deleteid));
    }

    @GetMapping("/songs/{id}")
    @ResponseBody
    public Optional<String> getSongsMetadata(@PathVariable(name = "id") Integer id) {
        return songMetadataService.getSongsById(id);
    }

    @GetMapping("/getextract/{id}")
    @ResponseBody
    public SongDTO getSong(@PathVariable Integer id) {
        return songMetadataService.getSong(id);
    }

}
