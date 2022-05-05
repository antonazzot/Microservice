package resourceservice.service.changerservice;

import org.springframework.stereotype.Service;
import resourceservice.model.SongDTO;
import resourceservice.model.StorageSongDto;

import java.util.List;

@Service
public interface Exchanger {
    public SongDTO receiveSong(SongDTO songDTO);

    public void deleteSongById(Integer[] id);

    public String getMetaById(Integer id);

    Integer storeInStorage(SongDTO songDTO);

    StorageSongDto getSong(Integer id);

    void changeStage(Integer id);

    List<Integer> deleteFromAllService(List<StorageSongDto> id);
}
