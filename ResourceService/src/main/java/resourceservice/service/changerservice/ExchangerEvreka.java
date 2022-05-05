package resourceservice.service.changerservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import resourceservice.model.SongDTO;
import resourceservice.model.StorageSongDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangerEvreka implements Exchanger {
    private final EurekaSender eurekaSend;
    private final DeleteInterfece deleteService;

    @Override
    public SongDTO receiveSong(SongDTO songDTO) {
        return eurekaSend.extractMetadata(songDTO);
    }

    @Override
    public void deleteSongById(Integer[] id) {
        deleteService.deleteFromMetadata(id);
    }

    @Override
    public String getMetaById(Integer id) {
        return eurekaSend.getMetadataFromBD(id);
    }

    @Override
    public Integer storeInStorage(SongDTO songDTO) {
        return eurekaSend.SendToStorage(songDTO);
    }

    @Override
    public StorageSongDto getSong(Integer id) {
        return eurekaSend.getStorageSong(id);
    }

    @Override
    public void changeStage(Integer id) {
        eurekaSend.changeStage(id);
    }

    @Override
    public List<Integer> deleteFromAllService(List<StorageSongDto> id) {
        return deleteService.deleteEverewhere(id);
    }
}
