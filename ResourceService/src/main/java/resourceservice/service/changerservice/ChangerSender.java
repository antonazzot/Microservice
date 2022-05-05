package resourceservice.service.changerservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import resourceservice.exception.MyCustomAppException;
import resourceservice.model.SongDTO;
import resourceservice.model.StorageSongDto;
import resourceservice.service.ConverterMultipartToFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChangerSender {
    @Value("${changeBy}")
    String changeBy;

    private final Exchanger exchangerEvreka;
    private final Exchanger exchangerKafka;
    private final ConverterMultipartToFile converterMultipartToFile;


    private Map<String, Exchanger> exchangerMap;

    @PostConstruct
    private void setUpExchange() {
        this.exchangerMap = Map.of("evreka", exchangerEvreka, "kafka", exchangerKafka);
    }

    public SongDTO sendAndRecive(SongDTO songDTO) {
        return exchangerMap.get(changeBy).receiveSong(songDTO);
    }

    public void deleteFromMeta(Integer[] id) {
        exchangerMap.get(changeBy).deleteSongById(id);
    }

    public String getMetaById(Integer id) {
        return exchangerMap.get(changeBy).getMetaById(id);
    }

    public Integer sendToStageService(MultipartFile multipartFile) {
        File file = null;
        try {
            file = converterMultipartToFile.convertMultiPartToFile(multipartFile);
        } catch (IOException e) {
            log.error("Convert multipartfile to file exception");
            throw new MyCustomAppException(e.getMessage() + ": " + " Convert multipartfile to file exception");
        }
        SongDTO songDTO = SongDTO.builder()
                .file(file)
                .build();
        return exchangerMap.get(changeBy).storeInStorage(songDTO);
    }

    public StorageSongDto getExtractSong(Integer id) {
        return exchangerMap.get(changeBy).getSong(id);
    }

    public void changeStorageStage(Integer id) {
        exchangerMap.get(changeBy).changeStage(id);
    }

    public List<Integer> deleteFromAllStorage(Integer[] id) {
        List<StorageSongDto> songDtoList = new ArrayList<>();
        for (Integer integer : id) {
            StorageSongDto extractSong = getExtractSong(integer);
            if (extractSong != null) {
                songDtoList.add(getExtractSong(integer));
            }
        }
        return exchangerMap.get(changeBy).deleteFromAllService(songDtoList);
    }
}
