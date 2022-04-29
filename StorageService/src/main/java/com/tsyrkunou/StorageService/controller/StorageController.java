package com.tsyrkunou.StorageService.controller;

import com.tsyrkunou.StorageService.dto.SongDTO;
import com.tsyrkunou.StorageService.dto.StorageSongDto;
import com.tsyrkunou.StorageService.services.PrimeStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StorageController {

    private final PrimeStorageService primeStorageService;

    @PostMapping("/{dto}")
    @ResponseBody
    public Integer storeSong(@RequestBody SongDTO songDTO) {
        return primeStorageService.storeSong(songDTO);
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public StorageSongDto storageSongDto(@PathVariable Integer id) {
        return primeStorageService.getSong(id);
    }

    @GetMapping("/changestage/{id}")
    @ResponseBody
    public void changeStage(@PathVariable Integer id) {
        primeStorageService.changeStage(id);
    }

    @DeleteMapping("/delete/{deleteid}")
    @ResponseBody
    public void deleteMetadata(@PathVariable Integer[] deleteid) {
        primeStorageService.deleteById(Arrays.asList(deleteid));
    }
}
