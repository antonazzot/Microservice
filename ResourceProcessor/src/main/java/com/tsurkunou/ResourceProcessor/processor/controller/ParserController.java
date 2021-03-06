package com.tsurkunou.ResourceProcessor.processor.controller;

import com.tsurkunou.ResourceProcessor.processor.processorservice.EvrekaService;
import com.tsurkunou.ResourceProcessor.processor.processorservice.SongDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parser")
public class ParserController {
    private final EvrekaService evrekaService;
    
    @PostMapping("/parse/{file}")
    @ResponseBody
    public SongDTO postFile (@RequestBody SongDTO songDTO) {
     return   evrekaService.receiveSongToMeta(songDTO);
    }
}
