package resourceservice.service.changerservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import resourceservice.config.KafakaSender;
import resourceservice.exception.MyCustomAppException;
import resourceservice.model.SongDTO;
import resourceservice.model.StorageSongDto;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaService {

    private final DeleteInterfece deleteByKafka;
    private final KafakaSender kafakaSender;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getMetaById(Integer id) throws MyCustomAppException {
        String meta = "Meta of the song with id: " + id + "  does not exist";
        try {
            meta = kafakaSender.getMeta(id);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new MyCustomAppException("Song with id " + id + " does not  exist" + e.getMessage());
        }
        return meta;
    }

    public Integer storeInStorage(SongDTO songDTO) {
        String topic = "storesongdto";
        String objectAsString;
        String answer = null;
        try {
            objectAsString = objectMapper.writeValueAsString(songDTO);
            answer = kafakaSender.sendCommonMessageWithStringCallback(topic, objectAsString);
        } catch (JsonProcessingException | ExecutionException | InterruptedException | TimeoutException e) {
            log.error("Fail with write/read Object in request to StorageService:  " + e.getMessage());
            throw new MyCustomAppException("Fail with write/read Object in request to StorageService: " + e.getMessage());
        }
        Integer result = 0;
        if (answer != null) {
            try {
                result = objectMapper.readValue(answer, Integer.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public StorageSongDto getExtractSong(Integer id) {
        String topic = "getextractsong";
        String objectAsString;
        String answer = null;
        try {
            objectAsString = objectMapper.writeValueAsString(id);
            answer = kafakaSender.sendCommonMessageWithStringCallback(topic, objectAsString);
        } catch (JsonProcessingException | ExecutionException | InterruptedException | TimeoutException e) {
            log.error("Fail with write/read Object in getExtractSong request to StorageService:  " + e.getMessage());
            throw new MyCustomAppException("Fail with write/read Object in request to StorageService: " + e.getMessage());
        }
        StorageSongDto result = null;
        if (answer != null) {
            try {
                result = objectMapper.readValue(answer, StorageSongDto.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void changeStage(Integer id) {
        String topic = "changestage";
        String objectAsString;
        try {
            objectAsString = objectMapper.writeValueAsString(id);
            kafakaSender.sendMessageWithoutCallback(topic, objectAsString);
        } catch (JsonProcessingException | ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> deleteFromAllStorage(List<StorageSongDto> id) {
        return deleteByKafka.deleteEverewhere(id);
    }

    public SongDTO sendWithSongDtoReply(SongDTO songDTO) {
        return songDTO;
    }
}
