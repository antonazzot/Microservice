package com.tsurkunou.ResourceProcessor.processor.processorservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class EvrekaService {
    private final RestTemplate restTemplate;
    private final ProcessorService processorService;
    private final CircuitBreakerFactory circuitBreakerFactory;

    public SongDTO receiveSongToMeta(SongDTO songDTO) {
        SongDTO dto = processorService.extractMetadataAndSave(songDTO);

        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker1");

        circuitBreaker.run(() -> restTemplate.postForObject("http://METADATA/metadata/save/", dto,
                        Integer.class, dto),
                throwable -> extractButNotSave(dto));

        return dto;
    }

    private Integer extractButNotSave(SongDTO dto) {
        log.info(" Song's  with id " + dto.getId() + " was extract but fail with save ");
        return dto.getId();
    }


}
