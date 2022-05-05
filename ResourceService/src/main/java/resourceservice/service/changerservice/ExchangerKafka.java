package resourceservice.service.changerservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import resourceservice.model.SongDTO;
import resourceservice.model.StorageSongDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangerKafka implements Exchanger {
    private final KafkaService kafkaService;
    private final DeleteInterfece deleteByKafka;

    @Override
    public SongDTO receiveSong(SongDTO songDTO) {
        return kafkaService.sendWithSongDtoReply(songDTO);
    }

    @Override
    public void deleteSongById(Integer[] id) {
        deleteByKafka.deleteFromMetadata(id);
    }

    @Override
    public String getMetaById(Integer id) {
        return kafkaService.getMetaById(id);
    }

    @Override
    public Integer storeInStorage(SongDTO songDTO) {
        return kafkaService.storeInStorage(songDTO);
    }

    @Override
    public StorageSongDto getSong(Integer id) {
        return kafkaService.getExtractSong(id);
    }

    @Override
    public void changeStage(Integer id) {
        kafkaService.changeStage(id);
    }

    @Override
    public List<Integer> deleteFromAllService(List<StorageSongDto> id) {
        return kafkaService.deleteFromAllStorage(id);
    }
}
