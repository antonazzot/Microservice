package resourceservice.service.changerservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import resourceservice.model.SongDTO;
import resourceservice.model.StorageSongDto;

@Service
@RequiredArgsConstructor
public class EurekaSend implements EurekaSender {
    private final RestTemplate restTemplate;

    public SongDTO extractMetadata(SongDTO songDTO) {
        return restTemplate.postForObject("http://PARSER/parser/parse/{file}", songDTO,
                SongDTO.class, songDTO);
    }

    public String getMetadataFromBD(Integer id) {
        return restTemplate.getForObject("http://METADATA/metadata/songs/{id}", String.class,
                id);
    }

    @Override
    public Integer SendToStorage(SongDTO songDTO) {
        return restTemplate.postForObject("http://STORAGE/store/{dto}", songDTO,
                Integer.class, songDTO);
    }

    @Override
    public StorageSongDto getStorageSong(Integer id) {
        return restTemplate.getForObject("http://STORAGE/store/get/{id}",
                StorageSongDto.class, id);
    }

    @Override
    public void changeStage(Integer id) {
        restTemplate.getForObject("http://STORAGE/store/changestage/{id}",
                Void.class, id);
    }

}
